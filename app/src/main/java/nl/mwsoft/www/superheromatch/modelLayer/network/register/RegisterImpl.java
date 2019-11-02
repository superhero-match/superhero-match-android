package nl.mwsoft.www.superheromatch.modelLayer.network.register;


import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import nl.mwsoft.www.superheromatch.modelLayer.model.RegisterResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterImpl {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_REGISTER_PORT))
            .client(OkHttpClientManager.setUpSecureClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    Register service = retrofit.create(Register.class);

    public RegisterImpl() { }

    public RegisterResponse register(HashMap<String, Object> body) {

        Call<RegisterResponse> call = service.register(body);
        try {
            return call.execute().body();
        } catch (IOException e) {
            // handle errors
            Log.d("tShoot", e.getMessage());
        }

        return new RegisterResponse(
                ConstantRegistry.SERVER_RESPONSE_ERROR,
                false
        );
    }
}
