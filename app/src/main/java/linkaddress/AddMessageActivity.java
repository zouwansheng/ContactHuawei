package linkaddress;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zws.ble.contacthuawei.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import contactui.SortModel;
import util.StringUtils;

/**
 * Created by zws on 2017/8/3.
 */

public class AddMessageActivity extends Activity {
    @InjectView(R.id.cancel_text)
    TextView cancelText;
    @InjectView(R.id.save_text)
    TextView saveText;
    @InjectView(R.id.link_type_tv)
    TextView linkTypeTv;
    @InjectView(R.id.edit_name)
    EditText editName;
    @InjectView(R.id.edit_phone)
    EditText editPhone;
    @InjectView(R.id.edit_email)
    EditText editEmail;
    @InjectView(R.id.edit_address)
    EditText editAddress;

    private SortModel sortModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmessage);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        sortModel = (SortModel) intent.getSerializableExtra("sortModel");
        initView();
    }

    private void initView() {
        editName.setText(sortModel.getName());
        editAddress.setText(sortModel.getPostal_address_v2().get(0));
        editEmail.setText(sortModel.getEmail_v2());
        editPhone.setText(sortModel.getPostal_address_v2().get(0));
    }

    @OnClick(R.id.cancel_text)
    void cancelTv(View view){
        finish();
    }

    @OnClick(R.id.save_text)
    void save(View view){
        if (StringUtils.isNullOrEmpty(editName.getText().toString())){
            Toast.makeText(AddMessageActivity.this, "请输入名字", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isNullOrEmpty(editPhone.getText().toString())){
            Toast.makeText(AddMessageActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
