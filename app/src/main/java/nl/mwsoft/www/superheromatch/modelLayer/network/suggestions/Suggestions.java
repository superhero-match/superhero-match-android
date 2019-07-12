package nl.mwsoft.www.superheromatch.modelLayer.network.suggestions;

import java.util.ArrayList;

import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Suggestions {
    @Headers({
            "Content-Type: application/json",
            "User-Agent: SuperheroMatch",
            "X-Platform: Android"
    })
    @POST("/suggestions")
    Call<ArrayList<User>> getSuggestions(@Body String body);

    // lookingForGender
    // gender
    // lookingForAgeMin
    // lookingForAgeMax
    // maxDistance
    // lat
    // lon

    // JSONObject paramObject = new JSONObject();
    // paramObject.put("email", "sample@gmail.com");
    // paramObject.put("pass", "4384984938943");
    // paramObject.toString()
}
