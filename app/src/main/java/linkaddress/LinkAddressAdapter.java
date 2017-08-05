package linkaddress;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zws.ble.contacthuawei.R;

import java.util.List;

import bean.CompanyAllModel;
import contactui.SortModel;
import insert.adapter.InsertAdapter;

/**
 * Created by zws on 2017/8/3.
 */

public class LinkAddressAdapter extends BaseQuickAdapter<CompanyAllModel.LinkAndAddress, BaseViewHolder>{
    public LinkAddressAdapter(@LayoutRes int layoutResId, @Nullable List<CompanyAllModel.LinkAndAddress> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CompanyAllModel.LinkAndAddress item) {
            helper.setText(R.id.link_name, item.getName())
                    .setText(R.id.link_address_tv, "地址："+item.getStreet())
                    .setText(R.id.link_email_tv, item.getEmail())
                    .setText(R.id.link_phone_tv, item.getPhone());
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
        void editOnclick(CompanyAllModel.LinkAndAddress sortModel, int position);
        void deleteLink(CompanyAllModel.LinkAndAddress sortModel, int position);
    }

    private SettingOnClickListener settingOnClickListener;

    public void setSettingOnClickListener(SettingOnClickListener settingOnClickListener){
        this.settingOnClickListener = settingOnClickListener;
    }
}
