package insert.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zws.ble.contacthuawei.R;

import java.util.List;

import contactui.SortModel;

/**
 * Created by zws on 2017/8/2.
 */

public class InsertAdapter extends BaseQuickAdapter<SortModel, BaseViewHolder>{
    public InsertAdapter(@LayoutRes int layoutResId, @Nullable List<SortModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final SortModel item) {
        helper.setText(R.id.title_name, item.getName().substring(0,1))
        .setText(R.id.name_product, item.getOrganization())
        .setText(R.id.sales_people, item.getName()+" "+item.getMobilePhone().get(0));
        helper.getView(R.id.setting_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingOnClickListener.editOnclick(item);
            }
        });
    }

    public interface  SettingOnClickListener{
        void editOnclick(SortModel sortModel);
    }

    private SettingOnClickListener settingOnClickListener;

    public void setSettingOnClickListener(SettingOnClickListener settingOnClickListener){
        this.settingOnClickListener = settingOnClickListener;
    }
}
