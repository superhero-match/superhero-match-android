package nl.mwsoft.www.superheromatch.modelLayer.network.profile;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import nl.mwsoft.www.superheromatch.modelLayer.model.ProfileResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.Superhero;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileImpl {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_SUGGESTIONS_PORT))
            .client(OkHttpClientManager.setUpSecureClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    Profile service = retrofit.create(Profile.class);

    public ProfileImpl() {
    }

    public ProfileResponse getSuggestionProfile(HashMap<String, Object> reqBody) {

        Call<ProfileResponse> call = service.getSuggestionProfile(reqBody);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e(ProfileResponse.class.getName(), "ProfileResponse --> exception: " + e.getMessage());
        }

        return new ProfileResponse(ConstantRegistry.SERVER_RESPONSE_ERROR, new Superhero());
    }

}
