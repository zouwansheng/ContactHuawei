package query;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zws.ble.contacthuawei.R;

import java.util.List;

import bean.TeamBean;

/**
 * Created by zws on 2017/8/3.
 */

public class QueryTeamAdapter extends BaseQuickAdapter<TeamBean.ResultBean, BaseViewHolder>{
    public QueryTeamAdapter(@LayoutRes int layoutResId, @Nullable List<TeamBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TeamBean.ResultBean item) {
        helper.setText(R.id.china_tv, item.getName());
    }
}
