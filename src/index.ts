import { registerPlugin } from '@capacitor/core';

import { GameConnectPlugin } from './definitions';

const GameConnect = registerPlugin<GameConnectPlugin>('GameConnect', {
  web: () => import('./web').then((m) => new m.GameConnectWeb()),
});

export * from './definitions';
export { GameConnect };
