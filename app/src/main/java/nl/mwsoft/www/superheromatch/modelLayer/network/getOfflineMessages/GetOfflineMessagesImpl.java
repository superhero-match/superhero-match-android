package nl.mwsoft.www.superheromatch.modelLayer.network.getOfflineMessages;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import nl.mwsoft.www.superheromatch.modelLayer.model.OfflineMessagesResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetOfflineMessagesImpl {

    private Retrofit retrofit;
    private GetOfflineMessages service;

    public GetOfflineMessagesImpl(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl(ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_OFFLINE_MESSAGE_PORT))
                .client(OkHttpClientManager.setUpSecureClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GetOfflineMessages.class);
    }

    public OfflineMessagesResponse getOfflineMessages(HashMap<String, Object> reqBody) {

        Call<OfflineMessagesResponse> call = service.getOfflineMessages(reqBody);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e(GetOfflineMessagesImpl.class.getName(), "GetOfflineMessagesImpl --> exception: " + e.getMessage());
        }

        return new OfflineMessagesResponse(ConstantRegistry.SERVER_RESPONSE_ERROR);
    }

}
