# iOS Integration Setup Summary

## ✅ What Was Done

Your Kotlin Multiplatform project has been successfully integrated with the new iOS app! All UI and business logic is
now shared across iOS, Android, and Desktop platforms.

## 🎯 Architecture Overview

### Single Source of Truth

All your app's UI and business logic is written **once** in:

- `composeApp/src/commonMain/kotlin/sbddesign/lnwallet/project/App.kt`

This same code runs on:

- ✅ iOS (iPhone, iPad, Mac Catalyst)
- ✅ Android
- ✅ Desktop (macOS, Windows, Linux)

## 📝 Changes Made

### 1. iOS App Configuration

- **Updated**: `iosApp/iosApp/ContentView.swift` - Now displays the shared Compose UI
- **Updated**: `iosApp/iosApp.xcodeproj/project.pbxproj` - Added Kotlin framework integration
- **Added**: Build phase that automatically compiles Kotlin code to iOS framework

### 2. Build Integration

The Xcode project now has a "Build Kotlin Framework" script that runs before compilation:

- Automatically builds the shared Kotlin code into `ComposeApp.framework`
- Builds both Debug and Release configurations
- Supports both iOS device (arm64) and Simulator (arm64)

### 3. Swift-Kotlin Bridge

The iOS app uses `UIViewControllerRepresentable` to embed the Compose UI:

```swift
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }
    // ...
}
```

## 🚀 How to Use

### Run on iOS

1. Open `iosApp/iosApp.xcodeproj` in Xcode
2. Select a simulator or device
3. Click Run (⌘R)
4. The Kotlin framework builds automatically!

### Run on Android

```bash
./gradlew :composeApp:assembleDebug
```

### Run on Desktop

```bash
./gradlew :composeApp:run
```

## 🎨 Development Workflow

### Making UI Changes

1. Edit `composeApp/src/commonMain/kotlin/sbddesign/lnwallet/project/App.kt`
2. Build any platform (iOS/Android/Desktop)
3. Your changes appear on **all platforms** automatically! 🎉

### Example: Adding a New Screen

```kotlin
// In composeApp/src/commonMain/kotlin/sbddesign/lnwallet/project/App.kt

@Composable
fun App() {
    MaterialTheme {
        // Your new UI here - works on iOS, Android, Desktop!
        Column {
            Text("Hello from Kotlin Multiplatform!")
            Button(onClick = { /* ... */ }) {
                Text("Click me!")
            }
        }
    }
}
```

## 🔍 Project Structure

```
lnwallet/
├── composeApp/                    # ← Edit here for all platforms!
│   ├── src/
│   │   ├── commonMain/           # Shared code (iOS + Android + Desktop)
│   │   │   └── kotlin/
│   │   │       └── sbddesign/lnwallet/project/
│   │   │           ├── App.kt            # 🎨 Main UI (shared!)
│   │   │           ├── Greeting.kt       # Business logic (shared!)
│   │   │           └── Platform.kt       # Platform info interface
│   │   ├── iosMain/              # iOS-specific (rarely needed)
│   │   ├── androidMain/          # Android-specific (rarely needed)
│   │   └── jvmMain/              # Desktop-specific (rarely needed)
│   └── build.gradle.kts          # Build configuration
│
├── iosApp/                        # iOS app wrapper (rarely edited)
│   ├── iosApp/
│   │   ├── iosAppApp.swift       # iOS app entry point
│   │   └── ContentView.swift     # Compose UI wrapper
│   └── iosApp.xcodeproj/         # Xcode project
│
├── README.md                      # Main project documentation
└── build.gradle.kts              # Root build configuration
```

## 🎉 Benefits

### Before

- ❌ Write iOS UI in Swift/SwiftUI
- ❌ Write Android UI in Kotlin/Compose
- ❌ Write Desktop UI separately
- ❌ Duplicate business logic
- ❌ Fix bugs 3 times

### After

- ✅ Write UI **once** in Compose Multiplatform
- ✅ Write business logic **once** in Kotlin
- ✅ Fix bugs **once**
- ✅ Add features **once**
- ✅ Test logic **once**

## 🔧 Technical Details

### Framework Build Process

```
1. Xcode starts build
2. "Build Kotlin Framework" script runs
3. Gradle compiles Kotlin → Native iOS framework
4. Framework includes Compose runtime + your code
5. Xcode links framework into iOS app
6. App launches with shared UI!
```

### Supported Targets

- **iOS Device**: arm64 (iPhone, iPad)
- **iOS Simulator**: arm64 (Apple Silicon Macs)
- **Android**: All architectures
- **Desktop**: macOS, Windows, Linux (JVM)

## 📚 Next Steps

1. **Explore the Demo App**: Run it on all platforms and see the same UI!
2. **Modify App.kt**: Change the UI and see it update everywhere
3. **Add Features**: Everything you add to `commonMain` works on all platforms
4. **Learn More**: Check out the [README.md](./README.md) and [iosApp/README.md](./iosApp/README.md)

## 💡 Tips

- **Hot Reload**: For faster iOS development, consider using Compose Hot Reload (already configured!)
- **Platform-Specific Code**: Use `expect`/`actual` declarations when you need platform-specific features
- **Debugging**: You can debug Kotlin code from both Xcode (iOS) and Android Studio
- **Resources**: Images, strings, etc. can be shared using Compose Resources

## 🐛 Troubleshooting

**Build fails in Xcode?**

- Clean build folder: Product → Clean Build Folder (⇧⌘K)
- Check that Java is installed: `java -version`

**Framework not found?**

- The build script automatically builds it, but you can manually run:
  ```bash
  ./gradlew :composeApp:linkDebugFrameworkIosSimulatorArm64
  ```

**Changes not appearing on iOS?**

- Clean the Xcode build folder
- The framework is cached, cleaning forces a rebuild

---

**Congratulations! 🎊** Your app now has a true single codebase with shared UI and logic across all platforms!
