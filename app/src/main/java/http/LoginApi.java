package http;

import bean.LoginBean;
import bean.LoginDatabase;
import bean.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;


/**
 * Created by Daniel.Xu on 2017/1/5.
 */

public interface LoginApi {
    @GET("get_db_list")
    Observable<LoginDatabase> getDatabase();

    @POST("login")
    Call<LoginResponse> toLogin(@Body LoginBean bean);

    @POST("login")
    Call<String> toLoginstring(@Body LoginBean bean);




/*    @Headers({
            "Content-Type: application/json; charset=UTF-8",
    })*/


}
