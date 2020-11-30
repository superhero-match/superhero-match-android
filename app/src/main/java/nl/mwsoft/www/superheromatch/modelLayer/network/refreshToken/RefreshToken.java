package nl.mwsoft.www.superheromatch.modelLayer.network.refreshToken;

import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.model.TokenResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RefreshToken {
    @Headers({
            "Content-Type: application/json",
            "User-Agent: SuperheroMatch",
            "X-Platform: Android"
    })
    @POST("/api/v1/superhero_auth/token/refresh")
    Call<TokenResponse> refreshToken(@Body HashMap<String, Object> body);
}
