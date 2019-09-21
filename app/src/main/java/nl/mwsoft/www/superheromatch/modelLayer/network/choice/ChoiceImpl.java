package nl.mwsoft.www.superheromatch.modelLayer.network.choice;

import java.io.IOException;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChoiceImpl {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_MATCHMAKER_PORT))
            .client(OkHttpClientManager.setUpSecureClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    Choice service = retrofit.create(Choice.class);

    public ChoiceImpl() {

    }

    public String saveChoice(String userId, String chosenUserId, int choice) {

        Call<String> call = service.saveChoice(userId, chosenUserId, choice);
        try {
            String responseMessage = call.execute().body();

            return responseMessage;
        } catch (IOException e) {
            // handle errors
        }

        return ConstantRegistry.ERROR;
    }
}
