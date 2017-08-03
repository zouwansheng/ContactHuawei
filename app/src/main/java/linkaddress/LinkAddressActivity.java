package linkaddress;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.zws.ble.contacthuawei.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import contactui.SortModel;
import query.QueryListActivity;

/**
 * Created by zws on 2017/8/3.
 */

public class LinkAddressActivity extends Activity {
    @InjectView(R.id.recycler_linkaddress)
    RecyclerView recyclerLinkaddress;
    @InjectView(R.id.image_left)
    ImageView imageLeft;
    private SortModel sortModel;
    private List<SortModel> sortModelList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private LinkAddressAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_linkaddress);
        ButterKnife.inject(this);

        linearLayoutManager = new LinearLayoutManager(LinkAddressActivity.this);
        recyclerLinkaddress.setLayoutManager(linearLayoutManager);
        recyclerLinkaddress.addItemDecoration(new DividerItemDecoration(LinkAddressActivity.this,
                DividerItemDecoration.VERTICAL));

        Intent intent = getIntent();
        sortModel = (SortModel) intent.getSerializableExtra("sortModel");
        sortModelList.add(sortModel);
        initView();
    }

    private void initView() {
        adapter = new LinkAddressAdapter(R.layout.item_linkaddress, sortModelList);
        recyclerLinkaddress.setAdapter(adapter);
        adapter.setSettingOnClickListener(new LinkAddressAdapter.SettingOnClickListener() {
            @Override
            public void editOnclick(SortModel sortModel) {
                Intent intent = new Intent(LinkAddressActivity.this, AddMessageActivity.class);
                intent.putExtra("sortModel", sortModel);
                startActivityForResult(intent, 1000);
            }

            @Override
            public void deleteLink(SortModel sortModel) {

            }
        });
    }

    @OnClick(R.id.image_left)
    void closeThis(View view){
        finish();
    }
}
