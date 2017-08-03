package edit;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zws.ble.contacthuawei.R;

import java.util.HashMap;
import java.util.List;

import bean.ComponyQueryBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import contactui.SortModel;
import dialog.CompanyDialog;
import http.Inventroy;
import http.RetrofitClient;
import linkaddress.LinkAddressActivity;
import query.QueryListActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import util.StringUtils;

/**
 * Created by zws on 2017/8/2.
 */

public class EditActivity extends Activity {
    private static final int SELECT_COUNTRY = 1;
    public static final int SELECT_COUNTRY_RESULT = 2;
    private static final int SELECT_CHANNEL = 3;
    public static final int SELECT_CHANNEL_RESULT = 4;
    private static final int SELECT_ORIGIN = 5;
    public static final int SELECT_ORIGIN_RESULT = 6;
    private static final int SELECT_TEAM = 7;
    public static final int SELECT_TEAM_RESULT = 8;
    private static final int SELECT_SALEMAN = 9;
    public static final int SELECT_SALEMAN_RESULT = 10;
    private static final int SELECT_TAG = 11;
    public static final int SELECT_TAG_RESULT = 12;

    @InjectView(R.id.cancel_text)
    TextView cancelText;
    @InjectView(R.id.save_text)
    TextView saveText;
    @InjectView(R.id.btn_query)
    Button btnQuery;
    @InjectView(R.id.product_name)
    EditText productName;
    @InjectView(R.id.country_name)
    TextView countryName;
    @InjectView(R.id.from_where)
    TextView fromWhere;
    @InjectView(R.id.channel_from)
    TextView channelFrom;
    @InjectView(R.id.sale_team)
    TextView saleTeam;
    @InjectView(R.id.sale_worker)
    TextView saleWorker;
    @InjectView(R.id.like_product)
    TextView likeProduct;
    @InjectView(R.id.link_address)
    TextView linkAddress;
    @InjectView(R.id.delete_this)
    Button deleteThis;
    @InjectView(R.id.tag_relative)
    RelativeLayout tagRelative;
    @InjectView(R.id.tv_customer_tag)
    TextView tvCustomerTag;
    @InjectView(R.id.tv_level_tag)
    TextView tvLevelTag;
    @InjectView(R.id.rating_tag_activity)
    RatingBar ratingTagActivity;

