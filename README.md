# DEVIL OPTIMAIZ – FF BOOSTER PRO

Android Studio project built with Kotlin and Jetpack Compose.

## Current development build: 1.0.1

### Working in this build
- Red/black branded welcome and navigation screens.
- Device brand and model/series selection UI (selection is currently local only).
- Free Fire and Free Fire MAX launch shortcuts using Android package launch intents.
- Clear messages when a supported game package is not installed or cannot be launched.
- Sensitivity profile category list (categories only; no tuned values are included yet).
- Scrollable screens for smaller phone displays.
- Preview-only entry flow that does not pretend to authenticate an invitation PIN.
- Dashboard placeholders that do not fabricate FPS, CPU, GPU, RAM or temperature readings.

### Not implemented yet
- Firebase Authentication, Firestore, Owner verification and secure PIN issuance.
- One-PIN/one-device binding, server-side user blocking and Owner-only publishing.
- Actual sensitivity values, custom profile CRUD, favorites and cloud sharing.
- Real sensor and performance diagnostics (FPS, CPU/GPU, temperature, RAM, battery).
- Touch tester, HUD helper, Gloo Wall helper and headshot practice module.
- REG/config import, local/cloud backup and restore.
- Network diagnostics, storage analyzer, announcements and update delivery.
- Sinhala translations and final release branding/assets.

## Build
1. Open this folder in a recent stable Android Studio.
2. Install Android SDK Platform 35 if Android Studio requests it.
3. Allow Gradle to sync, then run the `app` configuration on a device or emulator.

The project archive does not include a Gradle wrapper. Android Studio can open the project and configure Gradle; alternatively generate a wrapper from an installed Gradle distribution.

## Notes
This is a development prototype, not a performance booster that can force unsupported FPS modes. It does not modify protected Free Fire data, inject files, automate game input, or provide aimbot/aimlock/macros. Game launching depends on the official game package installed on the device. Package IDs can vary by regional distribution; this build includes the common global package IDs.
