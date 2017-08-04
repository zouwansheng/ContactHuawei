package http;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zws on 2017/8/4.
 */

public class RetrofitClientLogin {
    private  static Retrofit retrofit;
    public static String Url ;

    private RetrofitClientLogin(Context context) {
        retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(new OKHttpFactory(context).getOkHttpClient())

//                .baseUrl("http://192.168.2.111:8069/linkloving_app_api/")
                .baseUrl(Url+"/linkloving_app_api/")
                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static Retrofit getInstance(Context context) {
        new RetrofitClientLogin(context);
        return retrofit;
    }
}
