import { WebPlugin } from '@capacitor/core';
import { GameConnectPlugin } from './definitions';

export class GameConnectWeb extends WebPlugin implements GameConnectPlugin {
  signIn(): Promise<any> {
    console.warn('GameConnect does not have web implementation.');
    return Promise.resolve();
  }

  signOut(): Promise<any> {
    console.warn('GameConnect does not have web implementation.');
    return Promise.resolve();
  }

  showLeaderboard(_: { leaderboardId: string }): Promise<any> {
    console.warn('GameConnect does not have web implementation.');
    return Promise.resolve();
  }

  submitScore(_: { leaderboardId: string; score: number }): Promise<any> {
    console.warn('GameConnect does not have web implementation.');
    return Promise.resolve();
  }

  showAchievements(): Promise<any> {
    console.warn('GameConnect does not have web implementation.');
    return Promise.resolve();
  }

  unlockAchievement(_: { achievementID: string }): Promise<any> {
    console.warn('GameConnect does not have web implementation.');
    return Promise.resolve();
  }

  progressAchievement(_: {
    achievementID: string;
    percentComplete: number;
  }): Promise<any> {
    console.warn('GameConnect does not have web implementation.');
    return Promise.resolve();
  }

  resetAllAchievementProgress(): Promise<any> {
    console.warn('GameConnect does not have web implementation.');
    return Promise.resolve();
  }

  revealAchievement(): Promise<any> {
    console.warn('GameConnect does not have web implementation.');
    return Promise.resolve();
  }
}
