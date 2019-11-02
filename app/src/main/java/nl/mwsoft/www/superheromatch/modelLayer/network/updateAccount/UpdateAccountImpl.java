package nl.mwsoft.www.superheromatch.modelLayer.network.updateAccount;

import java.io.IOException;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateAccountImpl {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_REGISTER_PORT))
            .client(OkHttpClientManager.setUpSecureClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    UpdateAccount service = retrofit.create(UpdateAccount.class);

    public UpdateAccountImpl() {

    }

    public String updateAccount(String userId, String accountType) {

        Call<String> call = service.updateAccount(userId, accountType);
        try {
            String responseMessage = call.execute().body();

            return responseMessage;
        } catch (IOException e) {
            // handle errors
        }

        return ConstantRegistry.ERROR;
    }
}
