package io.openforge.gameservices;

import android.content.Intent;
import android.util.Log;
import android.webkit.WebView;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.tasks.Task;

/**
 * GameServices plugin
 */
@NativePlugin(requestCodes = { GameServices.RC_SIGN_IN, GameServices.RC_LEADERBOARD_UI,
        GameServices.RC_ACHIEVEMENT_UI })
public class GameServices extends Plugin {
    static final int RC_SIGN_IN = 1;
    static final int RC_LEADERBOARD_UI = 9004;
    static final int RC_ACHIEVEMENT_UI = 9003;

    private final String TAG = "GameServices";
    private GoogleSignInOptions mGoogleSignInOptions;

    // MARK: Plugin Overrides
    @Override
    public void load() {
        super.load();
        Log.d(TAG, "lifecycle load called");

        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
    }

    @Override
    protected void handleOnActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.handleOnActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "handleOnActivityResult called");

        final PluginCall savedCall = getSavedCall();

        if (requestCode == RC_SIGN_IN) {
            Log.d(TAG, "starting handler for rc sign in");
            final GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                final GoogleSignInAccount signInAccount = result.getSignInAccount();
                this.registerPopupView(signInAccount);
                Games.getPlayersClient(getContext(), signInAccount).getCurrentPlayer().addOnCompleteListener(playerClientTask -> {
                    JSObject response = new JSObject();
                    JSObject responseData = new JSObject();
                    responseData.put("player_name", playerClientTask.getResult().getDisplayName());
                    responseData.put("player_id", playerClientTask.getResult().getPlayerId());
                    response.put("response", responseData);
                    savedCall.resolve(response);
                });
            } else {
                String message = result.getStatus().getStatusMessage();
                Integer code = result.getStatus().getStatusCode();
                Log.e(TAG, "signInWithIntent:failure " + message + " " + code.toString());
                if (savedCall != null) {
                    JSObject response = new JSObject();
                    JSObject responseData = new JSObject();
                    responseData.put("error", "Error while trying to sign in");
                    response.put("response", responseData);
                    savedCall.resolve(response);
                }
            }
        }
    }

    // MARK: Plugin Methods
    @PluginMethod()
    public void signIn(final PluginCall call) {
        Log.d(TAG, "signIn called");
        saveCall(call);
        startSilentSignIn();
    }

    @PluginMethod()
    public void signOut(final PluginCall call) {
        final GoogleSignInClient signInClient = GoogleSignIn.getClient(getContext(), mGoogleSignInOptions);
        signInClient.signOut().addOnCompleteListener(getActivity(), task -> {
            if (task.isSuccessful()) {
                call.resolve(new JSObject().put("response", "signOut:success"));
            } else {
                call.reject("signOut:error");
            }
        });
    }

    /**
     * TODO: if no leaderboardId (show ios default leaderboard if set, show android
     * all leaderboards)
     *
     * @param call Capacitor plugin call
     */
    @PluginMethod()
    public void showLeaderboard(final PluginCall call) {
        final String leaderboardId = call.getString("leaderboardId");
        if (leaderboardId == null) {
            Log.w(TAG, "showLeaderboard called without providing leaderboardId");
            return;
        }
        Log.d(TAG, "showLeaderboard called with id: " + leaderboardId);

        final GoogleSignInAccount lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(getContext());
        if (null == lastSignedInAccount) {
            Log.w(TAG,
                    "cannot find last signed in account, either services are disabled or fingerprint doesn't match services account");
            call.resolve();
            return;
        }

        Games.getLeaderboardsClient(getContext(), lastSignedInAccount).getLeaderboardIntent(leaderboardId)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "showLeaderboard:getIntent:success");
                        saveCall(call);
                        startActivityForResult(call, task.getResult(), RC_LEADERBOARD_UI);
                    } else {
                        Log.e(TAG, "showLeaderboard:getIntent:error");
                        call.reject("showLeaderboard:getIntent:error");
                    }
                });
    }

    /**
     * TODO: test and handle/throw errors for non integer values passed to score,
     * not to mention, score can be non-integer values depending on game store and
     * leaderboard settings.
     *
     * @param call Capacitor plugin call
     */
    @PluginMethod()
    public void submitScore(final PluginCall call) {
        final String leaderboardId = call.getString("leaderboardId", "");
        final int score = call.getInt("score", 0);
        Log.d(TAG, String.format("submitScore:leaderboardId:%s:score:%d", leaderboardId, score));

        final GoogleSignInAccount lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(getContext());
        if (null == lastSignedInAccount) {
            Log.w(TAG,
                    "cannot find last signed in account, either services are disabled or fingerprint doesn't match services account");
            call.resolve();
            return;
        }

        Games.getLeaderboardsClient(getContext(), lastSignedInAccount).submitScoreImmediate(leaderboardId, score)
                .addOnSuccessListener(scoreSubmissionData -> {
                    Log.d(TAG, "submitScore:success");
                    call.resolve(new JSObject().put("result", "submitScore:success"));
                }).addOnFailureListener(error -> {
            Log.e(TAG, String.format("submitScore:error:%s", error.getMessage()));
            call.reject(String.format("submitScore:error:%s", error.getMessage()));
        });
    }

    @PluginMethod()
    public void showAchievements(final PluginCall call) {
        Log.d(TAG, "showAchievements:called");

        final GoogleSignInAccount lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(getContext());
        if (null == lastSignedInAccount) {
            Log.w(TAG,
                    "cannot find last signed in account, either services are disabled or fingerprint doesn't match services account");
            call.resolve();
            return;
        }

        Games.getAchievementsClient(getContext(), lastSignedInAccount).getAchievementsIntent()
                .addOnSuccessListener(intent -> {
                    saveCall(call);
                    Log.d(TAG, "showAchievements:success");
                    startActivityForResult(call, intent, RC_ACHIEVEMENT_UI);
                }).addOnFailureListener(error -> {
            Log.e(TAG, "showAchievements:error:" + error.getMessage());
            call.reject("showAchievements:error:" + error.getMessage());
        });
    }

    /**
     * Fails when trying to unlock a incremental achievement TODO: needs to respond
     * to all available errors in error responses
     *
     * @param call Capacitor plugin call
     */
    @PluginMethod()
    public void unlockAchievement(final PluginCall call) {
        final String achievementId = call.getString("achievementId", "");
        Log.d(TAG, String.format("unlockAchievement:%s", achievementId));

        final GoogleSignInAccount lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(getContext());
        if (null == lastSignedInAccount) {
            Log.w(TAG,
                    "cannot find last signed in account, either services are disabled or fingerprint doesn't match services account");
            call.resolve();
            return;
        }

        Games.getAchievementsClient(getContext(), lastSignedInAccount).unlockImmediate(achievementId)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "unlockAchievement:success");
                        call.resolve(new JSObject().put("response", "unlockAchievement:success"));
                    } else {
                        Log.e(TAG, "unlockAchievement:failure" + task.getException());
                        call.reject("unlockAchievement:failure");
                    }
                });
    }

    @PluginMethod()
    public void progressAchievement(final PluginCall call) {
        final String achievementId = call.getString("achievementId");
        if (achievementId == null) {
            call.reject("progressAchievement:error: achievementId not provided");
            return;
        }
        // TODO: does this need error handling when a number that looks like an integer
        // is passed, how
        // does capacitor number conversion work here?
        final double percentComplete = call.getDouble("achievementId", 100.0);
        final int stepsComplete = (int) Math.floor(percentComplete);
        Log.d(TAG, String.format("progressAchievement:%s:percentage:%s", achievementId, stepsComplete));

        final GoogleSignInAccount lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(getContext());
        if (null == lastSignedInAccount) {
            Log.w(TAG,
                    "cannot find last signed in account, either services are disabled or fingerprint doesn't match services account");
            call.resolve();
            return;
        }

        // TODO: do math to get desired increment
        Games.getAchievementsClient(getContext(), lastSignedInAccount).setStepsImmediate(achievementId, stepsComplete)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String successMessage = task.getResult() ? "progressAchievement:success:unlocked"
                                : "progressAchievement:success";
                        Log.d(TAG, successMessage);
                        call.resolve(new JSObject().put("result", successMessage));
                    } else {
                        Log.e(TAG, "progressAchievement:error" + task.getException().getMessage());
                        call.reject("progressAchievement:error");
                    }
                });
    }

    @PluginMethod()
    public void resetAllAchievementProgress(final PluginCall call) {
        Log.w(TAG, "iOS only method, reset achievements in play services console for Android.");
        call.resolve();
    }

    // MARK: Private Methods

    private void startSilentSignIn() {
        final GoogleSignInOptions signInOptions = mGoogleSignInOptions;
        final GoogleSignInClient signInClient = GoogleSignIn.getClient(getContext(), signInOptions);
        final PluginCall savedCall = getSavedCall();

        signInClient.silentSignIn().addOnCompleteListener(getActivity(), (Task<GoogleSignInAccount> task) -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "startSilentSignIn success");
                GoogleSignInAccount signedInAccount = task.getResult();
                this.registerPopupView(signedInAccount);
                Games.getPlayersClient(getContext(), signedInAccount).getCurrentPlayer().addOnCompleteListener(playerClientTask -> {
                    JSObject response = new JSObject();
                    JSObject responseData = new JSObject();
                    responseData.put("player_name", playerClientTask.getResult().getDisplayName());
                    responseData.put("player_id", playerClientTask.getResult().getPlayerId());
                    response.put("response", responseData);
                    savedCall.resolve(response);
                });
            } else {
                Log.d(TAG, "startSilentSignIn error");
                Log.d(TAG, task.getException().getMessage());
                startSignInIntent();
            }
        });
    }

    private void startSignInIntent() {
        Log.d(TAG, "startSignInIntent:called");
        final GoogleSignInClient signInClient = GoogleSignIn.getClient(getContext(), mGoogleSignInOptions);
        final Intent intent = signInClient.getSignInIntent();
        startActivityForResult(null, intent, RC_SIGN_IN);
    }

    private void registerPopupView(final GoogleSignInAccount signInAccount) {
        final GamesClient gamesClient = Games.getGamesClient(this.getContext(), signInAccount);
        final WebView webView = this.getBridge().getWebView();
        gamesClient.setViewForPopups(webView);
    }
}
