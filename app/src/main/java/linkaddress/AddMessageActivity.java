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

import bean.CompanyAllModel;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import contactui.SortModel;
import dialog.LinkDialog;
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

    private CompanyAllModel.LinkAndAddress sortModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmessage);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        sortModel = (CompanyAllModel.LinkAndAddress) intent.getSerializableExtra("sortModel");
        initView();
    }

    private void initView() {
        editName.setText(sortModel.getName());
        editAddress.setText(sortModel.getStreet());
        editEmail.setText(sortModel.getEmail());
        editPhone.setText(sortModel.getPhone());
        editName.setSelection(editName.getText().length());
        editAddress.setSelection(editAddress.getText().length());
        editEmail.setSelection(editEmail.getText().length());
        editPhone.setSelection(editPhone.getText().length());
    }

    @OnClick(R.id.cancel_text)
    void cancelTv(View view){
        finish();
    }

    @OnClick(R.id.save_text)
    void save(View view){
        if (StringUtils.isNullOrEmpty(editName.getText().toString())){
            Toast.makeText(AddMessageActivity.this, "请输入名字！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isNullOrEmpty(editPhone.getText().toString())){
            Toast.makeText(AddMessageActivity.this, "请输入手机号！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isNullOrEmpty(linkTypeTv.getText().toString())){
            Toast.makeText(AddMessageActivity.this, "请选择联系人类型！", Toast.LENGTH_SHORT).show();
            return;
        }
        sortModel.setName(editName.getText().toString());
        sortModel.setPhone(editPhone.getText().toString());
        sortModel.setType(linkTypeTv.getText().toString());
        sortModel.setEmail(editEmail.getText().toString());
        sortModel.setStreet(editAddress.getText().toString());
        Intent intent = new Intent();
        intent.putExtra("backSormodel", sortModel);
        setResult(999, intent);
        finish();
    }

    @OnClick(R.id.link_type_tv)
    void setLinkTypeTv(View view){
        LinkDialog dialog = new LinkDialog(AddMessageActivity.this);
        dialog.show();
        dialog.sendNameLink(new LinkDialog.SetTypeLink() {
            @Override
            public void setLinkName(String name) {
                linkTypeTv.setText(name);
            }
        });
    }
}
