package login;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.zws.ble.contacthuawei.MainActivity;
import com.zws.ble.contacthuawei.MyApplication;
import com.zws.ble.contacthuawei.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import bean.LoginBean;
import bean.LoginDatabase;
import bean.LoginResponse;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import contactui.MainContactActivity;
import http.Inventroy;
import http.LoginApi;
import http.RetrofitClient;
import http.RetrofitClientLogin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import util.SharePreferenceUtils;
import util.StringUtils;
import util.UserManager;
import util.ViewUtils;


public class LoginActivity extends Activity {
    private static final String K21_DRIVER_NAME = "com.newland.me.K21Driver";
    @InjectView(R.id.database)
    Button database;
    @InjectView(R.id.email)
    AutoCompleteTextView email;
    @InjectView(R.id.password)
    EditText password;
    @InjectView(R.id.to_login)
    Button toLogin;
    @InjectView(R.id.httpUrl)
    EditText httpUrl;
    @InjectView(R.id.img_delete)
    ImageView imgDelete;
    private String TAG = LoginActivity.class.getSimpleName();
    private int databaseSwitch = 0;
    private LoginApi loginApi;
    private ProgressDialog progressDialog;
    private Inventroy inventoryApi;
    private ArrayList<String> userStrings;
    private Dialog alertDialog;
    private Retrofit retrofit;
    private static final int READ_CONTACTS = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("loading");
        checkOutoLogin();
        httpUrl.setSelection(httpUrl.getText().length());
        //获取联系人权限
        int permission = ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_CONTACTS);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    LoginActivity.this,
                    PERMISSIONS_STORAGE,
                    READ_CONTACTS
            );
        }
    }

    @OnClick(R.id.img_delete)
    void deleteHost(View view){
        httpUrl.setText("");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        ViewUtils.collapseSoftInputMethod(LoginActivity.this, httpUrl);
        ViewUtils.collapseSoftInputMethod(LoginActivity.this, email);
        ViewUtils.collapseSoftInputMethod(LoginActivity.this, password);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    private void checkOutoLogin() {
        int user_id = SharePreferenceUtils.getInt("user_id", -1000, LoginActivity.this);
        if (StringUtils.isNullOrEmpty(RetrofitClientLogin.Url)) {
            httpUrl.setText("http://erp.robotime.com");
        } else {
            httpUrl.setText(RetrofitClientLogin.Url);
        }
        if (user_id != -1000) {
            String url = SharePreferenceUtils.getString("url", "null", LoginActivity.this);
            LoginActivity.this.httpUrl.setText(url);
            RetrofitClientLogin.Url = url;
            loginApi = RetrofitClientLogin.getInstance(LoginActivity.this).create(LoginApi.class);
            String database = SharePreferenceUtils.getString("database", "null", LoginActivity.this);
            LoginActivity.this.database.setText(database);
            String email = SharePreferenceUtils.getString("email", "error", LoginActivity.this);
            LoginActivity.this.email.setText(email);
            String password = SharePreferenceUtils.getString("password", "error", LoginActivity.this);
            LoginActivity.this.password.setText(password);
            toLogin(new View(LoginActivity.this));
        }
    }

    @OnClick(R.id.database)
    void setDatabase(View view) {
        if (StringUtils.isNullOrEmpty(httpUrl.getText().toString())) {
            Toast.makeText(LoginActivity.this, "请输入url地址", Toast.LENGTH_SHORT).show();
            return;
        }
        String URL;
        if (!httpUrl.getText().toString().contains("http://")) {
            URL = "http://" + httpUrl.getText().toString();
            httpUrl.setText(URL);
        } else {
            URL = httpUrl.getText().toString();
        }
        RetrofitClientLogin.Url = URL;
        loginApi = RetrofitClientLogin.getInstance(LoginActivity.this).create(LoginApi.class);
        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("选择数据库");
        builder.setIcon(android.R.drawable.ic_menu_more);
        builder.setCancelable(true);
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        Observable<LoginDatabase> database = loginApi.getDatabase();
        database.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<LoginDatabase>() {
                            @Override
                            public void onCompleted() {
                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }

                            }

                            @Override
                            public void onNext(LoginDatabase loginDatabase) {
                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                if (loginDatabase == null) return;
                                final List<String> res_data = loginDatabase.getRes_data();
                                final String[] databaseArr = res_data.toArray(new String[res_data.size()]);
                                builder.setSingleChoiceItems(databaseArr, databaseArr.length, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        databaseSwitch = i;
                                    }
                                });
                                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        LoginActivity.this.database.setText(res_data.get(databaseSwitch));
                                        SharePreferenceUtils.putString("database", res_data.get(databaseSwitch), LoginActivity.this);
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.setCanceledOnTouchOutside(true);
                                dialog.show();
                            }
                        }
                );
    }

    @OnClick(R.id.to_login)
    void toLogin(View view) {
        String chooseDB = database.getText().toString();
        if (chooseDB.equals("选择数据库")){
            Toast.makeText(LoginActivity.this, "请选择数据库", Toast.LENGTH_SHORT).show();
            return;
        }
        final String emailString = this.email.getText().toString();
        if (StringUtils.isNullOrEmpty(emailString)){
            Toast.makeText(LoginActivity.this, "请输入邮箱", Toast.LENGTH_SHORT).show();
            return;
        }
        final String passwordString = password.getText().toString();
        if (StringUtils.isNullOrEmpty(passwordString)){
            Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        final String url = httpUrl.getText().toString();
        if (StringUtils.isNullOrEmpty(url)){
            Toast.makeText(LoginActivity.this, "请输入url地址", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.show();
        Call<LoginResponse> stringCall = loginApi.toLogin(new LoginBean(emailString, passwordString, chooseDB));
        stringCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() == null || response.body().getResult() == null)
                    return;
                if (response.body().getError() != null) {
                    Toast.makeText(LoginActivity.this, response.body().getError().getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body().getResult().getRes_code() == 1) {
                    UserManager.getSingleton().setUserInfoBean(response.body());//单例存储
                    final int user_id = response.body().getResult().getRes_data().getUser_id();
                    MyApplication.userID = user_id;
                    SharePreferenceUtils.putInt("user_id", user_id, LoginActivity.this);
                    SharePreferenceUtils.putString("email", emailString, LoginActivity.this);
                    SharePreferenceUtils.putString("url", url, LoginActivity.this);
                    SharePreferenceUtils.putString("password", passwordString, LoginActivity.this);
                    SharePreferenceUtils.putInt("partner_id", response.body().getResult().getRes_data().getPartner_id(), LoginActivity.this);
                    SharePreferenceUtils.putString("user_ava", response.body().getResult().getRes_data().getUser_ava(), LoginActivity.this);
                    final String name = response.body().getResult().getRes_data().getName();
                    toMainActivity();
                } else {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(LoginActivity.this, response.body().getResult().getRes_data().getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toMainActivity() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        String model = Build.BRAND;
        if (!model.equals("HUAWEI")){
            Toast.makeText(LoginActivity.this, "此APP功能仅限华为手机使用", Toast.LENGTH_LONG).show();
            return;
        }
        finish();
        Intent intent = new Intent(LoginActivity.this, MainContactActivity.class);
        startActivity(intent);
    }
}
