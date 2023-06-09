#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(GameConnect, "GameConnect",
    CAP_PLUGIN_METHOD(signIn, CAPPluginReturnPromise);
    CAP_PLUGIN_METHOD(showLeaderboard, CAPPluginReturnPromise);
    CAP_PLUGIN_METHOD(submitScore, CAPPluginReturnPromise);
    CAP_PLUGIN_METHOD(showAchievements, CAPPluginReturnPromise);
    CAP_PLUGIN_METHOD(unlockAchievement, CAPPluginReturnPromise);
    CAP_PLUGIN_METHOD(progressAchievement, CAPPluginReturnPromise);
    CAP_PLUGIN_METHOD(resetAllAchievementProgress, CAPPluginReturnPromise);
)
