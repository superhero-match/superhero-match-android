package nl.mwsoft.www.superheromatch.modelLayer.network.register;


import org.json.JSONObject;

import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.model.RegisterResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Register {
    @Headers({
            "Content-Type: application/json",
            "User-Agent: SuperheroMatch",
            "X-Platform: Android"
    })
    @POST("/api/v1/superhero_register/register")
    Call<RegisterResponse> register(@Body HashMap<String, Object> body);
}
