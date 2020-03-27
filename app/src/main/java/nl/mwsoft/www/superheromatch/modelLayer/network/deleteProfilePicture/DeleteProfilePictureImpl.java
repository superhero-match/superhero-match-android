package nl.mwsoft.www.superheromatch.modelLayer.network.deleteProfilePicture;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import nl.mwsoft.www.superheromatch.modelLayer.network.deleteMatch.DeleteMatch;
import nl.mwsoft.www.superheromatch.modelLayer.network.deleteMatch.DeleteMatchImpl;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeleteProfilePictureImpl {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_DELETE_MEDIA_PORT))
            .client(OkHttpClientManager.setUpSecureClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    DeleteProfilePicture service = retrofit.create(DeleteProfilePicture.class);

    public DeleteProfilePictureImpl() {
    }

    public Integer deleteProfilePicture(HashMap<String, Object> reqBody) {

        Call<Integer> call = service.deleteProfilePicture(reqBody);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e(DeleteProfilePictureImpl.class.getName(), "DeleteProfilePictureImpl --> exception: " + e.getMessage());
        }

        return ConstantRegistry.SERVER_RESPONSE_ERROR;
    }
}
