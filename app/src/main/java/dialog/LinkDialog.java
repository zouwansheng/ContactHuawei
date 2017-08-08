package dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zws.ble.contacthuawei.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zws on 2017/8/4.
 */

public class LinkDialog extends Dialog implements View.OnClickListener{
    @InjectView(R.id.linker)
    TextView linker;
    @InjectView(R.id.open_address)
    TextView openAddress;
    @InjectView(R.id.send_address)
    TextView sendAddress;
    @InjectView(R.id.other_address)
    TextView otherAddress;
    private Display display;
    private Context context;
    private LayoutInflater inflater;

    public LinkDialog(@NonNull Context context) {
        super(context, R.style.MyDialogStyle);
        this.context = context;

        inflater = LayoutInflater.from(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        initView();
    }

    public LinkDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, R.style.MyDialogStyle);
        this.context = context;

        inflater = LayoutInflater.from(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        initView();
    }

    private void initView() {
        View view = inflater.inflate(R.layout.dialog_addmessage, null);
        setContentView(view);
        ButterKnife.inject(this, view);

        Window window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.gravity = Gravity.CENTER;
        wl.width = (int) (display.getWidth() * 0.8);
        wl.height = (int) (display.getWidth() * 0.6);
        window.setAttributes(wl);
        setCanceledOnTouchOutside(true);
        setCancelable(true);

        linker.setOnClickListener(this);
        openAddress.setOnClickListener(this);
        sendAddress.setOnClickListener(this);
        otherAddress.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linker:
                setTypeLink.setLinkName("联系人", "contact");
                break;
            case R.id.open_address:
                setTypeLink.setLinkName("开票地址", "invoice");
                break;
            case R.id.send_address:
                setTypeLink.setLinkName("送货地址","delivery");
                break;
            case R.id.other_address:
                setTypeLink.setLinkName("其他地址","other");
                break;
        }
        dismiss();
    }

    public interface SetTypeLink{
        void setLinkName(String name, String englishName);
    }

    public void sendNameLink(SetTypeLink setTypeLink){
        this.setTypeLink = setTypeLink;
    }

    private SetTypeLink setTypeLink;
}
