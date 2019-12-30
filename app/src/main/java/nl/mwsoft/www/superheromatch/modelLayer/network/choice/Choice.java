package nl.mwsoft.www.superheromatch.modelLayer.network.choice;

import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.model.ChoiceResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Choice {
    @Headers({
            "Content-Type: application/json",
            "User-Agent: SuperheroMatch",
            "X-Platform: Android"
    })
    @POST("/api/v1/choice/choice")
    Call<ChoiceResponse> uploadChoice(@Body HashMap<String, Object> body);
}
