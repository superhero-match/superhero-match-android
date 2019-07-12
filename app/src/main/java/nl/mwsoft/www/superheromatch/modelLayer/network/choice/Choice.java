package nl.mwsoft.www.superheromatch.modelLayer.network.choice;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Choice {
    @Headers({
            "Content-Type: application/json",
            "User-Agent: SuperheroMatch",
            "X-Platform: Android"
    })
    @POST("/choice")
    Call<String> saveChoice(
            @Query("userID") String userID,
            @Query("chosenUserID") String chosenUserID,
            @Query("choice") String choice
    );
}
