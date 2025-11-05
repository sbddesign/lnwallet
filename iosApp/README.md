# iOS App - Kotlin Multiplatform Integration

This iOS app is integrated with the Kotlin Multiplatform project and shares all UI and business logic with the Android
and Desktop versions.

## Architecture

- **Shared UI**: All UI is written in Compose Multiplatform in
  `../composeApp/src/commonMain/kotlin/sbddesign/lnwallet/project/App.kt`
- **Shared Business Logic**: All business logic is in the common source set
- **iOS Entry Point**: The iOS app uses `MainViewController()` from the Kotlin framework to display the Compose UI

## How It Works

1. **Framework Build**: The Xcode project has a build phase that automatically compiles the Kotlin code into a framework
2. **Swift Integration**: The `ContentView.swift` wraps the Kotlin Compose UI using `UIViewControllerRepresentable`
3. **Seamless Updates**: Any changes to the shared Kotlin code are automatically reflected in the iOS app on next build

## Building

### Using Xcode

Simply open `iosApp.xcodeproj` in Xcode and build/run as normal. The Kotlin framework will be built automatically.

### Using Command Line

```bash
cd iosApp
xcodebuild -scheme iosApp -configuration Debug -sdk iphonesimulator
```

## Project Structure

```
iosApp/
├── iosApp/
│   ├── iosAppApp.swift       # App entry point
│   ├── ContentView.swift     # SwiftUI wrapper for Compose UI
│   └── Assets.xcassets/      # iOS-specific assets
└── iosApp.xcodeproj/         # Xcode project
```

## Making Changes

### To modify the UI or business logic:

Edit files in `../composeApp/src/commonMain/` - these changes apply to all platforms (iOS, Android, Desktop)

### To modify iOS-specific behavior:

Edit files in `../composeApp/src/iosMain/` - these changes only affect iOS

### To modify the iOS app wrapper:

Edit `ContentView.swift` or `iosAppApp.swift` in this directory

## Troubleshooting

**Issue**: "ComposeApp module not found"

- Solution: Clean and rebuild the project. The Kotlin framework will be built automatically.

**Issue**: Build script errors

- Solution: Make sure you have Java installed and can run `./gradlew` from the project root

**Issue**: Simulator doesn't start

- Solution: Make sure you have Xcode Command Line Tools installed: `xcode-select --install`
