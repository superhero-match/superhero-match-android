package nl.mwsoft.www.superheromatch.modelLayer.network.updateToken;

import java.io.IOException;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateTokenImpl {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_REGISTER_PORT))
            .client(OkHttpClientManager.setUpSecureClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    UpdateToken service = retrofit.create(UpdateToken.class);

    public UpdateTokenImpl() {

    }

    public String getUpdateUserTokenResponse(String userId, String messagingToken) {
        Call<String> call = service.updateUserMessagingToken(userId, messagingToken);
        try {
            String response = call.execute().body();

            return response;
        } catch (IOException e) {
            // handle errors
        }
        return ConstantRegistry.ERROR;
    }
}
