package query;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zws.ble.contacthuawei.R;

import java.util.List;

import bean.OriginBean;

/**
 * Created by zws on 2017/8/3.
 */

public class QueryOriginAdapter extends BaseQuickAdapter<OriginBean.ResultBean, BaseViewHolder>{
    public QueryOriginAdapter(@LayoutRes int layoutResId, @Nullable List<OriginBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OriginBean.ResultBean item) {
        helper.setText(R.id.china_tv, item.getName());
    }
}
