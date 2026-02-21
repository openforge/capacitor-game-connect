import { CapacitorGameConnect } from '../src';

describe('CapacitorGameConnect Plugin', () => {
  it('should be defined', () => {
    expect(CapacitorGameConnect).toBeDefined();
  });

  it('should have all required methods', () => {
    expect(typeof CapacitorGameConnect.signIn).toBe('function');
    expect(typeof CapacitorGameConnect.showLeaderboard).toBe('function');
    expect(typeof CapacitorGameConnect.submitScore).toBe('function');
    expect(typeof CapacitorGameConnect.showAchievements).toBe('function');
    expect(typeof CapacitorGameConnect.unlockAchievement).toBe('function');
    expect(typeof CapacitorGameConnect.incrementAchievementProgress).toBe('function');
    expect(typeof CapacitorGameConnect.getUserTotalScore).toBe('function');
  });
});