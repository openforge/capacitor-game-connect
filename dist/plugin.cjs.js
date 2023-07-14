'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@capacitor/core');

const CapacitorGameConnect = core.registerPlugin('CapacitorGameConnect', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.CapacitorGameConnectWeb()),
});

class CapacitorGameConnectWeb extends core.WebPlugin {
    /**
     * * Method to sign-in a user to Google Play Services
     *
     */
    async signIn() {
        return Promise.resolve();
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

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    CapacitorGameConnectWeb: CapacitorGameConnectWeb
});

exports.CapacitorGameConnect = CapacitorGameConnect;
//# sourceMappingURL=plugin.cjs.js.map
