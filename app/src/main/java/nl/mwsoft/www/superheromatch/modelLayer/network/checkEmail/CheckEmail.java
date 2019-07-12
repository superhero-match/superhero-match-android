package nl.mwsoft.www.superheromatch.modelLayer.network.checkEmail;

import nl.mwsoft.www.superheromatch.modelLayer.model.CheckEmailResponse;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CheckEmail {
    @Headers({
            "Content-Type: application/json",
            "User-Agent: SuperheroMatch",
            "X-Platform: Android"
    })
    @POST("/api/v1/superheroville_municipality/check_email")
    Call<CheckEmailResponse> checkEmail(@Query("email") String email);
}
