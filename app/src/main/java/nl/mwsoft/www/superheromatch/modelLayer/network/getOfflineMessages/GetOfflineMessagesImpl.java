package nl.mwsoft.www.superheromatch.modelLayer.network.getOfflineMessages;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import nl.mwsoft.www.superheromatch.modelLayer.model.OfflineMessage;
import nl.mwsoft.www.superheromatch.modelLayer.model.OfflineMessagesResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetOfflineMessagesImpl {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_OFFLINE_MESSAGE_PORT))
            .client(OkHttpClientManager.setUpSecureClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    GetOfflineMessages service = retrofit.create(GetOfflineMessages.class);

    public GetOfflineMessagesImpl() {
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
