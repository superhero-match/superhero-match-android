package nl.mwsoft.www.superheromatch.modelLayer.network.updateToken;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateTokenImpl {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_FIREBASE_MESSAGING_TOKEN_PORT))
            .client(OkHttpClientManager.setUpSecureClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    UpdateToken service = retrofit.create(UpdateToken.class);

    public UpdateTokenImpl() {
    }

    public Integer updateFirebaseToken(HashMap<String, Object> body) {
        Call<Integer> call = service.updateFirebaseToken(body);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e(UpdateTokenImpl.class.getName(), "updateFirebaseToken error --> " + e.getMessage());
        }

        return ConstantRegistry.SERVER_RESPONSE_ERROR;
    }
}
