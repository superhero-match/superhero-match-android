package nl.mwsoft.www.superheromatch.modelLayer.network.getMatch;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import nl.mwsoft.www.superheromatch.modelLayer.model.GetMatchResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.Superhero;
import nl.mwsoft.www.superheromatch.modelLayer.network.match.MatchImpl;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetMatchImpl {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_MATCH_PORT))
            .client(OkHttpClientManager.setUpSecureClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    GetMatch service = retrofit.create(GetMatch.class);

    public GetMatchImpl() {
    }

    public GetMatchResponse getMatch(HashMap<String, Object> reqBody) {

        Call<GetMatchResponse> call = service.getMatch(reqBody);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e(MatchImpl.class.getName(), "GetMatchImpl --> exception: " + e.getMessage());
        }

        return new GetMatchResponse(ConstantRegistry.SERVER_RESPONSE_ERROR, new Superhero());
    }
}
