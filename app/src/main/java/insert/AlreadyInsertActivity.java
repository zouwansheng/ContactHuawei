package insert;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.zws.ble.contacthuawei.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.CompanyAllModel;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import contactui.ClearEditText;
import contactui.MainContactActivity;
import contactui.SortModel;
import edit.EditActivity;
import http.Inventroy;
import http.RetrofitClient;
import insert.adapter.InsertAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zws on 2017/8/1.
 */

public class AlreadyInsertActivity extends Activity{
    @InjectView(R.id.close_text)
    TextView closeText;
    @InjectView(R.id.finish_text)
    TextView finishText;
    @InjectView(R.id.edit_search)
    ClearEditText editSearch;
    @InjectView(R.id.recycler_already)
    RecyclerView recyclerAlready;
    private LinearLayoutManager linearLayoutManager;
    private List<SortModel> sortModelList;
    private InsertAdapter insertAdapter;
    private int groupId;
    private CompanyAllModel model;
    private Inventroy inventroy;
    private List<CompanyAllModel> companyAllModels = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_alreadyinsert);
        ButterKnife.inject(this);

        RetrofitClient.Url = "http://192.168.2.4:8069";
        inventroy = RetrofitClient.getInstance(AlreadyInsertActivity.this).create(Inventroy.class);
        linearLayoutManager = new LinearLayoutManager(AlreadyInsertActivity.this);
        recyclerAlready.setLayoutManager(linearLayoutManager);
        Intent intent = getIntent();
        groupId = intent.getIntExtra("groupId", 1);
        sortModelList = (List<SortModel>) intent.getSerializableExtra("data");
        initView();
    }

    private void initView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ProgressDialog progressDialog = new ProgressDialog(AlreadyInsertActivity.this);
                progressDialog.setMessage("数据整合中...");
                progressDialog.show();
                CompanyAllModel model = new CompanyAllModel();
              //  companyAllModels.add();
            }
        }).start();
        insertAdapter = new InsertAdapter(R.layout.item_alreadyinsert, sortModelList);
        insertAdapter.setSettingOnClickListener(new InsertAdapter.SettingOnClickListener() {
            @Override
            public void editOnclick(SortModel sortModel) {
                    Log.e("zws", "sortModel = "+sortModel);
                Intent intent = new Intent(AlreadyInsertActivity.this, EditActivity.class);
                intent.putExtra("sortModel", sortModel);
                intent.putExtra("groupId", groupId);
                startActivityForResult(intent, 100);
            }
        });
        recyclerAlready.setAdapter(insertAdapter);
    }

    /**
     * 将Sortmodel类的数据转换成CompanyAllModel.result数据
     * */
    public static CompanyAllModel.LinkAndAddress changeTYpeBean(SortModel sortModel){
        CompanyAllModel.LinkAndAddress linkAnd = new CompanyAllModel.LinkAndAddress();
        linkAnd.setName(sortModel.getName());
        linkAnd.setStreet(sortModel.getPostal_address_v2().get(0));
        linkAnd.setPhone(sortModel.getMobilePhone().get(0));
        linkAnd.setEmail(sortModel.getEmail_v2());
        return linkAnd;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 110){
            if (requestCode == 100){
                model = (CompanyAllModel) data.getSerializableExtra("companyAll");
            }
        }
    }

    @OnClick(R.id.close_text)
    void closeActiv(View view){
        finish();
    }

    //完成
    @OnClick(R.id.finish_text)
    void uoloadMessage(View view){
        HashMap<Object, Object> hashMap = new HashMap<>();
        List<CompanyAllModel> modelList = new ArrayList<>();
        modelList.add(model);
        hashMap.put("partners", modelList);
        Call<Object> objectCall = inventroy.addPaterner(hashMap);
        objectCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.body() == null)return;
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }
    /**
     * 删除联系人
     * */
    private void deleteContact(int id) {
            String[] RAW_PROJECTION = new String[] { ContactsContract.Data.RAW_CONTACT_ID, };

            String RAW_CONTACTS_WHERE = ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID
                    + "=?"
                    + " and "
                    + ContactsContract.Data.MIMETYPE
                    + "="
                    + "'"
                    + ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE
                    + "'";
        ContentResolver resolver = getContentResolver();
            // 通过分组的id 查询得到RAW_CONTACT_ID
            Cursor cursor = resolver.query(
                    ContactsContract.Data.CONTENT_URI, RAW_PROJECTION,
                    RAW_CONTACTS_WHERE, new String[] { groupId + "" }, "data1 asc");

            while (cursor.moveToNext()) {
                // RAW_CONTACT_ID
                int col = cursor.getColumnIndex("raw_contact_id");
                int raw_contact_id = cursor.getInt(col);
                Log.e("zws", "raw_contact_id:" +
                        raw_contact_id+"  id = "+id);

                Uri dataUri = Uri.parse("content://com.android.contacts/data");
                resolver.delete(dataUri, "raw_contact_id=?", new String[]{id+""});
            }
            cursor.close();
    }
}
