package linkaddress;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.zws.ble.contacthuawei.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import contactui.SortModel;
import edit.EditActivity;
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
    private int positionId;

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
            public void editOnclick(SortModel sortModel, int position) {
                Intent intent = new Intent(LinkAddressActivity.this, AddMessageActivity.class);
                intent.putExtra("sortModel", sortModel);
                Log.e("zws", "position = "+position);
                positionId = position;
                startActivityForResult(intent, 1000);
            }

            @Override
            public void deleteLink(SortModel sortModel, int position) {

            }
        });
    }

    @OnClick(R.id.image_left)
    void closeThis(View view){
        Intent intent = new Intent();
        intent.putExtra("linkData", sortModel);
        setResult(EditActivity.LINK_AND_ADDRESS_RESULT, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 999){
            if (requestCode == 1000){
                SortModel backSormodel = (SortModel) data.getSerializableExtra("backSormodel");
                Log.e("zws", "type = "+backSormodel.getLinkType());
                sortModel = backSormodel;
                sortModelList.set(positionId, backSormodel);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
