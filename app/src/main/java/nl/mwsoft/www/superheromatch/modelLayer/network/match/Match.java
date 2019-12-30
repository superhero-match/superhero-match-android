package nl.mwsoft.www.superheromatch.modelLayer.network.match;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Match {
    @Headers({
            "Content-Type: application/json",
            "User-Agent: SuperheroMatch",
            "X-Platform: Android"
    })
    @POST("/api/v1/match/match")
    Call<Integer> uploadMatch(@Body HashMap<String, Object> body);
}
