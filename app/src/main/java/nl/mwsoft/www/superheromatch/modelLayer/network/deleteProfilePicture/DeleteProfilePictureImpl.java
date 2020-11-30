package nl.mwsoft.www.superheromatch.modelLayer.network.deleteProfilePicture;

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

public class DeleteProfilePictureImpl {

    private Retrofit retrofit;
    private DeleteProfilePicture service;

    public DeleteProfilePictureImpl(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl(ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_DELETE_MEDIA_PORT))
                .client(OkHttpClientManager.setUpSecureClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(DeleteProfilePicture.class);
    }

    public StatusResponse deleteProfilePicture(HashMap<String, Object> reqBody) {

        Call<StatusResponse> call = service.deleteProfilePicture(reqBody);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e(DeleteProfilePictureImpl.class.getName(), "DeleteProfilePictureImpl --> exception: " + e.getMessage());
        }

        return new StatusResponse(ConstantRegistry.SERVER_RESPONSE_ERROR);
    }
}
