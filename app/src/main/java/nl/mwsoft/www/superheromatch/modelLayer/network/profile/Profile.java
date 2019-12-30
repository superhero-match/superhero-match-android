package nl.mwsoft.www.superheromatch.modelLayer.network.profile;

import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.model.ChoiceResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.ProfileResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Profile {
    @Headers({
            "Content-Type: application/json",
            "User-Agent: SuperheroMatch",
            "X-Platform: Android"
    })
    @POST("/api/v1/superhero_suggestions/profile")
    Call<ProfileResponse> getSuggestionProfile(@Body HashMap<String, Object> body);
}
