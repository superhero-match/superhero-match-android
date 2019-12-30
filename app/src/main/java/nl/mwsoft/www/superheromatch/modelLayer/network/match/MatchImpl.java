package nl.mwsoft.www.superheromatch.modelLayer.network.match;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import nl.mwsoft.www.superheromatch.modelLayer.model.ChoiceResponse;
import nl.mwsoft.www.superheromatch.modelLayer.network.choice.Choice;
import nl.mwsoft.www.superheromatch.modelLayer.network.choice.ChoiceImpl;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MatchImpl {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_MATCH_PORT))
            .client(OkHttpClientManager.setUpSecureClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    Match service = retrofit.create(Match.class);

    public MatchImpl() {
    }

    public Integer uploadMatch(HashMap<String, Object> reqBody) {

        Call<Integer> call = service.uploadMatch(reqBody);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e(MatchImpl.class.getName(), "MatchImpl --> exception: " + e.getMessage());
        }

        return ConstantRegistry.SERVER_RESPONSE_ERROR;
    }

}
