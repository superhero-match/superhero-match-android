package nl.mwsoft.www.superheromatch.modelLayer.network.inviteUser;

import java.io.IOException;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InviteSuperheroImpl {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHEROVILLE_MUNICIPALITY_PORT))
            .client(OkHttpClientManager.setUpSecureClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    InviteSuperhero service = retrofit.create(InviteSuperhero.class);

    public InviteSuperheroImpl() {

    }

    public String getInviteUserResponse(String userName, String inviteeName, String inviteeEmail) {
        Call<String> call = service.inviteSuperhero(userName,inviteeName,inviteeEmail);
        try {
            String response = call.execute().body();

            return response;
        } catch (IOException e) {
            // handle errors
        }
        return ConstantRegistry.ERROR;
    }
}
