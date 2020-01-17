package nl.mwsoft.www.superheromatch.modelLayer.network.getMatch;

import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.model.GetMatchResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GetMatch {
    @Headers({
            "Content-Type: application/json",
            "User-Agent: SuperheroMatch",
            "X-Platform: Android"
    })
    @POST("/api/v1/match/get_match")
    Call<GetMatchResponse> getMatch(@Body HashMap<String, Object> body);
}
