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
package nl.mwsoft.www.superheromatch.modelLayer.network.checkEmail;

import java.io.IOException;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.helper.okHttpClientManager.OkHttpClientManager;
import nl.mwsoft.www.superheromatch.modelLayer.model.CheckEmailResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckEmailImpl {

    private Retrofit retrofit;
    private CheckEmail service;

    public CheckEmailImpl() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ConstantRegistry.BASE_SERVER_URL.concat(ConstantRegistry.SUPERHERO_SCREEN_PORT))
                .client(OkHttpClientManager.setUpSecureClientWithoutAuthorization())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(CheckEmail.class);
    }

    public CheckEmailResponse checkEmailAlreadyExists(String email) {
        Call<CheckEmailResponse> call = service.checkEmail(email);
        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new CheckEmailResponse(
                ConstantRegistry.SERVER_RESPONSE_ERROR,
                false,
                false,
                false,
                new User()
        );
    }
}
