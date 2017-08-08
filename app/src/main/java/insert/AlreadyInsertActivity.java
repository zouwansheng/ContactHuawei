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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.zws.ble.contacthuawei.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bean.CommitAllMessageBean;
import bean.CompanyAllModel;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import contactui.MainContactActivity;
import contactui.SortModel;
import edit.EditActivity;
import http.Inventroy;
import http.RetrofitClient;
import http.RetrofitClientLogin;
import insert.adapter.InsertAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import util.StringUtils;

/**
 * Created by zws on 2017/8/1.
 */

public class AlreadyInsertActivity extends Activity {
    @InjectView(R.id.close_text)
    TextView closeText;
    @InjectView(R.id.finish_text)
    TextView finishText;
    @InjectView(R.id.recycler_already)
    RecyclerView recyclerAlready;
    @InjectView(R.id.edit_search)
    SearchView editSearch;
    private LinearLayoutManager linearLayoutManager;
    private List<SortModel> sortModelList;
    private InsertAdapter insertAdapter;
    private int groupId;
    private CompanyAllModel model;
    private Inventroy inventroy;
    private List<CompanyAllModel> companyAllModels = new ArrayList<>();
    private List<CompanyAllModel> allList = new ArrayList<>();
    private int position;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_alreadyinsert);
        ButterKnife.inject(this);

        RetrofitClient.Url = RetrofitClientLogin.Url;
        inventroy = RetrofitClient.getInstance(AlreadyInsertActivity.this).create(Inventroy.class);
        linearLayoutManager = new LinearLayoutManager(AlreadyInsertActivity.this);
        recyclerAlready.setLayoutManager(linearLayoutManager);
        Intent intent = getIntent();
        groupId = intent.getIntExtra("groupId", 1);
        sortModelList = (List<SortModel>) intent.getSerializableExtra("data");
        initView();
    }

    /**
     * 针对需求  按照公司分类
     * 创建一个空的List
     * add第一个元素
     * 之后每添加一个元素  拿元素的公司名字与已经添加的做比较，如果公司名字完全相同，则添加进同一个公司里面
     */
    private void initView() {
        progressDialog = new ProgressDialog(AlreadyInsertActivity.this);
        progressDialog.setMessage("数据整合中...");
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*String organization = sortModelList.get(0).getOrganization();
                CompanyAllModel.LinkAndAddress linkAndAddress = changeTYpeBean(sortModelList.get(0));
                List<CompanyAllModel.LinkAndAddress> list = new ArrayList<>();
                list.add(linkAndAddress);
                for (int i = 1; i < sortModelList.size() - 1; i++) {
                    String organization1 = sortModelList.get(i).getOrganization();
                    if (organization.equals(organization1)){
                        list.add(changeTYpeBean(sortModelList.get(i)));
                        sortModelList.remove(sortModelList.get(i));
                    }
                }*/
                for (int i = 0; i < sortModelList.size(); i++) {
                    String organization = sortModelList.get(i).getOrganization();
                    CompanyAllModel.LinkAndAddress linkAndAddress = changeTYpeBean(sortModelList.get(i));
                    List<CompanyAllModel.LinkAndAddress> list = new ArrayList<>();
                    list.add(linkAndAddress);
                    for (int j = i + 1; j < sortModelList.size(); j++) {
                        if (organization == null)
                            break;
                        String organization1 = sortModelList.get(j).getOrganization();
                        if (organization.equals(organization1)) {
                            list.add(changeTYpeBean(sortModelList.get(j)));
                            sortModelList.remove(sortModelList.get(j));
                        }
                    }
                    CompanyAllModel bean = new CompanyAllModel();
                    bean.setMembers(list);
                    bean.setCompany_name(organization);
                    companyAllModels.add(bean);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        insertAdapter = new InsertAdapter(R.layout.item_alreadyinsert, companyAllModels);
                        allList = companyAllModels;
                        insertAdapter.setSettingOnClickListener(new InsertAdapter.SettingOnClickListener() {
                            @Override
                            public void editOnclick(CompanyAllModel sortModel, int index) {
                                Intent intent = new Intent(AlreadyInsertActivity.this, EditActivity.class);
                                intent.putExtra("sortModel", sortModel);
                                intent.putExtra("groupId", groupId);
                                startActivityForResult(intent, 100);
                                position = index;
                            }
                        });
                        recyclerAlready.setAdapter(insertAdapter);
                        progressDialog.dismiss();
                    }
                });
            }
        }).start();
        editSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fillData(newText);
                return false;
            }
        });
    }

    //搜索功能
    private void fillData(String newText) {
        if (StringUtils.isNullOrEmpty(newText)) {
            companyAllModels = allList;
        } else {
            List<CompanyAllModel> filterDateList = new ArrayList<>();
            for (CompanyAllModel bean : allList) {
                if (bean.getCompany_name() != null && bean.getCompany_name().contains(newText)) {
                    filterDateList.add(bean);
                }
            }
            companyAllModels = filterDateList;
        }
        insertAdapter.setNewData(companyAllModels);
        insertAdapter.notifyDataSetChanged();
    }

    /**
     * 将Sortmodel类的数据转换成CompanyAllModel.result数据
     */
    public static CompanyAllModel.LinkAndAddress changeTYpeBean(SortModel sortModel) {
        CompanyAllModel.LinkAndAddress linkAnd = new CompanyAllModel.LinkAndAddress();
        linkAnd.setName(sortModel.getName());
        if (sortModel.getPostal_address_v2().size() > 0) {
            linkAnd.setStreet(sortModel.getPostal_address_v2().get(0));
        }
        if (sortModel.getMobilePhone().size() > 0) {
            linkAnd.setPhone(sortModel.getMobilePhone().get(0));
        }
        linkAnd.setEmail(sortModel.getEmail_v2());
        return linkAnd;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 110) {
            if (requestCode == 100) {
                String isDelete = data.getStringExtra("isDelete");
                if (isDelete.equals("no")) {
                    model = (CompanyAllModel) data.getSerializableExtra("companyAll");
                    companyAllModels.set(position, model);
                    insertAdapter.notifyDataSetChanged();
                } else {
                    companyAllModels.remove(position);
                    insertAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @OnClick(R.id.close_text)
    void closeActiv(View view) {
        finish();
    }

    //完成
    @OnClick(R.id.finish_text)
    void uoloadMessage(View view) {
        if (companyAllModels.size() == 0 || companyAllModels == null) {
            Toast.makeText(AlreadyInsertActivity.this, "没有上传数据", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("上传中。。。");
        progressDialog.show();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("partners", companyAllModels);
        Call<CommitAllMessageBean> objectCall = inventroy.addPaterner(hashMap);
        objectCall.enqueue(new Callback<CommitAllMessageBean>() {
            @Override
            public void onResponse(Call<CommitAllMessageBean> call, Response<CommitAllMessageBean> response) {
                progressDialog.dismiss();
                if (response.body() == null) return;
                if (response.body().getError() != null) {
                    Toast.makeText(AlreadyInsertActivity.this, response.body().getError().getData().getMessage(), Toast.LENGTH_SHORT).show();
                } else if (response.body().isResult()) {
                    Toast.makeText(AlreadyInsertActivity.this, "上传数据成功", Toast.LENGTH_LONG).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < sortModelList.size(); i++) {
                                int raw_contact_id = sortModelList.get(i).getRaw_contact_id();
                                deleteContact(raw_contact_id);
                            }
                            Intent intent = new Intent();
                            setResult(MainContactActivity.UPDATE_CONTACT_RESULT, intent);
                            finish();
                        }
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<CommitAllMessageBean> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    /**
     * 删除联系人
     */
    private void deleteContact(int id) {
        String[] RAW_PROJECTION = new String[]{ContactsContract.Data.RAW_CONTACT_ID,};

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
                RAW_CONTACTS_WHERE, new String[]{groupId + ""}, "data1 asc");

        while (cursor.moveToNext()) {
            // RAW_CONTACT_ID
            int col = cursor.getColumnIndex("raw_contact_id");
            int raw_contact_id = cursor.getInt(col);
            Log.e("zws", "raw_contact_id:" +
                    raw_contact_id + "  id = " + id);

            Uri dataUri = Uri.parse("content://com.android.contacts/data");
            resolver.delete(dataUri, "raw_contact_id=?", new String[]{id + ""});
        }
        cursor.close();
    }
}
