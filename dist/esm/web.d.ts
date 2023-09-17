import { WebPlugin } from '@capacitor/core';
import type { CapacitorGameConnectPlugin } from './definitions';
export declare class CapacitorGameConnectWeb extends WebPlugin implements CapacitorGameConnectPlugin {
    /**
     * * Method to sign-in a user to Google Play Services
     *
     */
    signIn(): Promise<any>;
    /**
     * Method to display the Leaderboards view from Google Play Services SDK
     *
     * @param leaderboardID as string
     */
    showLeaderboard(options: {
        leaderboardID: string;
    }): Promise<void>;
    /**
     * * Method to submit a score to the Google Play Services SDK
     *
     * @returns Promise
     */
    submitScore(options: {
        leaderboardID: string;
        totalScoreAmount: number;
    }): Promise<void>;
    /**
     * * Method to display the Achievements view from Google Play SDK
     *
     * @returns Promise
     */
    showAchievements(): Promise<void>;
    /**
     * * Method to unlock an achievement
     *
     * @returns  Promise
     */
    unlockAchievement(options: {
        achievementID: string;
    }): Promise<void>;
    /**
     * * Method to increment the progress of an achievement
     *
     * @returns Promise
     */
    incrementAchievementProgress(options: {
        achievementID: string;
        pointsToIncrement: number;
    }): Promise<void>;
}
