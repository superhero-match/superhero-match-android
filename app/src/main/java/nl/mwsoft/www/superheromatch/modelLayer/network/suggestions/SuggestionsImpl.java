package nl.mwsoft.www.superheromatch.modelLayer.network.suggestions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import nl.mwsoft.www.superheromatch.modelLayer.model.SuggestionsResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SuggestionsImpl {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_SUGGESTIONS_PORT))
            .client(OkHttpClientManager.setUpSecureClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    Suggestions service = retrofit.create(Suggestions.class);

    public SuggestionsImpl() {

    }

    public SuggestionsResponse getSuggestions(HashMap<String, Object> body) {
        Call<SuggestionsResponse> call = service.getSuggestions(body);
        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new SuggestionsResponse(500, new ArrayList<>());
    }
}
