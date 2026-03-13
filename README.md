# 📍 RnNitroLocationEnabler

[![npm version](https://img.shields.io/npm/v/rn-nitro-location-enabler.svg?style=flat-square)](https://www.npmjs.com/package/rn-nitro-location-enabler)
[![license](https://img.shields.io/npm/l/rn-nitro-location-enabler.svg?style=flat-square)](https://github.com/SolankiYogesh/rn-nitro-location-enabler/blob/main/LICENSE)
[![platform](https://img.shields.io/badge/platform-android-blue.svg?style=flat-square)](https://github.com/SolankiYogesh/rn-nitro-location-enabler)
[![React Native](https://img.shields.io/badge/React%20Native-0.81+-blue.svg?style=flat-square)](https://reactnative.dev/)

A lightweight React Native module built with **Nitro Modules** that provides seamless location services management for Android applications. Check if location is enabled and request users to enable it with a beautiful native dialog.

## ✨ Features

- 🔍 **Check Location Status**: Determine if location services are enabled on the device
- 🎯 **Request Location Enablement**: Show native Android dialog to enable location services
- 🚀 **Built with Nitro Modules**: High-performance, type-safe React Native module
- 📱 **Android Support**: Comprehensive Android location services integration
- 🔧 **TypeScript Support**: Full TypeScript definitions included
- ⚡ **Promise-based API**: Clean, modern async/await interface

## 📋 Requirements

- `react-native-nitro-modules` ^0.35.0

## 🚀 Installation

```bash
npm install rn-nitro-location-enabler react-native-nitro-modules
```

or

```bash
yarn add rn-nitro-location-enabler react-native-nitro-modules
```

## 🔧 Setup

### Android Configuration

Add the following permissions to your `android/app/src/main/AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

## 📖 Usage

### Basic Example

```typescript
import React, { useState, useEffect } from 'react';
import { View, Text, Button, Alert, StyleSheet } from 'react-native';
import { isLocationEnabled, requestLocationEnabled } from 'rn-nitro-location-enabler';

const LocationComponent = () => {
  const [locationEnabled, setLocationEnabled] = useState(false);

  useEffect(() => {
    // Check initial location status
    const checkLocation = async () => {
      const enabled = isLocationEnabled();
      setLocationEnabled(enabled);
    };
    checkLocation();
  }, []);

  const handleRequestLocation = async () => {
    try {
      const result = await requestLocationEnabled();
      setLocationEnabled(result);

      if (result) {
        Alert.alert('Success', 'Location services have been enabled!');
      } else {
        Alert.alert('Location Required', 'Please enable location services to use this feature.');
      }
    } catch (error) {
      Alert.alert('Error', 'Failed to request location services');
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.status}>
        Location Services: {locationEnabled ? '✅ Enabled' : '❌ Disabled'}
      </Text>
      <Button
        title="Enable Location Services"
        onPress={handleRequestLocation}
        disabled={locationEnabled}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    padding: 20,
    alignItems: 'center',
  },
  status: {
    fontSize: 16,
    marginBottom: 20,
    fontWeight: 'bold',
  },
});

export default LocationComponent;
```

### Advanced Usage with Error Handling

```typescript
import {
  isLocationEnabled,
  requestLocationEnabled,
} from 'rn-nitro-location-enabler';

class LocationService {
  static async ensureLocationEnabled(): Promise<boolean> {
    // Check if location is already enabled
    if (isLocationEnabled()) {
      return true;
    }

    // Request user to enable location
    try {
      const enabled = await requestLocationEnabled();

      if (!enabled) {
        console.warn('User declined to enable location services');
        return false;
      }

      return true;
    } catch (error) {
      console.error('Error requesting location services:', error);
      return false;
    }
  }

  static async withLocation<T>(
    action: () => Promise<T>,
    fallback?: () => Promise<T>
  ): Promise<T> {
    const locationEnabled = await this.ensureLocationEnabled();

    if (!locationEnabled && fallback) {
      return fallback();
    }

    if (!locationEnabled) {
      throw new Error('Location services are required for this operation');
    }

    return action();
  }
}

// Usage
const fetchUserLocation = async () => {
  return await LocationService.withLocation(
    async () => {
      // Your location-dependent code here
      return { latitude: 0, longitude: 0 }; // Example
    },
    async () => {
      // Fallback when location is not available
      return { latitude: null, longitude: null };
    }
  );
};
```

## 📚 API Reference

### `isLocationEnabled(): boolean`

Synchronously checks if location services are enabled on the device.

**Returns:** `boolean` - `true` if location services are enabled, `false` otherwise.

**Example:**

```typescript
const enabled = isLocationEnabled();
console.log(`Location services are ${enabled ? 'enabled' : 'disabled'}`);
```

### `requestLocationEnabled(): Promise<boolean>`

Shows the native Android dialog to request enabling location services.

**Returns:** `Promise<boolean>` - Resolves to `true` if user enabled location, `false` if declined.

**Example:**

```typescript
const result = await requestLocationEnabled();
if (result) {
  // Location services are now enabled
  console.log('Location services enabled successfully');
} else {
  // User declined to enable location
  console.log('User declined to enable location services');
}
```

## 🎯 Use Cases

- **Location-based apps**: Maps, navigation, ride-sharing, food delivery
- **Geofencing applications**: Location-based reminders and notifications
- **Fitness apps**: Track workouts and outdoor activities
- **Social apps**: Find nearby friends or places
- **Emergency services**: Quick access to user location

## 🔍 How It Works

This module uses Android's native location services APIs:

1. **Location Status Check**: Uses `LocationManager` to check if location services are enabled
2. **Location Request Dialog**: Uses Google Play Services `SettingsClient` to show the native location enablement dialog
3. **Result Handling**: Properly handles user responses and activity lifecycle

## 🛠 Development

### Running the Example App

```bash
# Install dependencies
yarn install

# Run the example app
yarn example android
```

### Building from Source

```bash
# Build the module
yarn prepare

# Run tests
yarn test

# Type checking
yarn typecheck

# Linting
yarn lint
```

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guide](CONTRIBUTING.md) for details.

### Development Workflow

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Make your changes and test them in the example app
4. Commit your changes: `git commit -m 'Add amazing feature'`
5. Push to the branch: `git push origin feature/amazing-feature`
6. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Built with [Nitro Modules](https://nitro.margelo.com/) for high-performance React Native modules
- Created using [create-react-native-library](https://github.com/callstack/react-native-builder-bob)

## 📞 Support

- 📧 **Email**: solankiyogesh3500@gmail.com
- 🐛 **Issues**: [GitHub Issues](https://github.com/SolankiYogesh/rn-nitro-location-enabler/issues)
- 💻 **Repository**: [GitHub](https://github.com/SolankiYogesh/rn-nitro-location-enabler)

---

<div align="center">

Made with ❤️ by [Yogesh Solanki](https://github.com/SolankiYogesh)

</div>
