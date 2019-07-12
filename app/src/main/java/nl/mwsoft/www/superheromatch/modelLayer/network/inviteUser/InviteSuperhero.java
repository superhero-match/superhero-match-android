package nl.mwsoft.www.superheromatch.modelLayer.network.inviteUser;


import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InviteSuperhero {
    @Headers({
            "Content-Type: application/json",
            "User-Agent: SuperheroMatch",
            "X-Platform: Android"
    })
    @POST("/api/v1/superheroville_municipality/invite_superhero")
    Call<String> inviteSuperhero(
            @Query("userName") String userName,
            @Query("inviteeName") String inviteeName,
            @Query("inviteeEmail") String inviteeEmail
    );
}
