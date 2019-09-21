package nl.mwsoft.www.superheromatch.modelLayer.network.checkEmail;

import java.io.IOException;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import nl.mwsoft.www.superheromatch.modelLayer.model.CheckEmailResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckEmailImpl {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHEROVILLE_MUNICIPALITY_PORT))
            .client(OkHttpClientManager.setUpSecureClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    CheckEmail service = retrofit.create(CheckEmail.class);

    public CheckEmailImpl() {

    }

    public CheckEmailResponse checkEmailAlreadyExists(String email) {
        Call<CheckEmailResponse> call = service.checkEmail(email);
        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new CheckEmailResponse(500, false, false, false);
    }
}
