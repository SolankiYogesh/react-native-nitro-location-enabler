import { NitroModules } from 'react-native-nitro-modules';
import type { RnNitroLocationEnabler } from './RnNitroLocationEnabler.nitro';
import { Platform } from 'react-native';

let Module: RnNitroLocationEnabler | null = null;

try {
  Module = NitroModules.createHybridObject<RnNitroLocationEnabler>(
    'RnNitroLocationEnabler'
  );
} catch (error) {
  Module = null;
}

export function isLocationEnabled(): boolean {
  if (Platform.OS === 'ios') {
    return true;
  }
  return Module?.isLocationEnabled() ?? true;
}
export function requestLocationEnabled(): Promise<boolean> {
  if (Platform.OS === 'ios') {
    return Promise.resolve(true);
  }
  return Module?.requestLocationEnabled() ?? Promise.resolve(true);
}
