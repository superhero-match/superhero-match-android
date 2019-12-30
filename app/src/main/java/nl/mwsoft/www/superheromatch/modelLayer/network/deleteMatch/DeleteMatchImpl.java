package nl.mwsoft.www.superheromatch.modelLayer.network.deleteMatch;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeleteMatchImpl {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_DELETE_MATCH_PORT))
            .client(OkHttpClientManager.setUpSecureClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    DeleteMatch service = retrofit.create(DeleteMatch.class);

    public DeleteMatchImpl() {
    }

    public Integer deleteMatch(HashMap<String, Object> reqBody) {

        Call<Integer> call = service.deleteMatch(reqBody);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e(DeleteMatchImpl.class.getName(), "DeleteMatchImpl --> exception: " + e.getMessage());
        }

        return ConstantRegistry.SERVER_RESPONSE_ERROR;
    }
}
