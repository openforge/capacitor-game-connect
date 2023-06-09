<p align="center">
  <img src="https://github.com/openforge/main-website/blob/master/src/assets/logo-openforge.png?raw=true"/>
</p>
<p align="center">
  <a href="http://www.openforge.io/">Official Website</a> |
  <a href="http://www.openforge.io/opportunities">Opportunities</a> |
  <a href="https://www.facebook.com/openforgemobile/">Facebook</a>
</p>

<h3 align="center">
  Leading By Example.
</h3>

<p align="center">
  Working with the latest technologies, designing the best products, and sharing our knowledge with the world wide community.
</p>

## Installation
Run `npm install @openforge/capacitor-game-connect`

## How To Use
1. Import the plugin into your `ts` file as `import { GameServices } from '@openforge/capacitor-game-connect';`

## Supported Methods

| Method                           | Description                                          |
| -------------------------------- | ---------------------------------------------------- |
| signIn                           | Method to sign in a user to Game Services            |
| showLeaderboard                  | Method to open Leaderboard view from Game Services   |
| showAchievements                 | Method to open Achievements view from Game Services  |
| submitScore                      | Method to submit a score to the leaderboards table   |
| unlockAchievement                | Method to submit a score to the leaderboards table   |
| progressAchievement              | Method to set some progress to an achievement        |
| resetAllAchievementProgress      | Method to set some reset all achievements            |

## Required

1. Google Developer Account
2. Apple Developer Account

## Setting up Google Play Services

1. Go to [Google Play Console](https://play.google.com/apps/publish).
2. Click New Game button and fill out name and category fields.
3. Click Linked apps tab in side menu, and click the Android button.
4. Enter your appId, should be found in your capacitor.config.json file.
5. Click Save and Continue, then click "authorize your app now" button.
6. Enter your sha-1 and confirm
7. In your native android project, add your app id string to your strings xml file.
    - your strings xml file can be found in android/app/src/main/res/values/strings.xml
8. Go to testing tab and add test account.
9. Add the plugin to main activity after running npx cap sync

## Setting up Apple Game Services

1. Click on target app in xcode
2. Add team to signing and capabilities
3. Add game center capability
4. Go to your apps in https://appstoreconnect.apple.com/ and add your application
5. go to features, add leaderboard matching the generated id from google

## Adding Leaderboards and Achievements

When adding resouces to Google and Apple services, we would recommend you add resources to Google's Play Services first, and reuse the id values generated for resources in Apple's Game Center service. That way you wont need two sets of resource id's.

## Using the plugin

In order to use the plugin, you need to follow this steps:

- Include this meta data tags in the main manifest

```xml
<meta-data android:name="com.google.android.gms.games.APP_ID" android:value="@string/app_id" />
<meta-data android:name="com.google.android.gms.version" android:value="@integer/google_play_services_version"/>
```

- Add on build gradle (app):

```any
implementation 'com.google.android.gms:play-services-auth:17.0.0'
implementation 'com.google.android.gms:play-services-games:19.0.0'
```

- On the Main Activy add:

```android
import io.openforge.gameservices.GameServices;
...
add(GameServices.class);
...
```

Once all this steps are done, you just need to call the sign method of the plugin.

```ts
import { GameServices } from '@openforge/capacitor-game-connect';
await GameServices.signIn();
```
