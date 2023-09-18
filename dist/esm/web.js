import { WebPlugin } from '@capacitor/core';
export class CapacitorGameConnectWeb extends WebPlugin {
    /**
     * * Method to sign-in a user to Google Play Services
     * * TODO: migrate interface to a different file
     * * Add web support for signIn
     */
    async signIn() {
        return Promise.resolve({});
    }
    /**
     * Method to display the Leaderboards view from Google Play Services SDK
     *
     * @param leaderboardID as string
     */
    async showLeaderboard(options) {
        console.info('showLeaderboard function has been called', options);
        return Promise.resolve();
    }
    /**
     * * Method to submit a score to the Google Play Services SDK
     *
     * @returns Promise
     */
    async submitScore(options) {
        console.info('submitScore function has been called', options);
        return Promise.resolve();
    }
    /**
     * * Method to display the Achievements view from Google Play SDK
     *
     * @returns Promise
     */
    async showAchievements() {
        return Promise.resolve();
    }
    /**
     * * Method to unlock an achievement
     *
     * @returns  Promise
     */
    async unlockAchievement(options) {
        console.info('unlockAchievement function has been called', options);
        return Promise.resolve();
    }
    /**
     * * Method to increment the progress of an achievement
     *
     * @returns Promise
     */
    async incrementAchievementProgress(options) {
        console.info('incrementAchievementProgress function has been called', options);
        return Promise.resolve();
    }
}
//# sourceMappingURL=web.js.map