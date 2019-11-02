package nl.mwsoft.www.superheromatch.modelLayer.network.update;

import java.io.IOException;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateImpl {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_UPDATE_PORT))
            .client(OkHttpClientManager.setUpSecureClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    Update service = retrofit.create(Update.class);

    public UpdateImpl() {

    }

    public String updateUserData(String body) {

        Call<String> call = service.update(body);
        try {
            String responseMessage = call.execute().body();

            return responseMessage;
        } catch (IOException e) {
            // handle errors
        }

        return ConstantRegistry.ERROR;
    }
}
