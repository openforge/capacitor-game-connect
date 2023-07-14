package com.openforge.capacitorgameconnect;

import android.content.Intent;
import android.util.Log;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import com.getcapacitor.PluginCall;
import com.google.android.gms.games.GamesSignInClient;
import com.google.android.gms.games.PlayGames;
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
     */
    public void signIn(PluginCall call) {
        Log.i(TAG, "SignIn method called");
        GamesSignInClient gamesSignInClient = PlayGames.getGamesSignInClient(this.activity);

        gamesSignInClient
                .isAuthenticated()
                .addOnCompleteListener(
                        isAuthenticatedTask -> {
                            boolean isAuthenticated = (isAuthenticatedTask.isSuccessful() && isAuthenticatedTask.getResult().isAuthenticated());

                            if (isAuthenticated) {
                                Log.i(TAG, "User is already authenticated");
                                call.resolve();
                            } else {
                                gamesSignInClient
                                        .signIn()
                                        .addOnCompleteListener(
                                                data -> {
                                                    Log.i(TAG, "Sign-in completed successful");
                                                }
                                        );
                            }
                        }
                );
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
        PlayGames
                .getLeaderboardsClient(this.activity)
                .getLeaderboardIntent(leaderboardID)
                .addOnSuccessListener(
                        new OnSuccessListener<Intent>() {
                            @Override
                            public void onSuccess(Intent intent) {
                                startActivityIntent.launch(intent);
                            }
                        }
                );
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
    public void showAchievements(ActivityResultLauncher<Intent> startActivityIntent) {
        Log.i(TAG, "showAchievements has been called");
        PlayGames
                .getAchievementsClient(this.activity)
                .getAchievementsIntent()
                .addOnSuccessListener(
                        new OnSuccessListener<Intent>() {
                            @Override
                            public void onSuccess(Intent intent) {
                                startActivityIntent.launch(intent);
                            }
                        }
                );
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
}