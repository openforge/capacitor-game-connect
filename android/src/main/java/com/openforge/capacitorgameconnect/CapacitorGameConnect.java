package com.openforge.capacitorgameconnect;

import android.content.Intent;
import android.util.Log;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import com.google.android.gms.games.AnnotatedData;
import com.google.android.gms.games.GamesSignInClient;
import com.google.android.gms.games.PlayGames;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.games.signin.AuthenticationResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class CapacitorGameConnect {

    private AppCompatActivity activity;
    private static final String TAG = "CapacitorGameConnect";

    public CapacitorGameConnect(AppCompatActivity activity) {
        this.activity = activity;
    }

    /**
     * * Method to sign-in a user to Google Play Services
     *
     * @param call as PluginCall
     * @param resultCallback as SignInCallback
     */
    public void signIn(PluginCall call, final SignInCallback resultCallback) {
        Log.i(TAG, "SignIn method called");
        GamesSignInClient gamesSignInClient = PlayGames.getGamesSignInClient(this.activity);

        gamesSignInClient
            .isAuthenticated()
            .addOnCompleteListener(
                isAuthenticatedTask -> {
                    boolean isAuthenticated = (isAuthenticatedTask.isSuccessful() && isAuthenticatedTask.getResult().isAuthenticated());

                    if (isAuthenticated) {
                        Log.i(TAG, "User is already authenticated");
                        resultCallback.success();
                    } else {
                        gamesSignInClient
                            .signIn()
                            .addOnCompleteListener(
                                data -> {
                                    if (!data.isSuccessful()) {
                                        resultCallback.error("Sign-in failed");
                                        return;
                                    }
                                    AuthenticationResult authResult = data.getResult();
                                    if (authResult != null && authResult.isAuthenticated()) {
                                        Log.i(TAG, "Sign-in completed successful");
                                        resultCallback.success();
                                    } else {
                                        resultCallback.error("User is not authenticated");
                                    }
                                }
                            )
                            .addOnFailureListener(e -> resultCallback.error(e.getMessage()));
                    }
                }
            )
            .addOnFailureListener(e -> resultCallback.error(e.getMessage()));
    }

    /**
     * * Method to fetch the logged in Player
     *
     * @param resultCallback as PlayerResultCallback
     */
    public void fetchUserInformation(final PlayerResultCallback resultCallback) {
        PlayGames
            .getPlayersClient(this.activity)
            .getCurrentPlayer()
            .addOnSuccessListener(
                player -> {
                    resultCallback.success(player);
                }
            )
            .addOnFailureListener(e -> resultCallback.error(e.getMessage()));
    }

    /**
     * * Method to display the Leaderboards view from Google Play Services SDK
     *
     * @param call as PluginCall
     * @param startActivityIntent as ActivityResultLauncher<Intent>
     */
    public void showLeaderboard(PluginCall call, ActivityResultLauncher<Intent> startActivityIntent) {
        Log.i(TAG, "showLeaderboard has been called");
        var leaderboardID = call.getString("leaderboardID");
        if (leaderboardID == null || leaderboardID.isBlank()) {
            call.reject("leaderboardID is required");
            return;
        }
        PlayGames
            .getLeaderboardsClient(this.activity)
            .getLeaderboardIntent(leaderboardID)
            .addOnSuccessListener(
                new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityIntent.launch(intent);
                        call.resolve();
                    }
                }
            )
            .addOnFailureListener(e -> call.reject("Unable to open leaderboard: " + e.getMessage()));
    }

    /**
     * * Method to display ALL Leaderboards from Google Play Services SDK
     *
     * @param startActivityIntent as ActivityResultLauncher<Intent>
     */
    public void showAllLeaderboards(PluginCall call, ActivityResultLauncher<Intent> startActivityIntent) {
        Log.i(TAG, "showAllLeaderboards has been called");
        PlayGames
            .getLeaderboardsClient(this.activity)
            .getAllLeaderboardsIntent()
            .addOnSuccessListener(
                new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityIntent.launch(intent);
                        call.resolve();
                    }
                }
            )
            .addOnFailureListener(e -> call.reject("Unable to open leaderboards: " + e.getMessage()));
    }

    /**
     * * Method to submit a score to the Google Play Services SDK
     *
     * @param call as PluginCall
     */
    public void submitScore(PluginCall call) {
        Log.i(TAG, "submitScore has been called");
        var leaderboardID = call.getString("leaderboardID");
        var totalScoreAmount = call.getInt("totalScoreAmount");
        PlayGames.getLeaderboardsClient(this.activity).submitScore(leaderboardID, totalScoreAmount);
    }

    /**
     * * Method to display the Achievements view from Google Play SDK
     *
     * @param startActivityIntent as ActivityResultLauncher<Intent>
     */
    public void showAchievements(PluginCall call, ActivityResultLauncher<Intent> startActivityIntent) {
        Log.i(TAG, "showAchievements has been called");
        PlayGames
            .getAchievementsClient(this.activity)
            .getAchievementsIntent()
            .addOnSuccessListener(
                new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityIntent.launch(intent);
                        call.resolve();
                    }
                }
            )
            .addOnFailureListener(e -> call.reject("Unable to open achievements: " + e.getMessage()));
    }

    /**
     * * Method to unlock an achievement
     *
     */
    public void unlockAchievement(PluginCall call) {
        Log.i(TAG, "unlockAchievement has been called");
        var achievementID = call.getString("achievementID");
        PlayGames.getAchievementsClient(this.activity).unlock(achievementID);
    }

    /**
     * * Method to increment the progress of an achievement
     *
     */
    public void incrementAchievementProgress(PluginCall call) {
        Log.i(TAG, "incrementAchievementProgress has been called");
        var achievementID = call.getString("achievementID");
        var pointsToIncrement = call.getInt("pointsToIncrement");
        PlayGames.getAchievementsClient(this.activity).increment(achievementID, pointsToIncrement);
    }

    /**
     * * Method to get the total player score from a leaderboard
     *
     */
    public void getUserTotalScore(PluginCall call) {
        Log.i(TAG, "getUserTotalScore has been called");
        var leaderboardID = call.getString("leaderboardID");
        if (leaderboardID == null || leaderboardID.isBlank()) {
            call.reject("leaderboardID is required");
            return;
        }

        int timeSpan = getLeaderboardTimeSpan(call.getString("timeSpan"));
        var leaderboardScore = PlayGames
            .getLeaderboardsClient(this.activity)
            .loadCurrentPlayerLeaderboardScore(leaderboardID, timeSpan, LeaderboardVariant.COLLECTION_PUBLIC);
        leaderboardScore
            .addOnSuccessListener(
                new OnSuccessListener<AnnotatedData<LeaderboardScore>>() {
                    @Override
                    public void onSuccess(AnnotatedData<LeaderboardScore> leaderboardScoreAnnotatedData) {
                        long userTotalScore = 0;
                        if (leaderboardScoreAnnotatedData.get() != null) {
                            userTotalScore = leaderboardScoreAnnotatedData.get().getRawScore();
                        }
                        JSObject result = new JSObject();
                        result.put("player_score", userTotalScore);
                        call.resolve(result);
                    }
                }
            )
            .addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        call.reject("Error getting player score: " + e.getMessage());
                    }
                }
            );
    }

    private int getLeaderboardTimeSpan(String timeSpan) {
        if (timeSpan == null) {
            return LeaderboardVariant.TIME_SPAN_ALL_TIME;
        }

        switch (timeSpan) {
            case "daily":
                return LeaderboardVariant.TIME_SPAN_DAILY;
            case "weekly":
                return LeaderboardVariant.TIME_SPAN_WEEKLY;
            case "all_time":
            default:
                return LeaderboardVariant.TIME_SPAN_ALL_TIME;
        }
    }
}
