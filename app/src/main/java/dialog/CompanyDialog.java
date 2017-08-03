package dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zws.ble.contacthuawei.R;

import java.util.List;

import bean.ComponyQueryBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import edit.QueryComponyAdapter;
import query.QueryListActivity;

/**
 * Created by zws on 2017/8/3.
 */

public class CompanyDialog extends Dialog {
    @InjectView(R.id.recycler_dialog_compony)
    RecyclerView recyclerDialogCompony;
    private Display display;
    private Context context;
    private LayoutInflater inflater;
    private LinearLayoutManager linearLayoutManager;
    private List<ComponyQueryBean.ResultBean> result;
    private QueryComponyAdapter adapter;
    private GetCompany getCompany;

    public CompanyDialog(@NonNull Context context, List<ComponyQueryBean.ResultBean> result) {
        super(context, R.style.MyDialogStyle);
        this.context = context;
        this.result = result;
        inflater = LayoutInflater.from(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        initView();
    }

    public CompanyDialog(@NonNull Context context, @StyleRes int themeResId, List<ComponyQueryBean.ResultBean> result) {
        super(context, R.style.MyDialogStyle);
        this.context = context;
        this.result = result;
        inflater = LayoutInflater.from(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        initView();
    }

    private void initView() {
        View view = inflater.inflate(R.layout.dialog_company, null);
        setContentView(view);
        ButterKnife.inject(this, view);

        linearLayoutManager = new LinearLayoutManager(context);
        recyclerDialogCompony.setLayoutManager(linearLayoutManager);
        recyclerDialogCompony.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL));
        adapter = new QueryComponyAdapter(R.layout.item_country, result);
        recyclerDialogCompony.setAdapter(adapter);

        Window window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.gravity = Gravity.CENTER;
        wl.width = (int) (display.getWidth() * 0.8);
        wl.height = (int) (display.getWidth() * 0.5);
        window.setAttributes(wl);
        setCanceledOnTouchOutside(true);
        setCancelable(true);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                getCompany.getCompanyName(result.get(position).getName());
                dismiss();
            }
        });
    }

    public interface GetCompany{
        void getCompanyName(String name);
    }

    public void sendNameCompany(GetCompany getCompany){
        this.getCompany = getCompany;
    }
}
