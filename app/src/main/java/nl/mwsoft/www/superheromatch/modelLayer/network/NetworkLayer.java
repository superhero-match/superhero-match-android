package nl.mwsoft.www.superheromatch.modelLayer.network;


import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import nl.mwsoft.www.superheromatch.modelLayer.model.CheckEmailResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.RegisterResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.SuggestionsResponse;
import nl.mwsoft.www.superheromatch.modelLayer.network.checkEmail.CheckEmailImpl;
import nl.mwsoft.www.superheromatch.modelLayer.network.deleteAccount.DeleteAccountImpl;
import nl.mwsoft.www.superheromatch.modelLayer.network.inviteUser.InviteSuperheroImpl;
import nl.mwsoft.www.superheromatch.modelLayer.network.register.RegisterImpl;
import nl.mwsoft.www.superheromatch.modelLayer.network.suggestions.SuggestionsImpl;
import nl.mwsoft.www.superheromatch.modelLayer.network.update.UpdateImpl;

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

    // region Save User's Choice

    public Observable<String> saveChoice(String userId, String chosenUserId, String choiceType) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
//                    ChoiceImpl choice = new ChoiceImpl();
//                    String response = choice.saveChoice(userId, chosenUserId, choiceType);
//                    emitter.onNext(response);
//                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    // endregion

    // region Delete User's Account

    public Observable<String> deleteAccount(String userId){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    DeleteAccountImpl deleteAccount = new DeleteAccountImpl();
                    String response = deleteAccount.deleteAccount(userId);
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

    public Observable<RegisterResponse> register(HashMap<String, Object> body){
        return Observable.create(new ObservableOnSubscribe<RegisterResponse>() {
            @Override
            public void subscribe(ObservableEmitter<RegisterResponse> emitter) throws Exception {
                try {
                    RegisterImpl register = new RegisterImpl();
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

    public Observable<SuggestionsResponse> getSuggestions(HashMap<String, Object> body){
        return Observable.create(new ObservableOnSubscribe<SuggestionsResponse>() {
            @Override
            public void subscribe(ObservableEmitter<SuggestionsResponse> emitter) throws Exception {
                try {
                    SuggestionsImpl suggestions = new SuggestionsImpl();
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

    // region Update User's Firebase Messaging Token

    public Observable<String> getUpdateUserTokenResponse(String userId, String messagingToken) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
//                    UpdateTokenImpl updateToken = new UpdateTokenImpl();
//                    String response = updateToken.getUpdateUserTokenResponse(userId, messagingToken);
//                    emitter.onNext(response);
//                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    // endregion

    // region Update User's Data

    public Observable<String> updateUserData(String body) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    UpdateImpl updateUserData = new UpdateImpl();
                    String response = updateUserData.updateUserData(body);
                    emitter.onNext(response);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    // endregion

    // region Upgrade User's Account

    public Observable<String> upgradeAccount(String userId, String upgradedAccountType) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
//                    UpdateAccountImpl updateAccount = new UpdateAccountImpl();
//                    String response = updateAccount.updateAccount(userId, upgradedAccountType);
//                    emitter.onNext(response);
//                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    // endregion

    // region InviteSuperhero User To Join Superhero Match

    public Observable<String> inviteUser(String userName, String inviteeName, String inviteeEmail){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    InviteSuperheroImpl inviteRequest = new InviteSuperheroImpl();
                    String response = inviteRequest.getInviteUserResponse(userName, inviteeName, inviteeEmail);
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
