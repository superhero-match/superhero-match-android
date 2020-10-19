package nl.mwsoft.www.superheromatch.modelLayer.network.deleteOfflineMessages;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeleteOfflineMessagesImpl {

    private Retrofit retrofit;
    private DeleteOfflineMessages service;

    public DeleteOfflineMessagesImpl(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl(ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_DELETE_OFFLINE_MESSAGE_PORT))
                .client(OkHttpClientManager.setUpSecureClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(DeleteOfflineMessages.class);
    }

    public Integer deleteOfflineMessages(HashMap<String, Object> reqBody) {

        Call<Integer> call = service.deleteOfflineMessages(reqBody);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e(DeleteOfflineMessagesImpl.class.getName(), "DeleteOfflineMessagesImpl --> exception: " + e.getMessage());
        }

        return ConstantRegistry.SERVER_RESPONSE_ERROR;
    }
}
