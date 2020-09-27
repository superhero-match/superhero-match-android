package nl.mwsoft.www.superheromatch.modelLayer.network.reportUser;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReportUserImpl {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_REPORT_USER_PORT))
            .client(OkHttpClientManager.setUpSecureClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ReportUser service = retrofit.create(ReportUser.class);

    public ReportUserImpl() {
    }

    public Integer reportUser(HashMap<String, Object> body) {
        Call<Integer> call = service.reportUser(body);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e(ReportUserImpl.class.getName(), "reportUser error --> " + e.getMessage());
        }

        return ConstantRegistry.SERVER_RESPONSE_ERROR;
    }
}
