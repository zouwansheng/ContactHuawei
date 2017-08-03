package query;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zws.ble.contacthuawei.R;

import java.util.List;

import bean.CountryBean;
import contactui.SortModel;

/**
 * Created by zws on 2017/8/3.
 */

public class QueryAdapter extends BaseQuickAdapter<CountryBean.ResultBean, BaseViewHolder> {


    public QueryAdapter(@LayoutRes int layoutResId, @Nullable List<CountryBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CountryBean.ResultBean item) {
        helper.setText(R.id.china_tv, item.getName());
    }

}
