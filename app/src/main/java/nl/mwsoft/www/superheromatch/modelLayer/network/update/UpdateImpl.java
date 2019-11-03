package nl.mwsoft.www.superheromatch.modelLayer.network.update;

import java.io.IOException;
import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import nl.mwsoft.www.superheromatch.modelLayer.model.UpdateResponse;
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

    public UpdateImpl() { }

    public UpdateResponse updateProfile(HashMap<String, Object> body) {
        Call<UpdateResponse> call = service.update(body);
        try {
            return call.execute().body();
        } catch (IOException e) {
            // handle errors
        }

        return new UpdateResponse(ConstantRegistry.SERVER_RESPONSE_ERROR, false);
    }
}
