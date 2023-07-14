package com.openforge.capacitorgameconnect;

import android.content.Intent;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.android.gms.games.PlayGamesSdk;

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
        implementation.signIn(call);
        call.resolve();
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