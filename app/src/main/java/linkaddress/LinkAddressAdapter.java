package linkaddress;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zws.ble.contacthuawei.R;

import java.util.List;

import contactui.SortModel;
import insert.adapter.InsertAdapter;

/**
 * Created by zws on 2017/8/3.
 */

public class LinkAddressAdapter extends BaseQuickAdapter<SortModel, BaseViewHolder>{
    public LinkAddressAdapter(@LayoutRes int layoutResId, @Nullable List<SortModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final SortModel item) {
            helper.setText(R.id.link_name, item.getName())
                    .setText(R.id.link_address_tv, "地址："+item.getPostal_address_v2().get(0))
                    .setText(R.id.link_email_tv, item.getEmail_v2())
                    .setText(R.id.link_phone_tv, item.getMobilePhone().get(0));
            helper.getView(R.id.image_edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    settingOnClickListener.editOnclick(item, helper.getPosition());
                }
            });
        helper.getView(R.id.img_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingOnClickListener.deleteLink(item, helper.getPosition());
            }
        });
    }

    public interface  SettingOnClickListener{
        void editOnclick(SortModel sortModel, int position);
        void deleteLink(SortModel sortModel, int position);
    }

    private SettingOnClickListener settingOnClickListener;

    public void setSettingOnClickListener(SettingOnClickListener settingOnClickListener){
        this.settingOnClickListener = settingOnClickListener;
    }
}
