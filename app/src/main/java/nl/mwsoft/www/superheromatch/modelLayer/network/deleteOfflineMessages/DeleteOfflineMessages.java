package nl.mwsoft.www.superheromatch.modelLayer.network.deleteOfflineMessages;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DeleteOfflineMessages {
    @Headers({
            "Content-Type: application/json",
            "User-Agent: SuperheroMatch",
            "X-Platform: Android"
    })
    @POST("/api/v1/offline_messages/delete")
    Call<Integer> deleteOfflineMessages(@Body HashMap<String, Object> body);
}
