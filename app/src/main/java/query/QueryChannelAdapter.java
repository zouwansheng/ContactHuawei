package query;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zws.ble.contacthuawei.R;

import java.util.List;

import bean.ChannelBean;

/**
 * Created by zws on 2017/8/3.
 */

public class QueryChannelAdapter extends BaseQuickAdapter<ChannelBean.ResultBean, BaseViewHolder>{
    public QueryChannelAdapter(@LayoutRes int layoutResId, @Nullable List<ChannelBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChannelBean.ResultBean item) {
        helper.setText(R.id.china_tv, item.getName());
    }
}
