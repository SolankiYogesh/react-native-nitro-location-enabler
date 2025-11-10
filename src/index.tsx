import { NitroModules } from 'react-native-nitro-modules';
import type { RnNitroLocationEnabler } from './RnNitroLocationEnabler.nitro';

const RnNitroLocationEnablerHybridObject =
  NitroModules.createHybridObject<RnNitroLocationEnabler>('RnNitroLocationEnabler');

export function multiply(a: number, b: number): number {
  return RnNitroLocationEnablerHybridObject.multiply(a, b);
}
