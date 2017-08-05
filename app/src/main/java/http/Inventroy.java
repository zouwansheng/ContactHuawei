package http;

import java.util.HashMap;

import bean.ChannelBean;
import bean.CommitAllMessageBean;
import bean.ComponyQueryBean;
import bean.CountryBean;
import bean.LikeProductBean;
import bean.OriginBean;
import bean.SalemanBean;
import bean.TagBean;
import bean.TeamBean;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by zws on 2017/8/2.
 */

public interface Inventroy {

    /**
     * 国家：get_countries

     渠道：get_sources

     来源 get_origins

     销售团队：get_saleteam_list

     销售员：get_saleman_list
     * */
    @POST("get_countries")
    Call<CountryBean> getCountry(@Body HashMap hashMap);

    @POST("get_sources")
    Call<ChannelBean> getSource(@Body HashMap hashMap);

    @POST("get_saleteam_list")
    Call<TeamBean> getSaleTeam(@Body HashMap hashMap);

    @POST("get_saleman_list")
    Call<SalemanBean> getSaleMan(@Body HashMap hashMap);

    @POST("get_origins")
    Call<OriginBean> getOrigin(@Body HashMap hashMap);

    @POST("get_company_by_name")
    Call<ComponyQueryBean> getCompbyName(@Body HashMap hashMap);

    @POST("add_partners")
    Call<CommitAllMessageBean> addPaterner(@Body HashMap hashMap);

    @POST("get_partner_tag_list")
    Call<TagBean> getTaglist(@Body HashMap hashMap);

    @POST("get_product_series")
    Call<LikeProductBean> getProduct(@Body HashMap hashMap);
}
