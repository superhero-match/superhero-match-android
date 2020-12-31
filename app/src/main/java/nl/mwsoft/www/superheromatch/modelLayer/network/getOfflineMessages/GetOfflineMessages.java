package nl.mwsoft.www.superheromatch.modelLayer.network.getOfflineMessages;


import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.model.OfflineMessagesResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GetOfflineMessages {
    @Headers({
            "Content-Type: application/json",
            "User-Agent: SuperheroMatch",
            "X-Platform: Android"
    })
    @POST("/api/v1/chat/get_offline_messages")
    Call<OfflineMessagesResponse> getOfflineMessages(@Body HashMap<String, Object> body);
}
