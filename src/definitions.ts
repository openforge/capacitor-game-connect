import type { PlayerScore } from './interfaces/player-score.interface';

export type LeaderboardTimeSpan = 'all_time' | 'weekly' | 'daily';

export interface GetUserTotalScoreOptions {
  leaderboardID: string;
  /**
   * Time span to load from Google Play Games leaderboard scores.
   * Defaults to `all_time`.
   */
  timeSpan?: LeaderboardTimeSpan;
}

export interface CapacitorGameConnectPlugin {
  /**
   * * Method to sign-in a user
   *
   *
   */
  signIn(): Promise<{
    player_name: string;
    player_id: string;
  }>;

  /**
   * * Method to display the Leaderboards
   *
   * @param leaderboardID as string
   */
  showLeaderboard(options: { leaderboardID: string }): Promise<void>;

  /**
   * * Method to display all Leaderboards
   *
   */
  showAllLeaderboards(): Promise<void>;

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

  /**
   * * Method to get total player score from a leaderboard
   *
   * @param options { leaderboardID: string; timeSpan?: 'all_time' | 'weekly' | 'daily' }
   */
  getUserTotalScore(options: GetUserTotalScoreOptions): Promise<PlayerScore>;
}
