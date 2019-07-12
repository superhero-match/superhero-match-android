package nl.mwsoft.www.superheromatch.modelLayer.network.deleteAccount;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DeleteAccount {
    @Headers({
            "Content-Type: application/json",
            "User-Agent: SuperheroMatch",
            "X-Platform: Android"
    })
    @POST("/api/v1/superheroville_municipality/delete_account")
    Call<String> deleteAccount(@Query("userID") String userID);
}
