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
import android.widget.Toast;

import com.zws.ble.contacthuawei.R;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bean.CompanyAllModel;
import bean.ComponyQueryBean;
import bean.LoginResponse;
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
import util.SharePreferenceUtils;
import util.StringUtils;
import util.UserManager;

import static android.R.attr.level;

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
    private static final int LINK_AND_ADDRESS = 13;
    public static final int LINK_AND_ADDRESS_RESULT = 14;
    private static final int SELECT_LIKE_PRODUCT = 15;
    public static final int SELECT_LIKE_PRODUCT_RESULT = 16;

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

    private CompanyAllModel sortModel;
    private Inventroy inventroy;
    private ProgressDialog progressDialog;
    private int groupId;
    private int country_id;
    private int source_id;//渠道
    private int src_id;//来源
    private int team_id = -111;//销售团队
    private int partner_id = -110;//销售员
    private int series_id;//感兴趣的产品
    private int partner_level;//等级
    private int type_id;//客户供应商id
    private int company_id;//公司id
    private List<CompanyAllModel.LinkAndAddress> linkAndAddresses = new ArrayList<>();
    private String paterner_type;//客户类型

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit);
        ButterKnife.inject(this);
        //RetrofitClient.Url = "http://192.168.2.4:8069";

        inventroy = RetrofitClient.getInstance(EditActivity.this).create(Inventroy.class);
        Intent intent = getIntent();
        sortModel = (CompanyAllModel) intent.getSerializableExtra("sortModel");
        linkAndAddresses = sortModel.getMembers();
        Log.e("zws", " 联系人地址 = "+linkAndAddresses.toString());
        groupId = intent.getIntExtra("groupId", 1);
        progressDialog = new ProgressDialog(EditActivity.this);
        progressDialog.setMessage("加载中...");
        initView();
    }

    private void initView() {
        if (!StringUtils.isNullOrEmpty(sortModel.getCompany_name())) {
            productName.setText(sortModel.getCompany_name());
            productName.setSelection(productName.getText().length());
        }
        linkAddress.setText(linkAndAddresses.size()+"");
        LoginResponse userInfoBean = UserManager.getSingleton().getUserInfoBean();
        if (userInfoBean!=null){
            if (userInfoBean.getResult().getRes_data().getTeam() == null){
                return;
            }
            saleTeam.setText(userInfoBean.getResult().getRes_data().getTeam().getTeam_name());
            saleWorker.setText(userInfoBean.getResult().getRes_data().getName());
        }
        ratingTagActivity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                return true;
            }
        });
        btnQuery(new View(EditActivity.this));
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

    //感兴趣的产品
    @OnClick(R.id.like_product)
    void likeProduct(View view) {
        Intent intent = new Intent(EditActivity.this, QueryListActivity.class);
        intent.putExtra("type", "likeProduct");
        startActivityForResult(intent, SELECT_LIKE_PRODUCT);
    }

    //联系人&地址
    @OnClick(R.id.link_address)
    void setLinkAddress(View view){
        Intent intent = new Intent(EditActivity.this, LinkAddressActivity.class);
        intent.putExtra("sortModel", (Serializable) linkAndAddresses);
        startActivityForResult(intent, LINK_AND_ADDRESS);
    }

    //删除
    @OnClick(R.id.delete_this)
    void setDeleteThis(View view){
        new AlertDialog.Builder(EditActivity.this)
                .setMessage("确定删除本客户？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.putExtra("isDelete", "yes");
                setResult(110, intent);
                finish();
            }
        }).show();
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
                if (response.body() == null) return;
                if (response.body().getResult() == null){
                    Toast.makeText(EditActivity.this, "未找到相似公司名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<ComponyQueryBean.ResultBean> result = response.body().getResult();
                CompanyDialog dialog = new CompanyDialog(EditActivity.this, result);
                dialog.show();
                dialog.sendNameCompany(new CompanyDialog.GetCompany() {
                    @Override
                    public void getCompanyName(String name, int companyId) {
                        productName.setText(name);
                        company_id = companyId;
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
                country_id = data.getIntExtra("country_id", 3);
                countryName.setText(nameProduct);
            }
        } else if (resultCode == SELECT_CHANNEL_RESULT) {
            if (requestCode == SELECT_CHANNEL) {
                String nameChannel = data.getStringExtra("channelName");
                source_id = data.getIntExtra("source_id", 1);
                channelFrom.setText(nameChannel);
            }
        } else if (resultCode == SELECT_ORIGIN_RESULT) {
            if (requestCode == SELECT_ORIGIN) {
                String nameOrigin = data.getStringExtra("originName");
                src_id = data.getIntExtra("src_id", 1);
                fromWhere.setText(nameOrigin);
            }
        } else if (resultCode == SELECT_TEAM_RESULT) {
            if (requestCode == SELECT_TEAM) {
                String nameOrigin = data.getStringExtra("teamName");
                team_id = data.getIntExtra("team_id", 1);
                saleTeam.setText(nameOrigin);
            }
        } else if (resultCode == SELECT_SALEMAN_RESULT) {
            if (requestCode == SELECT_SALEMAN) {
                String nameOrigin = data.getStringExtra("salemanName");
                partner_id = data.getIntExtra("partner_id", 1);
                saleWorker.setText(nameOrigin);
            }
        } else if (resultCode == SELECT_TAG_RESULT) {
            if (requestCode == SELECT_TAG) {
                paterner_type = data.getStringExtra("type");
                type_id = data.getIntExtra("type_id", 1);
                partner_level = data.getIntExtra("level", 1);
                float rating = data.getFloatExtra("rating", 5);
                if (paterner_type.equals("customer")){
                    tvCustomerTag.setText("客户");
                }else if (paterner_type.equals("supplier")){
                    tvCustomerTag.setText("供应商");
                }
                tvCustomerTag.setVisibility(View.VISIBLE);
                tvLevelTag.setText(partner_level+"st");
                tvLevelTag.setVisibility(View.VISIBLE);
                ratingTagActivity.setVisibility(View.VISIBLE);
                ratingTagActivity.setRating(rating);
            }
        }else if (resultCode == LINK_AND_ADDRESS_RESULT){
            if (requestCode == LINK_AND_ADDRESS){
                linkAndAddresses = (List<CompanyAllModel.LinkAndAddress>) data.getSerializableExtra("linkData");
                sortModel.setMembers(linkAndAddresses);
                linkAddress.setText(linkAndAddresses.size()+"");
            }
        }else if (resultCode == SELECT_LIKE_PRODUCT_RESULT){
            if (requestCode == SELECT_LIKE_PRODUCT){
                series_id = data.getIntExtra("series_id", 1);
                String likeName = data.getStringExtra("likeName");
                likeProduct.setText(likeName);
            }
        }
    }

    //保存
    @OnClick(R.id.save_text)
    void saveEdit(View view){
        String companyName = productName.getText().toString();
        String country = countryName.getText().toString();
        String team = saleTeam.getText().toString();
        String saleman = saleWorker.getText().toString();
        String custoTYpe = tvCustomerTag.getText().toString();
        float rating = ratingTagActivity.getRating();
        if (StringUtils.isNullOrEmpty(companyName)){
            Toast.makeText(EditActivity.this, "请选择公司名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isNullOrEmpty(team) || "请选择销售团队".equals(team)){
            Toast.makeText(EditActivity.this, "请选择销售团队", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isNullOrEmpty(saleman) || "请选择".equals(saleman)){
            Toast.makeText(EditActivity.this, "请选择销售员", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isNullOrEmpty(custoTYpe)){
            Toast.makeText(EditActivity.this, "请设置标签", Toast.LENGTH_SHORT).show();
            return;
        }
        CompanyAllModel allModel = new CompanyAllModel();
        allModel.setSource_id(source_id);
        allModel.setCompany_name(companyName);
        allModel.setCountry_id(country_id);
        allModel.setCrm_source_id(src_id);
        // TODO: 2017/8/4 需要判断
        allModel.setStar_cnt((int) ratingTagActivity.getRating());
        allModel.setPartner_type(paterner_type);
        if (team_id !=-111){
            allModel.setSaleteam_id(team_id);
        }else {
            if (UserManager.getSingleton().getUserInfoBean().getResult().getRes_data().getTeam()!=null)
                allModel.setSaleteam_id(UserManager.getSingleton().getUserInfoBean().getResult().getRes_data().getTeam().getTeam_id());
        }
        if (partner_id != -110){
            allModel.setSaleman_id(partner_id);
        }else {
            if (UserManager.getSingleton().getUserInfoBean()!=null)
                allModel.setSaleman_id(UserManager.getSingleton().getUserInfoBean().getResult().getRes_data().getUser_id());
        }
        allModel.setPartner_lv(partner_level);
        allModel.setTag_list(type_id);
        allModel.setCompany_id(company_id);
        // TODO: 2017/8/4 需要判断
        // TODO: 2017/8/4 需要判断
        if (linkAndAddresses!=null && linkAndAddresses.size()>0){
            allModel.setMembers(linkAndAddresses);
        }
        Intent intent = new Intent();
        intent.putExtra("companyAll", allModel);
        intent.putExtra("isDelete", "no");
        setResult(110, intent);
        finish();
    }
}
