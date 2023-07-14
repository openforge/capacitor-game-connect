import { registerPlugin } from '@capacitor/core';

import type { CapacitorGameConnectPlugin } from './definitions';

const CapacitorGameConnect = registerPlugin<CapacitorGameConnectPlugin>(
  'CapacitorGameConnect',
  {
    web: () => import('./web').then(m => new m.CapacitorGameConnectWeb()),
  },
);

export * from './definitions';
export { CapacitorGameConnect };
