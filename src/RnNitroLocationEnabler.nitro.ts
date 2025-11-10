import type { HybridObject } from 'react-native-nitro-modules';

export interface RnNitroLocationEnabler
  extends HybridObject<{ android: 'kotlin' }> {
  requestLocationEnabled(): Promise<boolean>;
  isLocationEnabled(): boolean;
}
