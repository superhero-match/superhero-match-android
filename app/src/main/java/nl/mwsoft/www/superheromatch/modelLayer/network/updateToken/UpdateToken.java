package nl.mwsoft.www.superheromatch.modelLayer.network.updateToken;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UpdateToken {
    @Headers({
            "Content-Type: application/json",
            "User-Agent: SuperheroMatch",
            "X-Platform: Android"
    })
    @POST("/api/v1/firebase_token/update_messaging_token")
    Call<Integer> updateFirebaseToken(@Body HashMap<String, Object> body);
}
