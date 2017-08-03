package query;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zws.ble.contacthuawei.R;

import java.util.List;

import bean.SalemanBean;

/**
 * Created by zws on 2017/8/3.
 */

public class QuerySalemanAdapter extends BaseQuickAdapter<SalemanBean.ResultBean, BaseViewHolder> {
    public QuerySalemanAdapter(@LayoutRes int layoutResId, @Nullable List<SalemanBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SalemanBean.ResultBean item) {
        helper.setText(R.id.china_tv, item.getName());
    }
}
