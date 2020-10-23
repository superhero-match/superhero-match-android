package nl.mwsoft.www.superheromatch.modelLayer.network.deleteProfilePicture;

import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.model.StatusResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DeleteProfilePicture {
        @Headers({
                "Content-Type: application/json",
                "User-Agent: SuperheroMatch",
                "X-Platform: Android"
        })
        @POST("/api/v1/superhero_delete_media/delete")
        Call<StatusResponse> deleteProfilePicture(@Body HashMap<String, Object> body);
}
