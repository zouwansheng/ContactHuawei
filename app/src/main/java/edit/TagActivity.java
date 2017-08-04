package edit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zws.ble.contacthuawei.R;

import java.util.HashMap;
import java.util.List;

import bean.TagBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import http.Inventroy;
import http.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import util.StringUtils;

/**
 * Created by zws on 2017/8/3.
 */

public class TagActivity extends Activity {

    @InjectView(R.id.cancel_tag)
    TextView cancelTag;
    @InjectView(R.id.save_tag)
    TextView saveTag;
    @InjectView(R.id.custom_radio)
    RadioButton customRadio;
    @InjectView(R.id.suplier_radio)
    RadioButton suplierRadio;
    @InjectView(R.id.first_radio)
    RadioButton firstRadio;
    @InjectView(R.id.second_radio)
    RadioButton secondRadio;
    @InjectView(R.id.third_radio)
    RadioButton thirdRadio;
    @InjectView(R.id.rating_tag)
    RatingBar ratingTag;
    @InjectView(R.id.custom_type)
    RadioGroup customType;
    @InjectView(R.id.level_type)
    RadioGroup levelType;
    private String type = "";
    private int level = 0;
    private Inventroy inventroy;
    private int type_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tag);
        ButterKnife.inject(this);
        inventroy = RetrofitClient.getInstance(TagActivity.this).create(Inventroy.class);
        initView();
        getTag();
    }

    private void getTag() {
        Call<TagBean> taglist = inventroy.getTaglist(new HashMap());
        taglist.enqueue(new Callback<TagBean>() {
            @Override
            public void onResponse(Call<TagBean> call, Response<TagBean> response) {
                if (response.body() == null || response.body().getResult() == null)return;
                List<TagBean.ResultBean> result = response.body().getResult();
                if (result.size()>=2){
                    customRadio.setText(result.get(1).getName());
                    suplierRadio.setText(result.get(0).getName());
                }
            }

            @Override
            public void onFailure(Call<TagBean> call, Throwable t) {

            }
        });
    }

    private void initView() {
        customType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int checkedRadioButtonId = group.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.custom_radio){
                    type = "customer";
                    type_id = 2;
                }else if (checkedRadioButtonId == R.id.suplier_radio){
                    type = "supplier";
                    type_id = 1;
                }
            }
        });

        levelType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int checkedRadioButtonId = group.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.first_radio){
                    level = 1;
                }else if (checkedRadioButtonId == R.id.second_radio){
                    level = 2;
                }else if (checkedRadioButtonId == R.id.third_radio){
                    level = 3;
                }
            }
        });
    }

    //取消
    @OnClick(R.id.cancel_tag)
    void cancelTag(View view){
        finish();
    }

    //保存
    @OnClick(R.id.save_tag)
    void saveTag(View view){
        float rating = ratingTag.getRating();
        if (StringUtils.isNullOrEmpty(type)){
            Toast.makeText(TagActivity.this, "请选择类型", Toast.LENGTH_SHORT).show();
            return;
        }
        if (level == 0){
            Toast.makeText(TagActivity.this, "请选择等级", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("type", type);
        intent.putExtra("type_id", type_id);
        intent.putExtra("level", level);
        intent.putExtra("rating", rating);
        setResult(EditActivity.SELECT_TAG_RESULT, intent);
        finish();
    }
}
