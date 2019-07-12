package nl.mwsoft.www.superheromatch.modelLayer.network.updateToken;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UpdateToken {
    @Headers({
            "Content-Type: application/json",
            "User-Agent: SuperheroMatch",
            "X-Platform: Android"
    })
    @POST("/api/v1/superheroville_municipality/update_user_messaging_token")
    Call<String> updateUserMessagingToken(
            @Query("userID") String userID,
            @Query("messagingToken") String messagingToken
    );
}
