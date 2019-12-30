package nl.mwsoft.www.superheromatch.modelLayer.network.choice;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import nl.mwsoft.www.superheromatch.modelLayer.model.ChoiceResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChoiceImpl {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_CHOICE_PORT))
            .client(OkHttpClientManager.setUpSecureClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    Choice service = retrofit.create(Choice.class);

    public ChoiceImpl() {
    }

    public ChoiceResponse uploadChoice(HashMap<String, Object> reqBody) {

        Call<ChoiceResponse> call = service.uploadChoice(reqBody);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e(ChoiceImpl.class.getName(), "ChoiceResponse --> exception: " + e.getMessage());
        }

        return new ChoiceResponse(ConstantRegistry.SERVER_RESPONSE_ERROR, false);
    }
}
