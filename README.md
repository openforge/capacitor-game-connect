# capacitor-game-connect

Use Game Services and Game Connect

## Install

```bash
npm install capacitor-game-connect
npx cap sync
```

## API

<docgen-index>

* [`signIn()`](#signin)
* [`showLeaderboard(...)`](#showleaderboard)
* [`submitScore(...)`](#submitscore)
* [`showAchievements()`](#showachievements)
* [`unlockAchievement(...)`](#unlockachievement)
* [`incrementAchievementProgress(...)`](#incrementachievementprogress)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### signIn()

```typescript
signIn() => Promise<void>
```

* Method to sign-in a user

--------------------


### showLeaderboard(...)

```typescript
showLeaderboard(options: { leaderboardID: string; }) => Promise<void>
```

* Method to display the Leaderboards

| Param         | Type                                    |
| ------------- | --------------------------------------- |
| **`options`** | <code>{ leaderboardID: string; }</code> |

--------------------


### submitScore(...)

```typescript
submitScore(options: { leaderboardID: string; totalScoreAmount: number; }) => Promise<void>
```

* Method to submit a score to the Google Play Services SDK

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code>{ leaderboardID: string; totalScoreAmount: number; }</code> |

--------------------


### showAchievements()

```typescript
showAchievements() => Promise<void>
```

* Method to display the Achievements view

--------------------


### unlockAchievement(...)

```typescript
unlockAchievement(options: { achievementID: string; }) => Promise<void>
```

* Method to unlock an achievement

| Param         | Type                                    |
| ------------- | --------------------------------------- |
| **`options`** | <code>{ achievementID: string; }</code> |

--------------------


### incrementAchievementProgress(...)

```typescript
incrementAchievementProgress(options: { achievementID: string; pointsToIncrement: number; }) => Promise<void>
```

* Method to increment the progress of an achievement

| Param         | Type                                                               |
| ------------- | ------------------------------------------------------------------ |
| **`options`** | <code>{ achievementID: string; pointsToIncrement: number; }</code> |

--------------------

</docgen-api>
