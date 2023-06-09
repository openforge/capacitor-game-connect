import { registerPlugin } from '@capacitor/core';

import { GameServicesPlugin } from './definitions';

const GameServices = registerPlugin<GameServicesPlugin>('GameServices', {
  web: () => import('./web').then((m) => new m.GameServicesWeb()),
});

export * from './definitions';
export { GameServices };
