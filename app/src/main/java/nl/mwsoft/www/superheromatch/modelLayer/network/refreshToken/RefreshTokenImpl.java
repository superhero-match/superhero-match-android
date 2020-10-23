package nl.mwsoft.www.superheromatch.modelLayer.network.refreshToken;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import nl.mwsoft.www.superheromatch.modelLayer.model.TokenResponse;
import nl.mwsoft.www.superheromatch.modelLayer.network.token.TokenImpl;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RefreshTokenImpl {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_AUTH_PORT))
            .client(OkHttpClientManager.setUpSecureClientWithoutAuthorization())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RefreshToken service = retrofit.create(RefreshToken.class);

    public RefreshTokenImpl() {

    }

    public TokenResponse refreshToken(HashMap<String, Object> body) {
        Call<TokenResponse> call = service.refreshToken(body);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.d(TokenImpl.class.getName(), "IOException: " + e.getMessage());
        }

        return new TokenResponse(500, "", "");
    }
}
