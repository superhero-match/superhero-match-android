package nl.mwsoft.www.superheromatch.modelLayer.network.updateAccount;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UpdateAccount {
    @Headers({
            "Content-Type: application/json",
            "User-Agent: SuperheroMatch",
            "X-Platform: Android"
    })
    @POST("/api/v1/superheroville_municipality/update_account")
    Call<String> updateAccount(
            @Query("userID") String userID,
            @Query("accountType") String accountType
    );
}
