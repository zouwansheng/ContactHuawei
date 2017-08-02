package http;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * Created by zws on 2017/8/2.
 */

public interface Inventroy {

    /**
     * 国家：get_countries

     来源：get_sources

     渠道get_origins

     销售团队：get_saleteam_list

     销售员：get_saleman_list
     * */
    @GET("get_countries")
    Call<Object> getCountry(@Body HashMap hashMap);

    @GET("get_sources")
    Call<Object> getSource(@Body HashMap hashMap);

    @GET("get_saleteam_list")
    Call<Object> getSaleTeam(@Body HashMap hashMap);

    @GET("get_saleman_list")
    Call<Object> getSaleMan(@Body HashMap hashMap);

    @GET("渠道get_origins")
    Call<Object> getOrigin(@Body HashMap hashMap);
}
