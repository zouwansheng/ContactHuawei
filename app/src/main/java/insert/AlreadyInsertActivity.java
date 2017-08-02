package insert;

import android.app.Activity;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import contactui.ClearEditText;
import contactui.MainContactActivity;
import contactui.SortModel;
import edit.EditActivity;
import insert.adapter.InsertAdapter;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_alreadyinsert);
        ButterKnife.inject(this);

        linearLayoutManager = new LinearLayoutManager(AlreadyInsertActivity.this);
        recyclerAlready.setLayoutManager(linearLayoutManager);
        Intent intent = getIntent();
        groupId = intent.getIntExtra("groupId", 1);
        sortModelList = (List<SortModel>) intent.getSerializableExtra("data");
        initView();
    }

    private void initView() {
        insertAdapter = new InsertAdapter(R.layout.item_alreadyinsert, sortModelList);
        insertAdapter.setSettingOnClickListener(new InsertAdapter.SettingOnClickListener() {
            @Override
            public void editOnclick(SortModel sortModel) {
                    Log.e("zws", "sortModel = "+sortModel);
                Intent intent = new Intent(AlreadyInsertActivity.this, EditActivity.class);
                intent.putExtra("sortModel", sortModel);
                startActivity(intent);
            }
        });
        recyclerAlready.setAdapter(insertAdapter);
    }

    @OnClick(R.id.close_text)
    void closeActiv(View view){
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < sortModelList.size(); i++) {
                    int raw_contact_id = sortModelList.get(i).getRaw_contact_id();
                    //删除联系人
                    deleteContact(raw_contact_id);
                }
            }
        }).start();
        Intent intent = new Intent(AlreadyInsertActivity.this, MainContactActivity.class);
        startActivity(intent);*/
        finish();
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
