package jimmy.example.com.dsgapp.network;

import jimmy.example.com.dsgapp.model.Response;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Jinming on 1/26/18.
 */

public interface RetrofitApi {
    @GET(".")
    Call<Response> getData();
}
