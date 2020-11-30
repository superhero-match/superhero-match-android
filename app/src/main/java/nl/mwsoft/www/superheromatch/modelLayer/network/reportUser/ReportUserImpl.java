package nl.mwsoft.www.superheromatch.modelLayer.network.reportUser;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import nl.mwsoft.www.superheromatch.modelLayer.model.StatusResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReportUserImpl {

    private Retrofit retrofit;
    private ReportUser service;

    public ReportUserImpl(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl(ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_REPORT_USER_PORT))
                .client(OkHttpClientManager.setUpSecureClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ReportUser.class);
    }

    public StatusResponse reportUser(HashMap<String, Object> body) {
        Call<StatusResponse> call = service.reportUser(body);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e(ReportUserImpl.class.getName(), "reportUser error --> " + e.getMessage());
        }

        return new StatusResponse(ConstantRegistry.SERVER_RESPONSE_ERROR);
    }
}
