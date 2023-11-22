import Foundation
import GameKit
import Capacitor

@objc public class CapacitorGameConnect: NSObject, GKGameCenterControllerDelegate {
    public func gameCenterViewControllerDidFinish(_ gameCenterViewController: GKGameCenterViewController) {
        gameCenterViewController.dismiss(animated: true);
    }

    @objc func signIn(_ call: CAPPluginCall,  _ viewController: UIViewController) {
        let localPlayer = GKLocalPlayer.local
        
        localPlayer.authenticateHandler = { gcAuthVC, error in
            if localPlayer.isAuthenticated {
                let result = [
                    "player_name": localPlayer.displayName,
                    "player_id": localPlayer.gamePlayerID
                ]
                call.resolve(result)
            } else if gcAuthVC != nil {
                viewController.present(gcAuthVC!, animated: true)
            } else {
                call.reject("[GameServices] local player is not authenticated")
            }
        }
    }
    
    @objc func showLeaderboard(_ call: CAPPluginCall, _ viewController: UIViewController) {
        let leaderboardID = String(call.getString("leaderboardID") ?? "") // Property to get the leaderboard ID
        DispatchQueue.main.async {
            let leaderboardViewController = GKGameCenterViewController()
            leaderboardViewController.viewState = .leaderboards
            leaderboardViewController.leaderboardIdentifier = leaderboardID
            leaderboardViewController.gameCenterDelegate = self
            leaderboardViewController.leaderboardTimeScope = .allTime
            viewController.present(leaderboardViewController, animated: true)
        }
    }
        
    @objc func showAchievements(_ call: CAPPluginCall, _ viewController: UIViewController) {
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
            viewController.present(achievementsViewController, animated: true)
        }
    }
    
    @objc func submitScore(_ call: CAPPluginCall) {
        let leaderboardID = String(call.getString("leaderboardID") ?? "") // Property to get the leaderboard ID
        let score = Int64(call.getInt("totalScoreAmount") ?? 0) // Property to get the total score to submit
        
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
    
    @objc func unlockAchievement(_ call: CAPPluginCall) {
            print("unlockAchievement:called")
            setProgressAchievement(call, 100.0)
    }
    
    @objc func incrementAchievementProgress(_ call: CAPPluginCall) {
        print("progressAchievement:called")
        setProgressAchievement(call, call.getDouble("pointsToIncrement"))
    }
    
    private func setProgressAchievement(_ call: CAPPluginCall, _ pointsToIncrement: Double?) {
        
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
        let progressComplete = pointsToIncrement ?? 100.0
        
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
    
    @objc func getUserTotalScore(_ call: CAPPluginCall) {
        guard GKLocalPlayer.local.isAuthenticated else {
            print("Player is not authenticated")
            call.reject("Player is not authenticated")
            return
        }
        
        let leaderboardID = String(call.getString("leaderboardID") ?? "") // * Property to get the leaderboard ID
        let leaderboard = GKLeaderboard() // * LeaderBoard functions
        leaderboard.identifier = leaderboardID // * LeaderBoard we are going to use for
        leaderboard.playerScope = .global // * Section to use
        leaderboard.timeScope = .allTime // * Time to search for
        
        leaderboard.loadScores { (scores, error) in
            let hasScore = scores ?? nil
            if hasScore != nil {
                if let error = error {
                    call.reject("Error loading leaderboard score: \(error.localizedDescription)")
                } else if let scores = scores {
                    for score in scores {
                        if score.player.gamePlayerID == GKLocalPlayer.local.gamePlayerID {
                            let result = [
                                "player_score": score.value
                            ]
                            call.resolve(result)
                            break
                        }
                    }
                }
            } else {
                let result = [
                    "player_score": 0
                ]
                call.resolve(result as PluginCallResultData)
            }
        }
    }
}
