export interface GameServicesPlugin {
  /**
   * * Function to sign in a user
   *
   */
  signIn(): Promise<{
    player_name: string;
    player_id: string;
  }>;

  /**
   * * Function to sign out a user
   *
   */
  signOut(): Promise<any>;

  /**
   * *  Function to show the a leader board by ID
   *
   * @param options
   */
  showLeaderboard(options: { leaderboardId: string }): Promise<any>;

  /**
   * * Function to submit a score
   *
   * @param options
   */
  submitScore(options: { leaderboardId: string; score: number }): Promise<any>;

  showAchievements(): Promise<any>;

  /**
   * * Function to Unlock Achievement
   *
   */
  unlockAchievement(options: {
    achievementID: string; // * ID of achievement to unlock
  }): Promise<any>;

  /**
   * * Can only increment achievements across platforms with percentages!
   * * set number of steps to maximum in playstore and make 100.00 and 10,000/100 equivalent
   * * the native ui only shows percentages on android, so any percentages need to be calculated
   * * before passing that result to incrementAchievement
   * * would be able to go backwards on ios, but difficult to go backwards on android, would require a reset
   */
  progressAchievement(options: {
    achievementID: string;
    percentComplete: number;
  }): Promise<any>;

  /**
   * * Function to reset achievement
   *
   */
  resetAllAchievementProgress(): Promise<any>;

  /**
   * * Reveal an achievement if its hidden, should only hide achievements with spoilers, refer to google guide for achievements.
   */
  revealAchievement(): Promise<any>;
}
