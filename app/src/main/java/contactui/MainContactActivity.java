package contactui;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zws.ble.contacthuawei.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import insert.AlreadyInsertActivity;

public class MainContactActivity extends Activity {
    public static String TIME_CHANGED_ACTION = "com.zws.action.TIME_CHANGED_ACTION";
    @InjectView(R.id.image)
    ImageView image;
    @InjectView(R.id.check_activity)
    CheckBox checkActivity;
    @InjectView(R.id.tv_seclec)
    TextView tvSeclec;
    @InjectView(R.id.num_selected)
    TextView numSelected;
    @InjectView(R.id.insert_activity)
    TextView insertActivity;
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;
    private ProgressDialog proDialog;

    /**
     * 汉字转拼音
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;

    /**
     * 处理特殊首字母
     */
    private PinyinComparator pinyinComparator;
    private static final int READ_CONTACTS = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS
    };
    private String[] nameContact;
    private  int num_selected = 0;
    private List<String> listName;
    private List<Map<String, SortModel>> contactMessage;
    private int groupNeedId;//名片夹的id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_maincontact);
        ButterKnife.inject(this);
        //获取联系人权限
        int permission = ActivityCompat.checkSelfPermission(MainContactActivity.this, Manifest.permission.READ_CONTACTS);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    MainContactActivity.this,
                    PERMISSIONS_STORAGE,
                    READ_CONTACTS
            );
        }
        //初始化view
        initViews();
    }

    /**
     * 获取联系人分组下的所有联系人
     * */
    private void getListAll(int groupId) {
        String[] RAW_PROJECTION = new String[] { ContactsContract.Data.RAW_CONTACT_ID, };

        String RAW_CONTACTS_WHERE = ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID
                + "=?"
                + " and "
                + ContactsContract.Data.MIMETYPE
                + "="
                + "'"
                + ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE
                + "'";

        // 通过分组的id 查询得到RAW_CONTACT_ID
        Cursor cursor = getContentResolver().query(
                ContactsContract.Data.CONTENT_URI, RAW_PROJECTION,
                RAW_CONTACTS_WHERE, new String[] { groupId + "" }, "data1 asc");

        while (cursor.moveToNext()) {
            // RAW_CONTACT_ID
            int col = cursor.getColumnIndex("raw_contact_id");
            int raw_contact_id = cursor.getInt(col);

            Log.e("zws", "raw_contact_id:" +
                    raw_contact_id);

            Uri dataUri = Uri.parse("content://com.android.contacts/data");
            Cursor dataCursor = getContentResolver().query(dataUri,
                    null, "raw_contact_id=?",
                    new String[] { raw_contact_id + "" }, null);
            String name = "";
            String email = "";
            String phone = "";
            String address = "";
            String ognization = "";
            String website = "";
            List<String> phoneList = new ArrayList<>();
            List<String> addressList = new ArrayList<>();
            while (dataCursor.moveToNext()) {
                String data1 = dataCursor.getString(dataCursor
                        .getColumnIndex("data1"));
                String mime = dataCursor.getString(dataCursor
                        .getColumnIndex("mimetype"));
                Log.e("zws", "mime = "+mime);
                StringBuilder stringBuilder = new StringBuilder();
                if ("vnd.android.cursor.item/phone_v2".equals(mime)) {
                    // Log.e("zws", "data1 v2"+data1+"groupId"+groupId);
                    stringBuilder.append("phone_v2 = "+data1);
                    phoneList.add(data1);
                } else if ("vnd.android.cursor.item/name".equals(mime)) {
                    // Log.e("zws", "data1 name"+data1+"groupId"+groupId);
                    stringBuilder.append("name = "+data1);
                    name = data1;
                }else if ("vnd.android.cursor.item/email_v2".equals(mime)){
                    stringBuilder.append("email_v2 = "+data1);
                    email = data1;
                }else if ("vnd.android.cursor.item/organization".equals(mime)){
                    stringBuilder.append("organization = "+data1);
                    ognization = data1;
                }else if ("vnd.android.cursor.item/photo".equals(mime)){
                    stringBuilder.append("photo = "+data1);
                }else if ("vnd.android.cursor.item/postal-address_v2".equals(mime)){
                    stringBuilder.append("postal-address_v2 = "+data1);
                    address = data1;
                    addressList.add(data1);
                }else if ("vnd.android.cursor.item/group_membership".equals(mime)){
                    stringBuilder.append("group_membership = "+data1);
                }else if ("vnd.android.cursor.item/website".equals(mime)){
                    stringBuilder.append("website = "+data1);
                    website = data1;
                }else if ("vnd.android.cursor.item/im".equals(mime)){
                    stringBuilder.append("im = "+data1);
                }
              //  Log.e("zws", "个人信息 = "+stringBuilder);
            }
            dataCursor.close();
            listName.add(name);
            Map<String, SortModel> stringMap = new HashMap<>();
            SortModel sorModel = new SortModel();
            sorModel.mobilePhone = phoneList;
            sorModel.email_v2 = email;
            sorModel.organization = ognization;
            sorModel.website = website;
            sorModel.postal_address_v2 = addressList;
            sorModel.name = name;
            sorModel.raw_contact_id = raw_contact_id;
            stringMap.put(name, sorModel);
            contactMessage.add(stringMap);
        }
        cursor.close();
    }
    /**
     * 获取联系人信息
     */
    private void getContactMessage() {
        proDialog = new ProgressDialog(MainContactActivity.this);
        proDialog.setMessage("正在加载联系人...");
        proDialog.show();
        listName = new ArrayList<>();
        contactMessage = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri uri = ContactsContract.Contacts.CONTENT_URI;
                ContentResolver contentResolver = MainContactActivity.this.getContentResolver();
                Cursor cursor = null;
                try {
                    cursor = contentResolver.query(ContactsContract.Groups.CONTENT_URI,
                            null, null, null, null);
                    while (cursor.moveToNext()){
                        int groupId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Groups._ID)); // 组id
                        String groupName = cursor.getString(cursor
                                .getColumnIndex(ContactsContract.Groups.TITLE)); // 组名
                        if (groupName.equals("PREDEFINED_HUAWEI_GROUP_CCARD")){
                            groupNeedId = groupId;
                            getListAll(groupId);
                        }
                       // String title = cursor.getString(cursor.getColumnIndex(ContactsContract.Groups.TITLE_RES));
                       // Log.e("zws", "groupId="+groupId+"groupName="+groupName+"title = "+title);
                    }
                }finally {
                    if (cursor!=null){
                        cursor.close();
                    }
                }

                SourceDateList = filledData(listName, contactMessage);
                proDialog.dismiss();
                // 排序
                Collections.sort(SourceDateList, pinyinComparator);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new SortAdapter(MainContactActivity.this, SourceDateList);
                        adapter.setCheckboxClick(new SortAdapter.CheckboxClickListen() {
                            @Override
                            public void onItemCheck(boolean checkBox) {
                                if (checkBox){
                                    num_selected++;
                                }else {
                                    num_selected--;
                                }
                                numSelected.setText(num_selected+"");
                            }
                        });
                        sortListView.setAdapter(adapter);
                    }
                });
            }
        }).start();

        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
        sortListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                SortModel sortModel = (SortModel) adapter.getItem(position);
                boolean checked = sortModel.getChecked();
                sortModel.setChecked(!checked);
                List<SortModel> list = new ArrayList<>();
                list.add(sortModel);
                if (!checked){
                    num_selected++;
                    adapter.setSelectList(list,1);
                }else {
                    num_selected--;
                    adapter.setSelectList(list,2);
                }
                adapter.notifyDataSetChanged();
                numSelected.setText(num_selected+"");
              //  Toast.makeText(getApplication(), ((SortModel) adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        checkActivity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischeck) {
                List<SortModel> list = adapter.getList();
                List<SortModel> list_beiyong = new ArrayList<>();
                if (ischeck){
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setChecked(true);
                    }
                    adapter.setSelectList(list, 3);
                    adapter.updateListView(list);
                    num_selected = list.size();
                }else {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setChecked(false);
                        list_beiyong.add(list.get(i));
                    }
                    adapter.setSelectList(list, 4);
                    adapter.updateListView(list_beiyong);
                    num_selected = 0;
                }
                numSelected.setText(num_selected+"");
            }
        });
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void initViews() {

        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });

        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        getContactMessage();
    }

    @OnClick(R.id.image)
    void backImage(View view){
        finish();
    }

    @OnClick(R.id.insert_activity)
    void insertIntent(View view){
        Log.e("zws", adapter.getSelectList().toString());
        Intent intent = new Intent(MainContactActivity.this, AlreadyInsertActivity.class);
        intent.putExtra("data", (Serializable) adapter.getSelectList());
        intent.putExtra("groupId", groupNeedId);
        startActivity(intent);
        registerBroadcastReceiver();
    }
    /**
     * ΪListView
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(List<String> date, List<Map<String, SortModel>> mapList) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date.get(i));
            sortModel = mapList.get(i).get(date.get(i));
            String pinyin = characterParser.getSelling(date.get(i));
            String sortString = pinyin.substring(0, 1).toUpperCase();

            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }
            mSortList.add(sortModel);
        }
        return mSortList;
    }

    /**
     * ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }

    /**
     * 注册广播
     */
    private void registerBroadcastReceiver(){
        ContactListReceive receiver = new ContactListReceive();
        IntentFilter filter = new IntentFilter(TIME_CHANGED_ACTION);
        registerReceiver(receiver, filter);
    }
    class ContactListReceive extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TIME_CHANGED_ACTION.equals(action)){
                getContactMessage();
            }
        }
    }
}
