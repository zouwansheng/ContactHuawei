package insert.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zws.ble.contacthuawei.R;

import java.util.List;

import bean.CompanyAllModel;
import contactui.SortModel;
import util.StringUtils;

/**
 * Created by zws on 2017/8/2.
 */

public class InsertAdapter extends BaseQuickAdapter<CompanyAllModel, BaseViewHolder>{
    public InsertAdapter(@LayoutRes int layoutResId, @Nullable List<CompanyAllModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CompanyAllModel item) {
        if (item.getCompany_name()!=null && item.getCompany_name().length()>1){
            helper.setText(R.id.title_name, item.getCompany_name().substring(0,2));
        }
        if (item.getMembers()!=null){
            CompanyAllModel.LinkAndAddress linkAndAddress = item.getMembers().get(0);
            if (!StringUtils.isNullOrEmpty(linkAndAddress.getName()) && !StringUtils.isNullOrEmpty(linkAndAddress.getPhone())){
                helper.setText(R.id.sales_people, item.getMembers().get(0).getName()+" "+item.getMembers().get(0).getPhone());
            }else {
                helper.setText(R.id.sales_people, " ");
            }
            helper.setText(R.id.num_company_link, "本客户下联系人数目："+item.getMembers().size());
        }else {
            helper.setText(R.id.num_company_link, "本客户下联系人数目：0");
        }
        helper.setText(R.id.name_product, item.getCompany_name());
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingOnClickListener.editOnclick(item, helper.getPosition());
            }
        });
        /*helper.getView(R.id.setting_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });*/
    }

    public interface  SettingOnClickListener{
        void editOnclick(CompanyAllModel sortModel, int position);
    }

    private SettingOnClickListener settingOnClickListener;

    public void setSettingOnClickListener(SettingOnClickListener settingOnClickListener){
        this.settingOnClickListener = settingOnClickListener;
    }
}
