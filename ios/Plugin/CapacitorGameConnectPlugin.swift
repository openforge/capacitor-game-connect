import Foundation
import Capacitor
import GameKit

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */

@objc(CapacitorGameConnectPlugin)
public class CapacitorGameConnectPlugin: CAPPlugin {
    private let implementation = CapacitorGameConnect()

    @objc func signIn(_ call: CAPPluginCall, _ viewController: UIViewController) {
        let localPlayer = GKLocalPlayer.local
        
        localPlayer.authenticateHandler = { gcAuthVC, error in
            if localPlayer.isAuthenticated {
                call.resolve([
                    "player_name": localPlayer.displayName,
                    "player_id": localPlayer.gamePlayerID,
                ])
            } else if gcAuthVC != nil {
                viewController.present(gcAuthVC!, animated: true)
            } else {
                call.reject("[GameServices] local player is not authenticated")
            }
        }
    }
    
    @objc func showLeaderboard(_ call: CAPPluginCall) {
        implementation.showLeaderboard(call, (self.bridge?.viewController)!)
        call.resolve()
    }
    
    @objc func showAchievements(_ call: CAPPluginCall) {
        implementation.showAchievements(call, (self.bridge?.viewController)!)
        call.resolve()
    }
    
    @objc func submitScore(_ call: CAPPluginCall) {
        implementation.submitScore(call)
        call.resolve()
    }
    
    @objc func unlockAchievement(_ call: CAPPluginCall) {
        implementation.unlockAchievement(call)
        call.resolve()
    }
    
    @objc func incrementAchievementProgress(_ call: CAPPluginCall) {
        implementation.incrementAchievementProgress(call)
        call.resolve()
    }
}
