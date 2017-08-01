package insert;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.zws.ble.contacthuawei.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import contactui.ClearEditText;

/**
 * Created by zws on 2017/8/1.
 */

public class AlreadyInsertActivity extends AppCompatActivity {
    @InjectView(R.id.close_text)
    TextView closeText;
    @InjectView(R.id.finish_text)
    TextView finishText;
    @InjectView(R.id.edit_search)
    ClearEditText editSearch;
    @InjectView(R.id.recycler_already)
    RecyclerView recyclerAlready;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alreadyinsert);
        ButterKnife.inject(this);
    }
}
