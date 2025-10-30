# Hello World Kotlin Multiplatform App

This repository scaffolds a minimal "Hello World" application built with Kotlin Multiplatform. A single Compose-based screen is shared across Android and iOS so both platforms display the same message.

## Project Layout
- `androidApp/` – Android application module that hosts the shared UI.
- `shared/` – Kotlin Multiplatform module with the shared Compose screen and iOS framework packaging.
- `iosApp/` – Xcode project wrapping the shared module for iOS simulator/device previews.
- `gradle/`, `gradlew*` – Gradle wrapper for consistent builds.

## Prerequisites
- JDK 17+
- Android Studio (Hedgehog or newer) with the Android SDK installed.
- Xcode 15+ with command line tools.
- Kotlin Multiplatform plugin support is already configured through Gradle.

## Android: Build & Preview
1. Ensure the Android SDK path is available by creating `local.properties` in the project root (if Android Studio has not generated it):
   ```
   sdk.dir=/path/to/Android/sdk
   ```
2. Assemble or install the debug build with Gradle:
   - Build only: `./gradlew :androidApp:assembleDebug`
   - Install to a running emulator/device: `./gradlew :androidApp:installDebug`
3. Alternatively, open the project in Android Studio, select the `androidApp` configuration, and press **Run** to launch the "Hello World" screen on an emulator.

## iOS: Build & Preview
1. From the project root, run the Gradle sync task once so Xcode has a framework to link against:
   ```
   ./gradlew :shared:embedAndSignAppleFrameworkForXcode
   ```
2. Open `iosApp/iosApp.xcodeproj` in Xcode.
3. Pick the `iosApp` scheme and an iOS Simulator (for example, iPhone 15).
4. Press **Run**. Xcode will invoke the Gradle build phase that keeps the shared Kotlin framework up to date, then launch the simulator showing the shared "Hello World" screen.

## Useful Gradle Tasks
- `./gradlew clean` – Remove all build outputs.
- `./gradlew :shared:assembleDebug` – Build the shared framework and common code.
- `./gradlew :shared:check` – Run common tests (none included by default, but the task is wired up).

## Notes
- If you add platform-specific assets or resources, keep them inside the respective `androidMain`/`iosApp` directories.
- Kotlin/Native targets for iOS are configured but can only be built on macOS with the appropriate toolchains. On other hosts, Gradle will skip those targets automatically.
- Update `iosApp/Configuration/Config.xcconfig` with your Apple developer `TEAM_ID` if you plan to deploy to physical devices.