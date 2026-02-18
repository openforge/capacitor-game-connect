import { WebPlugin } from '@capacitor/core';

import type { CapacitorGameConnectPlugin } from './definitions';
import type { PlayerScore } from './interfaces/player-score.interface';
import type { User } from './interfaces/user.interface';

export class CapacitorGameConnectWeb
  extends WebPlugin
  implements CapacitorGameConnectPlugin
{
  /**
   * * Method to sign-in a user to Google Play Services
   * * TODO: migrate interface to a different file
   * * Add web support for signIn
   */
  async signIn(): Promise<User> {
    return Promise.resolve({} as User);
  }

  /**
   * Method to display the Leaderboards view from Google Play Services SDK
   *
   * @param leaderboardID as string
   */
  async showLeaderboard(options: { leaderboardID: string }): Promise<void> {
    console.info('showLeaderboard function has been called', options);
    return Promise.resolve();
  }

  /**
   * * Method to display all Leaderboards
   *
   * @returns Promise
   */
  async showAllLeaderboards(): Promise<void> {
    console.info('showAllLeaderboards function has been called');
    return Promise.resolve();
  }

  /**
   * * Method to submit a score to the Google Play Services SDK
   *
   * @returns Promise
   */
  async submitScore(options: {
    leaderboardID: string;
    totalScoreAmount: number;
  }): Promise<void> {
    console.info('submitScore function has been called', options);
    return Promise.resolve();
  }

  /**
   * * Method to display the Achievements view from Google Play SDK
   *
   * @returns Promise
   */
  async showAchievements(): Promise<void> {
    return Promise.resolve();
  }

  /**
   * * Method to unlock an achievement
   *
   * @returns  Promise
   */
  async unlockAchievement(options: { achievementID: string }): Promise<void> {
    console.info('unlockAchievement function has been called', options);
    return Promise.resolve();
  }

  /**
   * * Method to increment the progress of an achievement
   *
   * @returns Promise
   */
  async incrementAchievementProgress(options: {
    achievementID: string;
    pointsToIncrement: number;
  }): Promise<void> {
    console.info(
      'incrementAchievementProgress function has been called',
      options,
    );
    return Promise.resolve();
  }

  /**
   * * Function to get the total player score from a specific leaderboard
   *
   * @param options { leaderboardID: string }
   * @returns Promise<PlayerScore>
   */
  async getUserTotalScore(options: {
    leaderboardID: string;
  }): Promise<PlayerScore> {
    console.info('getUserTotalScore function has been called', options);
    return Promise.resolve({} as PlayerScore);
  }
}
