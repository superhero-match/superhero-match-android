package nl.mwsoft.www.superheromatch.modelLayer.network.update;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Update {
    @Headers({
            "Content-Type: application/json",
            "User-Agent: SuperheroMatch",
            "X-Platform: Android"
    })
    @POST("/api/v1/superhero_update/update_superhero")
    Call<String> update(@Body String body);


//    JSONObject paramObject = new JSONObject();
//    paramObject.put("email", "sample@gmail.com");
//    paramObject.put("pass", "4384984938943");
//    paramObject.toString()
}
