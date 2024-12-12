import { registerPlugin } from '@capacitor/core';
const CapacitorGameConnect = registerPlugin('CapacitorGameConnect', {
    web: () => import('./web').then(m => new m.CapacitorGameConnectWeb()),
});
export * from './definitions';
export { CapacitorGameConnect };
//# sourceMappingURL=index.js.map