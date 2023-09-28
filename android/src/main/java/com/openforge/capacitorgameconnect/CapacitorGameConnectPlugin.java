package com.openforge.capacitorgameconnect;

import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.android.gms.games.PlayGamesSdk;
import com.google.android.gms.games.Player;

@CapacitorPlugin(name = "CapacitorGameConnect")
public class CapacitorGameConnectPlugin extends Plugin {

    private CapacitorGameConnect implementation;
    private ActivityResultLauncher<Intent> startActivityIntent;

    @Override
    public void load() {
        PlayGamesSdk.initialize(getContext());
        startActivityIntent =
                getActivity()
                        .registerForActivityResult(
                                new ActivityResultContracts.StartActivityForResult(),
                                new ActivityResultCallback<ActivityResult>() {
                                    @Override
                                    public void onActivityResult(ActivityResult result) {
                                        // Add same code that you want to add in onActivityResult method
                                    }
                                }
                        );
        implementation = new CapacitorGameConnect(getActivity());
    }

    @PluginMethod
    public void signIn(PluginCall call) {
        implementation.signIn(call, new SignInCallback() {
            @Override
            public void success() {
                implementation.fetchUserInformation(new PlayerResultCallback() {
                    @Override
                    public void success(Player player) {
                        String playerId = player.getPlayerId();
                        String playerName = player.getDisplayName();

                        JSObject ret = new JSObject();
                        ret.put("player_id", playerId);
                        ret.put("player_name", playerName);
                        call.resolve(ret);
                    }

                    @Override
                    public void error(String message) {
                        call.reject(message);
                    }
                });
            }

            @Override
            public void error(String message) {
                call.reject(message);
            }
        });
    }

    @PluginMethod
    public void showLeaderboard(PluginCall call) {
        implementation.showLeaderboard(call, this.startActivityIntent);
        call.resolve();
    }

    @PluginMethod
    public void submitScore(PluginCall call) {
        implementation.submitScore(call);
        call.resolve();
    }

    @PluginMethod
    public void showAchievements(PluginCall call) {
        implementation.showAchievements(this.startActivityIntent);
        call.resolve();
    }

    @PluginMethod
    public void unlockAchievement(PluginCall call) {
        implementation.unlockAchievement(call);
        call.resolve();
    }

    @PluginMethod
    public void incrementAchievementProgress(PluginCall call) {
        implementation.incrementAchievementProgress(call);
        call.resolve();
    }
}