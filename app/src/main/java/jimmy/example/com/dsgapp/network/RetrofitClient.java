package jimmy.example.com.dsgapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jinming on 1/26/18.
 */

public class RetrofitClient {
    public static String BASE_URL = "https://movesync-qa.dcsg.com/dsglabs/mobile/api/venue/";
    private static Retrofit instance = null;

    public static Retrofit getInstance() {
        if (instance == null) {
            instance = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }
}
