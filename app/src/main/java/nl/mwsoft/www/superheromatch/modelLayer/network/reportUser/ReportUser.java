package nl.mwsoft.www.superheromatch.modelLayer.network.reportUser;

import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.model.StatusResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ReportUser {
    @Headers({
            "Content-Type: application/json",
            "User-Agent: SuperheroMatch",
            "X-Platform: Android"
    })
    @POST("/api/v1/superhero_report_user/report_user")
    Call<StatusResponse> reportUser(@Body HashMap<String, Object> body);
}
