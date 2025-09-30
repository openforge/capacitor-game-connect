import { CapacitorGameConnectWeb } from '../src/web';

describe('CapacitorGameConnectWeb', () => {
  let plugin: CapacitorGameConnectWeb;

  beforeEach(() => {
    plugin = new CapacitorGameConnectWeb();
  });

  describe('signIn', () => {
    it('should return an empty user object on web', async () => {
      const result = await plugin.signIn();
      expect(result).toEqual({});
    });
  });

  describe('showLeaderboard', () => {
    it('should resolve successfully', async () => {
      const consoleSpy = jest.spyOn(console, 'info').mockImplementation();

      await expect(plugin.showLeaderboard({ leaderboardID: 'test-leaderboard' }))
        .resolves.toBeUndefined();

      expect(consoleSpy).toHaveBeenCalledWith(
        'showLeaderboard function has been called',
        { leaderboardID: 'test-leaderboard' }
      );

      consoleSpy.mockRestore();
    });
  });

  describe('submitScore', () => {
    it('should resolve successfully', async () => {
      const consoleSpy = jest.spyOn(console, 'info').mockImplementation();

      await expect(plugin.submitScore({
        leaderboardID: 'test-leaderboard',
        totalScoreAmount: 100
      })).resolves.toBeUndefined();

      expect(consoleSpy).toHaveBeenCalledWith(
        'submitScore function has been called',
        { leaderboardID: 'test-leaderboard', totalScoreAmount: 100 }
      );

      consoleSpy.mockRestore();
    });
  });

  describe('showAchievements', () => {
    it('should resolve successfully', async () => {
      await expect(plugin.showAchievements()).resolves.toBeUndefined();
    });
  });

  describe('unlockAchievement', () => {
    it('should resolve successfully', async () => {
      const consoleSpy = jest.spyOn(console, 'info').mockImplementation();

      await expect(plugin.unlockAchievement({ achievementID: 'test-achievement' }))
        .resolves.toBeUndefined();

      expect(consoleSpy).toHaveBeenCalledWith(
        'unlockAchievement function has been called',
        { achievementID: 'test-achievement' }
      );

      consoleSpy.mockRestore();
    });
  });

  describe('incrementAchievementProgress', () => {
    it('should resolve successfully', async () => {
      const consoleSpy = jest.spyOn(console, 'info').mockImplementation();

      await expect(plugin.incrementAchievementProgress({
        achievementID: 'test-achievement',
        pointsToIncrement: 10
      })).resolves.toBeUndefined();

      expect(consoleSpy).toHaveBeenCalledWith(
        'incrementAchievementProgress function has been called',
        { achievementID: 'test-achievement', pointsToIncrement: 10 }
      );

      consoleSpy.mockRestore();
    });
  });

  describe('getUserTotalScore', () => {
    it('should return an empty player score object on web', async () => {
      const consoleSpy = jest.spyOn(console, 'info').mockImplementation();

      const result = await plugin.getUserTotalScore({ leaderboardID: 'test-leaderboard' });

      expect(result).toEqual({});
      expect(consoleSpy).toHaveBeenCalledWith(
        'getUserTotalScore function has been called',
        { leaderboardID: 'test-leaderboard' }
      );

      consoleSpy.mockRestore();
    });
  });
});