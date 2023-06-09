import Foundation
import Capacitor
import GameKit
import AuthenticationServices

@available(iOS 13.0, *)
@objc(GameServices)
public class GameServices: CAPPlugin, GKGameCenterControllerDelegate {
    var call: CAPPluginCall?

    public func gameCenterViewControllerDidFinish(_ gameCenterViewController: GKGameCenterViewController) {
        gameCenterViewController.dismiss(animated: true, completion: nil)
    }
        
    @objc func signIn(_ call: CAPPluginCall) {
        self.call = call
        let localPlayer = GKLocalPlayer.local
        
        localPlayer.authenticateHandler = { gcAuthVC, error in
            if localPlayer.isAuthenticated {
                print("User is authenticated to Game Center!")
                let result = [
                    "player_name": localPlayer.displayName,
                    "player_id": localPlayer.gamePlayerID
                ]
                call.resolve(result)
            } else if gcAuthVC != nil {
                self.bridge?.viewController?.present(gcAuthVC!, animated: true)
            } else {
                call.reject("[GameServices] local player is not authenticated")
            }
        }
    }
    
    @objc func showLeaderboard(_ call: CAPPluginCall) {
        let leaderboardID = String(call.getString("leaderboardId") ?? "") // Property to get the leaderboard ID
        DispatchQueue.main.async {
            self.call = call
            let leaderboardViewController = GKGameCenterViewController()
            leaderboardViewController.viewState = .leaderboards
            leaderboardViewController.leaderboardIdentifier = leaderboardID
            leaderboardViewController.gameCenterDelegate = self
            leaderboardViewController.leaderboardTimeScope = .allTime
            self.bridge?.viewController?.present(leaderboardViewController, animated: true, completion: nil)
        }
    }
    
    @objc func showAchievements(_ call: CAPPluginCall) {
        self.call = call
        
        guard GKLocalPlayer.local.isAuthenticated else {
            print("Player is not authenticated")
            call.reject("Player is not authenticated")
            return
        }
        
        print("[GameServices] Showing Achievements")
        DispatchQueue.main.async {
            let achievementsViewController = GKGameCenterViewController()
            achievementsViewController.gameCenterDelegate = self
            achievementsViewController.viewState = .achievements
            self.bridge?.viewController?.present(achievementsViewController, animated: true, completion: nil)
        }
    }
    
    @objc func submitScore(_ call: CAPPluginCall) {
        self.call = call
        
        let leaderboardID = String(call.getString("leaderboardId") ?? "") // Property to get the leaderboard ID
        let score = Int64(call.getInt("score") ?? 0) // Property to get the total score to submit
        
        guard GKLocalPlayer.local.isAuthenticated else {
            print("Player is not authenticated")
            call.reject("Player is not authenticated")
            return
        }
        
        let scoreReporter = GKScore(leaderboardIdentifier: leaderboardID)
        scoreReporter.value = Int64(score)
        scoreReporter.context = 0
        
        let scoreArray: [GKScore] = [scoreReporter]
        
        GKScore.report(scoreArray, withCompletionHandler: { error in
            if let error = error {
                // Handle score submission error
                print("Score submission failed with error: \(error.localizedDescription)")
                call.reject("Score submission failed, try again.")
            } else {
                let result = [
                    "type": "sucess",
                    "message": "Score has been submitted successfully"
                ]
                // Score submitted successfully
                print("Score submitted")
                call.resolve(result as PluginCallResultData)
            }
        })
    }
    
//    @objc func getUserScore(_ call: CAPPluginCall) {
//        self.call = call
//
//        let leaderboardID = call.getString("leaderboardId") // Property to get the leaderboard
//
//        let leaderboard = GKLeaderboard(players: nil)
//        leaderboard.playerScope = .global
//        leaderboard.timeScope = .allTime
//        leaderboard.identifier = leaderboardID
//        leaderboard.range = NSRange(location: 1, length: 1)
//
//        leaderboard.loadEntries { (scores, error) in
//            if let error = error {
//                call.reject("Failed to load the entries for the leaderboards", error.localizedDescription)
//            } else if let scores = scores {
//                if let topScore = scores.first {
//                    let totalScore = topScore.value
//                    print("Total score: \(totalScore)")
//                    call.resolve(totalScore)
//                }
//            }
//        }
//    }
    
    @objc func unlockAchievement(_ call: CAPPluginCall) {
        print("unlockAchievement:called")
        setProgressAchievement(call, 100.0)
    }
    
    @objc func progressAchievement(_ call: CAPPluginCall) {
        print("progressAchievement:called")
        setProgressAchievement(call, call.getDouble("percentComplete"))
    }
    
    @objc func resetAllAchievementProgress(_ call: CAPPluginCall) {
        self.call = call
        let result = [
            "response": []
        ]
        print("resetAllAchievementProgress:called")
        
        GKAchievement.resetAchievements(completionHandler: { error in
            guard error == nil else {
                let errorMessage = "[GameServices] \(error?.localizedDescription ?? "Some Achievements Error")"
                print(errorMessage)
                call.reject(errorMessage)
                return
            }
            print("[GameServices] Reset Achievement Called")
            call.resolve(result as PluginCallResultData)
        })
    }
    
    private func setProgressAchievement(_ call: CAPPluginCall, _ percentComplete: Double?) {
        self.call = call
        
        guard GKLocalPlayer.local.isAuthenticated else {
            print("Player is not authenticated")
            call.reject("Player is not authenticated")
            return
        }
        
        let result = [
            "type": "success",
            "message": "Achievement Progress Was Updating"
        ]
        
        let achievementID = call.getString("achievementID") ?? ""
        let progressComplete = percentComplete ?? 100.0
        
        print("[GameServices] Setting Achievement Percentage \(progressComplete)")
                        
        let achievementToComplete = GKAchievement(identifier: achievementID)
        achievementToComplete.showsCompletionBanner = true
        achievementToComplete.percentComplete = progressComplete
        
        GKAchievement.report([achievementToComplete]) { error in
            guard error == nil else {
                print("Error updating achievement \(error?.localizedDescription ?? "")")
                call.reject("Error updating achievement \(error?.localizedDescription ?? "")")
                return
            }
            call.resolve(result as PluginCallResultData)
        }
    }
}
