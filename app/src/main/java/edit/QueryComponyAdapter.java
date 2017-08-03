package edit;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zws.ble.contacthuawei.R;

import java.util.List;

import bean.ComponyQueryBean;

/**
 * Created by zws on 2017/8/3.
 */

public class QueryComponyAdapter extends BaseQuickAdapter<ComponyQueryBean.ResultBean, BaseViewHolder> {
    public QueryComponyAdapter(@LayoutRes int layoutResId, @Nullable List<ComponyQueryBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ComponyQueryBean.ResultBean item) {
        helper.setText(R.id.china_tv, item.getName());
    }
}
