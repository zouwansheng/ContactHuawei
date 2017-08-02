package edit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zws.ble.contacthuawei.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import contactui.SortModel;
import http.RetrofitClient;
import util.StringUtils;

/**
 * Created by zws on 2017/8/2.
 */

public class EditActivity extends Activity {
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

    private SortModel sortModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit);
        ButterKnife.inject(this);
        RetrofitClient.Url = "http://192.168.2.4:8069";

        Intent intent = getIntent();
        sortModel = (SortModel) intent.getSerializableExtra("sortModel");
        initView();
    }

    private void initView() {
        if (!StringUtils.isNullOrEmpty(sortModel.getOrganization())){
            productName.setText(sortModel.getOrganization());
        }
    }

    @OnClick(R.id.cancel_text)
    void cancelEdit(View view){
        finish();
    }
}
