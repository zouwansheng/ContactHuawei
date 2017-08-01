package com.zws.ble.contacthuawei;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import contactui.MainContactActivity;

public class MainActivity extends AppCompatActivity {


    @InjectView(R.id.btn_insert_contact)
    Button btnInsertContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);


    }

    @OnClick(R.id.btn_insert_contact)
    void insertContact(View view){
       /* Uri uri = Uri.parse("content://contacts/people");
        Intent intent = new Intent(Intent.ACTION_PICK, uri);
        startActivityForResult(intent, 0);*/
       Intent intent = new Intent(MainActivity.this, MainContactActivity.class);
        startActivity(intent);
    }
}
