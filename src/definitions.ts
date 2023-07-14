export interface CapacitorGameConnectPlugin {
  /**
   * * Method to sign-in a user
   *
   *
   */
  signIn(): Promise<void>;

  /**
   * * Method to display the Leaderboards
   *
   * @param leaderboardID as string
   */
  showLeaderboard(options: { leaderboardID: string }): Promise<void>;

  /**
   * * Method to submit a score to the Google Play Services SDK
   *
   */
  submitScore(options: {
    leaderboardID: string;
    totalScoreAmount: number;
  }): Promise<void>;

  /**
   * * Method to display the Achievements view
   *
   */
  showAchievements(): Promise<void>;

  /**
   * * Method to unlock an achievement
   *
   */
  unlockAchievement(options: { achievementID: string }): Promise<void>;

  /**
   * * Method to increment the progress of an achievement
   */
  incrementAchievementProgress(options: {
    achievementID: string;
    pointsToIncrement: number;
  }): Promise<void>;
}
