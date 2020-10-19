package nl.mwsoft.www.superheromatch.modelLayer.network.token;

import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.model.TokenResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Token {
    @Headers({
            "Content-Type: application/json",
            "User-Agent: SuperheroMatch",
            "X-Platform: Android"
    })
    @POST("/api/v1/superhero-auth/token")
    Call<TokenResponse> getToken(@Body HashMap<String, Object> body);
}
