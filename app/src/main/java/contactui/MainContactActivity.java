package contactui;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainContactActivity extends Activity {
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
     * 获取联系人信息
     */
    private void getContactMessage() {
        proDialog = new ProgressDialog(MainContactActivity.this);
        proDialog.setMessage("正在加载联系人...");
        proDialog.show();
        final List<String> listName = new ArrayList<>();
        final List<Map<String, String>> contactMessage = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri uri = ContactsContract.Contacts.CONTENT_URI;
                ContentResolver contentResolver = MainContactActivity.this.getContentResolver();
                Cursor cursor = contentResolver.query(uri, null, null, null, null);
                while (cursor.moveToNext()) {
                    //获取联系人的姓名
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));//联系人ID
                    listName.add(name);
                    //查询电话类型的数据操作
                    Cursor phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                            null, null);
                    Map<String, String> stringMap = new HashMap<String, String>();
                    while (phones.moveToNext()) {
                        String phoneNumber = phones.getString(phones.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        //添加Phone的信息
                        Log.e("zws", name + "的电话号码" + phoneNumber + "所在分组的id" + id);
                        stringMap.put(name, phoneNumber);
                        break;
                    }
                    phones.close();
                    contactMessage.add(stringMap);
                }
                cursor.close();
                nameContact = new String[listName.size()];
                for (int i = 0; i < listName.size(); i++) {
                    nameContact[i] = listName.get(i);
                }
                SourceDateList = filledData(nameContact, contactMessage);
                proDialog.dismiss();
                // 排序
                Collections.sort(SourceDateList, pinyinComparator);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new SortAdapter(MainContactActivity.this, SourceDateList);
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
                if (!checked){
                    Toast.makeText(MainContactActivity.this, sortModel.getName()+"选中了", Toast.LENGTH_SHORT).show();
                    num_selected++;
                }else {
                    Toast.makeText(MainContactActivity.this, sortModel.getName()+"取消选中了", Toast.LENGTH_SHORT).show();
                    num_selected--;
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
                if (ischeck){
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setChecked(true);
                    }
                    adapter.updateListView(list);
                    num_selected = list.size();
                }else {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setChecked(false);
                    }
                    adapter.updateListView(list);
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

    }
    /**
     * ΪListView
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(String[] date, List<Map<String, String>> mapList) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            sortModel.setMobilePhone(mapList.get(i).get(date[i]));
            String pinyin = characterParser.getSelling(date[i]);
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
}
