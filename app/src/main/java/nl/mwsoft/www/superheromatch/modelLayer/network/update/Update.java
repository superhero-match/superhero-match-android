package nl.mwsoft.www.superheromatch.modelLayer.network.update;

import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.model.UpdateResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Update {
    @Headers({
            "Content-Type: application/json",
            "User-Agent: SuperheroMatch",
            "X-Platform: Android"
    })
    @POST("/api/v1/superhero_update/update_superhero")
    Call<UpdateResponse> update(@Body HashMap<String, Object> body);
}
