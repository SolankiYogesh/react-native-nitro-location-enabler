import { NitroModules } from 'react-native-nitro-modules';
import type { RnNitroLocationEnabler } from './RnNitroLocationEnabler.nitro';
import { Platform } from 'react-native';

const RnNitroLocationEnablerHybridObject =
  NitroModules.createHybridObject<RnNitroLocationEnabler>(
    'RnNitroLocationEnabler'
  );

export function isLocationEnabled(): boolean {
  if (Platform.OS === 'ios') {
    return true;
  }
  return RnNitroLocationEnablerHybridObject.isLocationEnabled();
}
export function requestLocationEnabled(): Promise<boolean> {
  if (Platform.OS === 'ios') {
    return Promise.resolve(true);
  }
  return RnNitroLocationEnablerHybridObject.requestLocationEnabled();
}
