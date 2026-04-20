# Capacitor v8 Migration Checklist

This checklist is focused on validating Game Center (iOS) and Google Play Games Services (Android) for this plugin after upgrading a host app from Capacitor v5 to v8.

## 1) Prerequisites

- [ ] Upgrade host app Capacitor packages to v8.
- [ ] Run `npx cap sync` and confirm both native projects update cleanly.
- [ ] Ensure Android and iOS native toolchains match Capacitor v8 requirements.

## 2) Android (Google Play Games Services)

- [ ] Confirm `com.google.android.gms.games.APP_ID` metadata is present in app `AndroidManifest.xml`.
- [ ] Confirm `@string/game_services_project_id` is configured in `strings.xml`.
- [ ] Verify OAuth + SHA-1/SHA-256 credential setup in Google Cloud Console.
- [ ] Verify the game service project is published in Play Console.
- [ ] Validate plugin methods:
  - [ ] `signIn()` returns user profile.
  - [ ] `showLeaderboard({ leaderboardID })` opens selected leaderboard.
  - [ ] `showAllLeaderboards()` opens all leaderboards.
  - [ ] `submitScore({ leaderboardID, totalScoreAmount })` writes score.
  - [ ] `showAchievements()` opens achievements UI.
  - [ ] `unlockAchievement({ achievementID })` unlocks achievement.
  - [ ] `incrementAchievementProgress({ achievementID, pointsToIncrement })` increments achievement.

## 3) iOS (Game Center)

- [ ] Add and verify Game Center capability in Xcode.
- [ ] Confirm leaderboard and achievement IDs are configured in App Store Connect.
- [ ] Test on physical devices (Game Center behavior may differ on simulators).
- [ ] Validate plugin methods:
  - [ ] `signIn()` authenticates and returns player metadata.
  - [ ] `showLeaderboard({ leaderboardID })` opens selected leaderboard.
  - [ ] `showAllLeaderboards()` opens Game Center leaderboard list.
  - [ ] `submitScore({ leaderboardID, totalScoreAmount })` reports score.
  - [ ] `showAchievements()` opens achievements UI.
  - [ ] `unlockAchievement({ achievementID })` unlocks achievement.
  - [ ] `incrementAchievementProgress({ achievementID, pointsToIncrement })` updates percentage.

## 4) Failure-path checks

- [ ] Missing leaderboard ID returns reject/error.
- [ ] Missing achievement ID returns reject/error.
- [ ] Unauthenticated player flow returns reject/error and app handles retry.

## 5) Release

- [ ] Run a closed test track build on Play Console.
- [ ] Run TestFlight with Game Center enabled.
- [ ] Verify score/achievement writes appear in both consoles.
