/*
  Copyright (C) 2019 - 2021 MWSOFT
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package nl.mwsoft.www.superheromatch.modelLayer.network.suggestions;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import nl.mwsoft.www.superheromatch.modelLayer.model.SuggestionsResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SuggestionsImpl {

    private Retrofit retrofit;
    private Suggestions service;

    public SuggestionsImpl(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl(ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_SUGGESTIONS_PORT))
                .client(OkHttpClientManager.setUpSecureClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(Suggestions.class);
    }

    public SuggestionsResponse getSuggestions(HashMap<String, Object> body) {
        Call<SuggestionsResponse> call = service.getSuggestions(body);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.d("tShoot", "IOException: " + e.getMessage());
        }

        return new SuggestionsResponse(500, new ArrayList<>(), new ArrayList<>());
    }
}
