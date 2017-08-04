package query;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zws.ble.contacthuawei.R;

import java.util.List;

import bean.LikeProductBean;

/**
 * Created by zws on 2017/8/4.
 */

public class QueryLikeAdapter extends BaseQuickAdapter<LikeProductBean.ResultBean, BaseViewHolder>{
    public QueryLikeAdapter(@LayoutRes int layoutResId, @Nullable List<LikeProductBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LikeProductBean.ResultBean item) {
        helper.setText(R.id.china_tv, item.getName());
    }
}
