package nl.mwsoft.www.superheromatch.modelLayer.network.suggestions;

import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.model.SuggestionsResponse;
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
    @POST("/api/v1/superhero_suggestions/suggest")
    Call<SuggestionsResponse> getSuggestions(@Body HashMap<String, Object> body);
}
