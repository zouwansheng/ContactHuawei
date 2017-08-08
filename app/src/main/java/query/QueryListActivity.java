package query;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zws.ble.contacthuawei.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bean.ChannelBean;
import bean.CountryBean;
import bean.LikeProductBean;
import bean.OriginBean;
import bean.SalemanBean;
import bean.TeamBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import edit.EditActivity;
import http.Inventroy;
import http.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import util.StringUtils;

/**
 * Created by zws on 2017/8/3.
 */

public class QueryListActivity extends Activity {
    @InjectView(R.id.title_list)
    TextView titleList;
    @InjectView(R.id.recycler_query)
    RecyclerView recyclerQuery;
    @InjectView(R.id.query_edit_all)
    SearchView queryEditAll;
    @InjectView(R.id.image_left_close)
    ImageView imageLeftClose;
    private LinearLayoutManager linearLayoutManager;
    private Inventroy inventroy;
    private QueryAdapter countryAdapter;
    private QueryChannelAdapter channelAdapter;
    private QueryOriginAdapter originAdapter;
    private QueryTeamAdapter teamAdapter;
    private QuerySalemanAdapter salemanAdapter;
    private QueryLikeAdapter likeAdapter;
    private List<CountryBean.ResultBean> countryResult;
    private List<CountryBean.ResultBean> allList;
    private List<ChannelBean.ResultBean> channelResult;
    private List<ChannelBean.ResultBean> allListChannel;
    private List<OriginBean.ResultBean> originResult;
    private List<OriginBean.ResultBean> allListorigin;
    private List<TeamBean.ResultBean> teamResult;
    private List<TeamBean.ResultBean> allListteam;
    private List<SalemanBean.ResultBean> salemanResult;
    private List<SalemanBean.ResultBean> allListSaleman;
    private List<LikeProductBean.ResultBean> likeResult;
    private List<LikeProductBean.ResultBean> allListLike;
    private ProgressDialog progressDialog;
    private String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_querylist);
        ButterKnife.inject(this);

        linearLayoutManager = new LinearLayoutManager(QueryListActivity.this);
        recyclerQuery.setLayoutManager(linearLayoutManager);
        recyclerQuery.addItemDecoration(new DividerItemDecoration(QueryListActivity.this,
                DividerItemDecoration.VERTICAL));
        inventroy = RetrofitClient.getInstance(QueryListActivity.this).create(Inventroy.class);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        initView();
    }

    private void initView() {
        progressDialog = new ProgressDialog(QueryListActivity.this);
        progressDialog.setMessage("努力加载中...");
        progressDialog.show();
        switch (type) {
            case "country":
                titleList.setText("国家");
                countryType();
                break;
            case "channel":
                titleList.setText("渠道");
                channelType();
                break;
            case "origin":
                titleList.setText("来源");
                originType();
                break;
            case "team":
                titleList.setText("销售团队");
                teamTYpe();
                break;
            case "saleman":
                titleList.setText("销售员");
                salemanTYpe();
                break;
            case "likeProduct":
                titleList.setText("感兴趣的产品");
                likeType();
        }

    }

    //感兴趣的产品
    private void likeType() {
        final Call<LikeProductBean> country = inventroy.getProduct(new HashMap());
        country.enqueue(new Callback<LikeProductBean>() {
            @Override
            public void onResponse(Call<LikeProductBean> call, Response<LikeProductBean> response) {
                progressDialog.dismiss();
                if (response.body() == null) return;
                allListLike = new ArrayList<>();
                likeResult = response.body().getResult();
                allListLike = likeResult;
                likeAdapter = new QueryLikeAdapter(R.layout.item_country, likeResult);
                recyclerQuery.setAdapter(likeAdapter);

                likeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        List<LikeProductBean.ResultBean> data = adapter.getData();
                        Intent intent = new Intent();
                        intent.putExtra("likeName", data.get(position).getName());
                        intent.putExtra("series_id", data.get(position).getSeries_id());
                        setResult(EditActivity.SELECT_LIKE_PRODUCT_RESULT, intent);
                        finish();
                    }
                });
            }

            @Override
            public void onFailure(Call<LikeProductBean> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    //销售员
    private void salemanTYpe() {
        final Call<SalemanBean> country = inventroy.getSaleMan(new HashMap());
        country.enqueue(new Callback<SalemanBean>() {
            @Override
            public void onResponse(Call<SalemanBean> call, Response<SalemanBean> response) {
                progressDialog.dismiss();
                if (response.body() == null) return;
                allListSaleman = new ArrayList<>();
                salemanResult = response.body().getResult();
                allListSaleman = salemanResult;
                salemanAdapter = new QuerySalemanAdapter(R.layout.item_country, salemanResult);
                recyclerQuery.setAdapter(salemanAdapter);

                salemanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        List<SalemanBean.ResultBean> data = adapter.getData();
                        Intent intent = new Intent();
                        intent.putExtra("salemanName", data.get(position).getName());
                        intent.putExtra("partner_id", data.get(position).getPartner_id());
                        setResult(EditActivity.SELECT_SALEMAN_RESULT, intent);
                        finish();
                    }
                });
                queryEditAll.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        salemanQ(newText);
                        return false;
                    }
                });
            }

            @Override
            public void onFailure(Call<SalemanBean> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @OnClick(R.id.image_left_close)
    void closeThis(View view){
        finish();
    }
    //销售团队
    private void teamTYpe() {
        final Call<TeamBean> country = inventroy.getSaleTeam(new HashMap());
        country.enqueue(new Callback<TeamBean>() {
            @Override
            public void onResponse(Call<TeamBean> call, Response<TeamBean> response) {
                progressDialog.dismiss();
                if (response.body() == null) return;
                allListteam = new ArrayList<>();
                teamResult = response.body().getResult();
                allListteam = teamResult;
                teamAdapter = new QueryTeamAdapter(R.layout.item_country, teamResult);
                recyclerQuery.setAdapter(teamAdapter);

                teamAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        List<TeamBean.ResultBean> data = adapter.getData();
                        Intent intent = new Intent();
                        intent.putExtra("teamName", data.get(position).getName());
                        intent.putExtra("team_id", data.get(position).getTeam_id());
                        setResult(EditActivity.SELECT_TEAM_RESULT, intent);
                        finish();
                    }
                });
                queryEditAll.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        teamQ(newText);
                        return false;
                    }
                });
            }

            @Override
            public void onFailure(Call<TeamBean> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    //来源
    private void originType() {
        final Call<OriginBean> country = inventroy.getOrigin(new HashMap());
        country.enqueue(new Callback<OriginBean>() {
            @Override
            public void onResponse(Call<OriginBean> call, Response<OriginBean> response) {
                progressDialog.dismiss();
                if (response.body() == null) return;
                allListorigin = new ArrayList<>();
                originResult = response.body().getResult();
                allListorigin = originResult;
                originAdapter = new QueryOriginAdapter(R.layout.item_country, originResult);
                recyclerQuery.setAdapter(originAdapter);

                originAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        List<OriginBean.ResultBean> data = adapter.getData();
                        Intent intent = new Intent();
                        intent.putExtra("originName", data.get(position).getName());
                        intent.putExtra("src_id", data.get(position).getSrc_id());
                        setResult(EditActivity.SELECT_ORIGIN_RESULT, intent);
                        finish();
                    }
                });
                queryEditAll.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        originQ(newText);
                        return false;
                    }
                });
            }

            @Override
            public void onFailure(Call<OriginBean> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    //渠道
    private void channelType() {
        final Call<ChannelBean> country = inventroy.getSource(new HashMap());
        country.enqueue(new Callback<ChannelBean>() {
            @Override
            public void onResponse(Call<ChannelBean> call, Response<ChannelBean> response) {
                progressDialog.dismiss();
                if (response.body() == null) return;
                allListChannel = new ArrayList<>();
                channelResult = response.body().getResult();
                allListChannel = channelResult;
                channelAdapter = new QueryChannelAdapter(R.layout.item_country, channelResult);
                recyclerQuery.setAdapter(channelAdapter);

                channelAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        List<ChannelBean.ResultBean> data = adapter.getData();
                        Intent intent = new Intent();
                        intent.putExtra("channelName", data.get(position).getName());
                        intent.putExtra("source_id", data.get(position).getSource_id());
                        setResult(EditActivity.SELECT_CHANNEL_RESULT, intent);
                        finish();
                    }
                });
                queryEditAll.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        channelQ(newText);
                        return false;
                    }
                });
            }

            @Override
            public void onFailure(Call<ChannelBean> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    //国家
    private void countryType() {
        final Call<CountryBean> country = inventroy.getCountry(new HashMap());
        country.enqueue(new Callback<CountryBean>() {
            @Override
            public void onResponse(Call<CountryBean> call, Response<CountryBean> response) {
                progressDialog.dismiss();
                if (response.body() == null || response.body().getResult() == null) return;
                allList = new ArrayList<>();
                countryResult = response.body().getResult();
                allList = countryResult;
                countryAdapter = new QueryAdapter(R.layout.item_country, countryResult);
                recyclerQuery.setAdapter(countryAdapter);

                countryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        List<CountryBean.ResultBean> data = adapter.getData();
                        Intent intent = new Intent();
                        intent.putExtra("nameProduct", data.get(position).getName());
                        intent.putExtra("country_id", data.get(position).getCountry_id());
                        setResult(EditActivity.SELECT_COUNTRY_RESULT, intent);
                        finish();
                    }
                });
                queryEditAll.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        countryQ(newText);
                        return false;
                    }
                });
            }

            @Override
            public void onFailure(Call<CountryBean> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    /**
     * 查询
     */
    private void countryQ(String newText) {
        if (StringUtils.isNullOrEmpty(newText)) {
            countryResult = allList;
        } else {
            List<CountryBean.ResultBean> filterDateList = new ArrayList<>();
            for (CountryBean.ResultBean bean : allList) {
                if (bean.getName().contains(newText)) {
                    filterDateList.add(bean);
                }
            }
            countryResult = filterDateList;
        }
        countryAdapter.setNewData(countryResult);
        countryAdapter.notifyDataSetChanged();
    }

    private void channelQ(String newText) {
        if (StringUtils.isNullOrEmpty(newText)) {
            channelResult = allListChannel;
        } else {
            List<ChannelBean.ResultBean> filterDateList = new ArrayList<>();
            for (ChannelBean.ResultBean bean : allListChannel) {
                if (bean.getName().contains(newText)) {
                    filterDateList.add(bean);
                }
            }
            channelResult = filterDateList;
        }
        channelAdapter.setNewData(channelResult);
        channelAdapter.notifyDataSetChanged();
    }

    private void originQ(String newText) {
        if (StringUtils.isNullOrEmpty(newText)) {
            originResult = allListorigin;
        } else {
            List<OriginBean.ResultBean> filterDateList = new ArrayList<>();
            for (OriginBean.ResultBean bean : allListorigin) {
                if (bean.getName().contains(newText)) {
                    filterDateList.add(bean);
                }
            }
            originResult = filterDateList;
        }
        originAdapter.setNewData(originResult);
        originAdapter.notifyDataSetChanged();
    }
    private void teamQ(String newText) {
        if (StringUtils.isNullOrEmpty(newText)) {
            teamResult = allListteam;
        } else {
            List<TeamBean.ResultBean> filterDateList = new ArrayList<>();
            for (TeamBean.ResultBean bean : allListteam) {
                if (bean.getName().contains(newText)) {
                    filterDateList.add(bean);
                }
            }
            teamResult = filterDateList;
        }
        teamAdapter.setNewData(teamResult);
        teamAdapter.notifyDataSetChanged();
    }

    private void salemanQ(String newText) {
        if (StringUtils.isNullOrEmpty(newText)) {
            salemanResult = allListSaleman;
        } else {
            List<SalemanBean.ResultBean> filterDateList = new ArrayList<>();
            for (SalemanBean.ResultBean bean : allListSaleman) {
                if (bean.getName().contains(newText)) {
                    filterDateList.add(bean);
                }
            }
            salemanResult = filterDateList;
        }
        salemanAdapter.setNewData(salemanResult);
        salemanAdapter.notifyDataSetChanged();
    }
}
