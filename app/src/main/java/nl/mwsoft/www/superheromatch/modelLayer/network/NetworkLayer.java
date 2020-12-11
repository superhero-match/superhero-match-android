/*
  Copyright (C) 2019 - 2020 MWSOFT
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
package nl.mwsoft.www.superheromatch.modelLayer.network;


import android.content.Context;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import nl.mwsoft.www.superheromatch.modelLayer.model.CheckEmailResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.ChoiceResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.DeleteAccountResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.GetMatchResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.OfflineMessagesResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.ProfileResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.RegisterResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.StatusResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.SuggestionsResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.TokenResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.UpdateResponse;
import nl.mwsoft.www.superheromatch.modelLayer.network.checkEmail.CheckEmailImpl;
import nl.mwsoft.www.superheromatch.modelLayer.network.choice.ChoiceImpl;
import nl.mwsoft.www.superheromatch.modelLayer.network.deleteAccount.DeleteAccountImpl;
import nl.mwsoft.www.superheromatch.modelLayer.network.deleteMatch.DeleteMatchImpl;
import nl.mwsoft.www.superheromatch.modelLayer.network.deleteOfflineMessages.DeleteOfflineMessagesImpl;
import nl.mwsoft.www.superheromatch.modelLayer.network.deleteProfilePicture.DeleteProfilePictureImpl;
import nl.mwsoft.www.superheromatch.modelLayer.network.getMatch.GetMatchImpl;
import nl.mwsoft.www.superheromatch.modelLayer.network.getOfflineMessages.GetOfflineMessagesImpl;
import nl.mwsoft.www.superheromatch.modelLayer.network.match.MatchImpl;
import nl.mwsoft.www.superheromatch.modelLayer.network.profile.ProfileImpl;
import nl.mwsoft.www.superheromatch.modelLayer.network.refreshToken.RefreshTokenImpl;
import nl.mwsoft.www.superheromatch.modelLayer.network.register.RegisterImpl;
import nl.mwsoft.www.superheromatch.modelLayer.network.reportUser.ReportUserImpl;
import nl.mwsoft.www.superheromatch.modelLayer.network.suggestions.SuggestionsImpl;
import nl.mwsoft.www.superheromatch.modelLayer.network.token.TokenImpl;
import nl.mwsoft.www.superheromatch.modelLayer.network.update.UpdateImpl;
import nl.mwsoft.www.superheromatch.modelLayer.network.updateToken.UpdateTokenImpl;

public class NetworkLayer {

    public NetworkLayer() {
    }

    // region Check If Email Already Exists

    public Observable<CheckEmailResponse> checkEmailAlreadyExists(String email) {
        return Observable.create(new ObservableOnSubscribe<CheckEmailResponse>() {
            @Override
            public void subscribe(ObservableEmitter<CheckEmailResponse> emitter) throws Exception {
                try {
                    CheckEmailImpl checkEmailAlreadyExists = new CheckEmailImpl();
                    CheckEmailResponse response = checkEmailAlreadyExists.checkEmailAlreadyExists(email);
                    emitter.onNext(response);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    // endregion

    // region Delete User's Account

    public Observable<DeleteAccountResponse> deleteAccount(HashMap<String, Object> body, Context context){
        return Observable.create(new ObservableOnSubscribe<DeleteAccountResponse>() {
            @Override
            public void subscribe(ObservableEmitter<DeleteAccountResponse> emitter) throws Exception {
                try {
                    DeleteAccountImpl deleteAccount = new DeleteAccountImpl(context);
                    DeleteAccountResponse response = deleteAccount.deleteAccount(body);
                    emitter.onNext(response);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    // endregion

    // region Register

    public Observable<RegisterResponse> register(HashMap<String, Object> body, Context context){
        return Observable.create(new ObservableOnSubscribe<RegisterResponse>() {
            @Override
            public void subscribe(ObservableEmitter<RegisterResponse> emitter) throws Exception {
                try {
                    RegisterImpl register = new RegisterImpl(context);
                    RegisterResponse response = register.register(body);
                    emitter.onNext(response);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    // endregion

    // region Retrieve Suggestions

    public Observable<SuggestionsResponse> getSuggestions(HashMap<String, Object> body, Context context){
        return Observable.create(new ObservableOnSubscribe<SuggestionsResponse>() {
            @Override
            public void subscribe(ObservableEmitter<SuggestionsResponse> emitter) throws Exception {
                try {
                    SuggestionsImpl suggestions = new SuggestionsImpl(context);
                    SuggestionsResponse response = suggestions.getSuggestions(body);
                    emitter.onNext(response);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    // endregion

    // region Upload Choice

    public Observable<ChoiceResponse> uploadChoice(HashMap<String, Object> body, Context context){
        return Observable.create(new ObservableOnSubscribe<ChoiceResponse>() {
            @Override
            public void subscribe(ObservableEmitter<ChoiceResponse> emitter) throws Exception {
                try {
                    ChoiceImpl choice = new ChoiceImpl(context);
                    ChoiceResponse response = choice.uploadChoice(body);
                    emitter.onNext(response);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    // endregion

    // region Upload Match

    public Observable<StatusResponse> uploadMatch(HashMap<String, Object> body, Context context){
        return Observable.create(new ObservableOnSubscribe<StatusResponse>() {
            @Override
            public void subscribe(ObservableEmitter<StatusResponse> emitter) throws Exception {
                try {
                    MatchImpl match = new MatchImpl(context);
                    StatusResponse response = match.uploadMatch(body);
                    emitter.onNext(response);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    // endregion

    // region Update Firebase Messaging Token

    public Observable<StatusResponse> updateFirebaseToken(HashMap<String, Object> body, Context context){
        return Observable.create(new ObservableOnSubscribe<StatusResponse>() {
            @Override
            public void subscribe(ObservableEmitter<StatusResponse> emitter) throws Exception {
                try {
                    UpdateTokenImpl updateToken = new UpdateTokenImpl(context);
                    StatusResponse response = updateToken.updateFirebaseToken(body);
                    emitter.onNext(response);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    // endregion

    // region Delete Match

    public Observable<StatusResponse> deleteMatch(HashMap<String, Object> body, Context context){
        return Observable.create(new ObservableOnSubscribe<StatusResponse>() {
            @Override
            public void subscribe(ObservableEmitter<StatusResponse> emitter) throws Exception {
                try {
                    DeleteMatchImpl deleteMatch = new DeleteMatchImpl(context);
                    StatusResponse response = deleteMatch.deleteMatch(body);
                    emitter.onNext(response);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    // endregion

    // region Get Suggestion Profile

    public Observable<ProfileResponse> getSuperheroProfile(HashMap<String, Object> body, Context context){
        return Observable.create(new ObservableOnSubscribe<ProfileResponse>() {
            @Override
            public void subscribe(ObservableEmitter<ProfileResponse> emitter) throws Exception {
                try {
                    ProfileImpl profile = new ProfileImpl(context);
                    ProfileResponse response = profile.getSuggestionProfile(body);
                    emitter.onNext(response);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    // endregion

    // region Get Match

    public Observable<GetMatchResponse> getMatch(HashMap<String, Object> body, Context context){
        return Observable.create(new ObservableOnSubscribe<GetMatchResponse>() {
            @Override
            public void subscribe(ObservableEmitter<GetMatchResponse> emitter) throws Exception {
                try {
                    GetMatchImpl getMatch = new GetMatchImpl(context);
                    GetMatchResponse response = getMatch.getMatch(body);
                    emitter.onNext(response);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    // endregion

    // region Update User's Data

    public Observable<UpdateResponse> updateProfile(HashMap<String, Object> body, Context context) {
        return Observable.create(new ObservableOnSubscribe<UpdateResponse>() {
            @Override
            public void subscribe(ObservableEmitter<UpdateResponse> emitter) throws Exception {
                try {
                    UpdateImpl updateUserData = new UpdateImpl(context);
                    UpdateResponse response = updateUserData.updateProfile(body);
                    emitter.onNext(response);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    // endregion

    // region Get Offline Messages

    public Observable<OfflineMessagesResponse> getOfflineMessages(HashMap<String, Object> body, Context context){
        return Observable.create(new ObservableOnSubscribe<OfflineMessagesResponse>() {
            @Override
            public void subscribe(ObservableEmitter<OfflineMessagesResponse> emitter) throws Exception {
                try {
                    GetOfflineMessagesImpl getOfflineMessages = new GetOfflineMessagesImpl(context);
                    OfflineMessagesResponse response = getOfflineMessages.getOfflineMessages(body);
                    emitter.onNext(response);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    // endregion

    // region Delete Profile Picture

    public Observable<StatusResponse> deleteProfilePicture(HashMap<String, Object> body, Context context){
        return Observable.create(new ObservableOnSubscribe<StatusResponse>() {
            @Override
            public void subscribe(ObservableEmitter<StatusResponse> emitter) throws Exception {
                try {
                    DeleteProfilePictureImpl deleteProfilePictureImpl = new DeleteProfilePictureImpl(context);
                    StatusResponse response = deleteProfilePictureImpl.deleteProfilePicture(body);
                    emitter.onNext(response);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    // endregion

    // region Delete Profile Picture

    public Observable<Integer> deleteOfflineMessages(HashMap<String, Object> body, Context context){
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                try {
                    DeleteOfflineMessagesImpl deleteOfflineMessagesImpl = new DeleteOfflineMessagesImpl(context);
                    Integer response = deleteOfflineMessagesImpl.deleteOfflineMessages(body);
                    emitter.onNext(response);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    // endregion

    // region Report User

    public Observable<StatusResponse> reportUser(HashMap<String, Object> body, Context context){
        return Observable.create(new ObservableOnSubscribe<StatusResponse>() {
            @Override
            public void subscribe(ObservableEmitter<StatusResponse> emitter) throws Exception {
                try {
                    ReportUserImpl reportUserImpl = new ReportUserImpl(context);
                    StatusResponse response = reportUserImpl.reportUser(body);
                    emitter.onNext(response);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    // endregion

    // region Token

    public Observable<TokenResponse> getToken(HashMap<String, Object> body) {
        return Observable.create(new ObservableOnSubscribe<TokenResponse>() {
            @Override
            public void subscribe(ObservableEmitter<TokenResponse> emitter) throws Exception {
                try {
                    TokenImpl tokenImpl = new TokenImpl();
                    TokenResponse response = tokenImpl.getToken(body);
                    emitter.onNext(response);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    // endregion

    // region RefreshToken

    public Observable<TokenResponse> refreshToken(HashMap<String, Object> body) {
        return Observable.create(new ObservableOnSubscribe<TokenResponse>() {
            @Override
            public void subscribe(ObservableEmitter<TokenResponse> emitter) throws Exception {
                try {
                    RefreshTokenImpl refreshTokenImpl = new RefreshTokenImpl();
                    TokenResponse response = refreshTokenImpl.refreshToken(body);
                    emitter.onNext(response);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    // endregion
}