    private SortModel sortModel;
    private Inventroy inventroy;
    private ProgressDialog progressDialog;
    private int groupId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit);
        ButterKnife.inject(this);
        RetrofitClient.Url = "http://192.168.2.4:8069";

        inventroy = RetrofitClient.getInstance(EditActivity.this).create(Inventroy.class);
        Intent intent = getIntent();
        sortModel = (SortModel) intent.getSerializableExtra("sortModel");
        groupId = intent.getIntExtra("groupId", 1);
        progressDialog = new ProgressDialog(EditActivity.this);
        progressDialog.setMessage("加载中...");
        initView();
    }

    private void initView() {
        if (!StringUtils.isNullOrEmpty(sortModel.getOrganization())) {
            productName.setText(sortModel.getOrganization());
            productName.setSelection(productName.getText().length());
        }
        ratingTagActivity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                return true;
            }
        });
    }

    //取消
    @OnClick(R.id.cancel_text)
    void cancelEdit(View view) {
        finish();
    }

    //国家
    @OnClick(R.id.country_name)
    void getCountry(View view) {
        Intent intent = new Intent(EditActivity.this, QueryListActivity.class);
        intent.putExtra("type", "country");
        startActivityForResult(intent, SELECT_COUNTRY);
    }

    //渠道
    @OnClick(R.id.channel_from)
    void getFrom(View view) {
        Intent intent = new Intent(EditActivity.this, QueryListActivity.class);
        intent.putExtra("type", "channel");
        startActivityForResult(intent, SELECT_CHANNEL);
    }

    //来源
    @OnClick(R.id.from_where)
    void getOrigin(View view) {
        Intent intent = new Intent(EditActivity.this, QueryListActivity.class);
        intent.putExtra("type", "origin");
        startActivityForResult(intent, SELECT_ORIGIN);
    }

    //销售团队
    @OnClick(R.id.sale_team)
    void getTeam(View view) {
        Intent intent = new Intent(EditActivity.this, QueryListActivity.class);
        intent.putExtra("type", "team");
        startActivityForResult(intent, SELECT_TEAM);
    }

    //销售员
    @OnClick(R.id.sale_worker)
    void getSaleman(View view) {
        Intent intent = new Intent(EditActivity.this, QueryListActivity.class);
        intent.putExtra("type", "saleman");
        startActivityForResult(intent, SELECT_SALEMAN);
    }

    //标签
    @OnClick(R.id.tag_relative)
    void tagSelected(View view) {
        Intent intent = new Intent(EditActivity.this, TagActivity.class);
        startActivityForResult(intent, SELECT_TAG);
    }

    //联系人&地址
    @OnClick(R.id.link_address)
    void setLinkAddress(View view){
        Intent intent = new Intent(EditActivity.this, LinkAddressActivity.class);
        intent.putExtra("sortModel", sortModel);
        startActivity(intent);
    }

    //删除
    @OnClick(R.id.delete_this)
    void setDeleteThis(View view){
        new AlertDialog.Builder(EditActivity.this).setMessage("确定删除客户？")
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int raw_contact_id = sortModel.getRaw_contact_id();
                        //删除联系人
                        deleteContact(raw_contact_id);
                    }
                }).start();
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
    //查询
    @OnClick(R.id.btn_query)
    void btnQuery(View view) {
        progressDialog.show();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("name", productName.getText().toString());
        Call<ComponyQueryBean> compbyName = inventroy.getCompbyName(hashMap);
        compbyName.enqueue(new Callback<ComponyQueryBean>() {
            @Override
            public void onResponse(Call<ComponyQueryBean> call, Response<ComponyQueryBean> response) {
                progressDialog.dismiss();
                if (response.body() == null || response.body().getResult() == null) return;
                List<ComponyQueryBean.ResultBean> result = response.body().getResult();
                CompanyDialog dialog = new CompanyDialog(EditActivity.this, result);
                dialog.show();
                dialog.sendNameCompany(new CompanyDialog.GetCompany() {
                    @Override
                    public void getCompanyName(String name) {
                        productName.setText(name);
                    }
                });
            }

            @Override
            public void onFailure(Call<ComponyQueryBean> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == SELECT_COUNTRY_RESULT) {
            if (requestCode == SELECT_COUNTRY) {
                String nameProduct = data.getStringExtra("nameProduct");
                countryName.setText(nameProduct);
            }
        } else if (resultCode == SELECT_CHANNEL_RESULT) {
            if (requestCode == SELECT_CHANNEL) {
                String nameChannel = data.getStringExtra("channelName");
                channelFrom.setText(nameChannel);
            }
        } else if (resultCode == SELECT_ORIGIN_RESULT) {
            if (requestCode == SELECT_ORIGIN) {
                String nameOrigin = data.getStringExtra("originName");
                fromWhere.setText(nameOrigin);
            }
        } else if (resultCode == SELECT_TEAM_RESULT) {
            if (requestCode == SELECT_TEAM) {
                String nameOrigin = data.getStringExtra("teamName");
                saleTeam.setText(nameOrigin);
            }
        } else if (resultCode == SELECT_SALEMAN_RESULT) {
            if (requestCode == SELECT_SALEMAN) {
                String nameOrigin = data.getStringExtra("salemanName");
                saleWorker.setText(nameOrigin);
            }
        } else if (resultCode == SELECT_TAG_RESULT) {
            if (requestCode == SELECT_TAG) {
                String type = data.getStringExtra("type");
                String level = data.getStringExtra("level");
                float rating = data.getFloatExtra("rating", 5);
                tvCustomerTag.setText(type);
                tvLevelTag.setText(level);
                ratingTagActivity.setVisibility(View.VISIBLE);
                ratingTagActivity.setRating(rating);
            }
        }

    }
}
