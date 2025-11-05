# LnWallet - Kotlin Multiplatform Project

This is a Kotlin Multiplatform project with **shared UI and business logic** targeting Android, iOS, and Desktop (JVM).

## ✨ Key Features

- **Single Codebase**: All UI and business logic is written once in Kotlin and Compose Multiplatform
- **Native Performance**: Compiles to native code on each platform
- **Consistent Experience**: The same app runs identically on iOS, Android, and Desktop

## 📁 Project Structure

### `/composeApp` - Shared Code (Edit Here!)

This is where **all your UI and business logic** lives:

- **[commonMain](./composeApp/src/commonMain/kotlin)** - Code shared across ALL platforms
    - `App.kt` - Main UI written in Compose Multiplatform
    - `Greeting.kt` - Example business logic
    - This is where you'll spend most of your time developing!

- **[iosMain](./composeApp/src/iosMain/kotlin)** - iOS-specific code (rarely needed)
- **[androidMain](./composeApp/src/androidMain/kotlin)** - Android-specific code (rarely needed)
- **[jvmMain](./composeApp/src/jvmMain/kotlin)** - Desktop-specific code (rarely needed)

### `/iosApp` - iOS App Wrapper

Contains the minimal iOS app that displays the shared Compose UI. You rarely need to edit this.
See [iosApp/README.md](./iosApp/README.md) for more details.

## 🚀 Getting Started

### Prerequisites

- **For Android/Desktop**: Java 17+ (check with `java -version`)
- **For iOS**: macOS with Xcode installed

### Build and Run

#### Android Application

From the terminal:

```shell
./gradlew :composeApp:assembleDebug
```

Or use the Android run configuration in your IDE (Android Studio/IntelliJ IDEA).

#### Desktop (JVM) Application

From the terminal:

```shell
./gradlew :composeApp:run
```

Or use the Desktop run configuration in your IDE.

#### iOS Application

Open `/iosApp/iosApp.xcodeproj` in Xcode and click Run, or use the iOS run configuration in your IDE.

The iOS app automatically builds the Kotlin framework during the Xcode build process.

## 🎨 Making Changes

### Modify the UI

Edit `composeApp/src/commonMain/kotlin/sbddesign/lnwallet/project/App.kt` - changes apply to **all platforms**
automatically!

### Add Business Logic

Add new Kotlin files to `composeApp/src/commonMain/kotlin/` - accessible from **all platforms**.

### Platform-Specific Code

Only when needed, add platform-specific code to:

- `iosMain/` for iOS-only features
- `androidMain/` for Android-only features
- `jvmMain/` for Desktop-only features

## 🏗️ Architecture

```
┌─────────────────────────────────────┐
│   Compose Multiplatform UI          │
│   (commonMain/App.kt)               │
│   Written once, runs everywhere!    │
└─────────────────────────────────────┘
            ↓         ↓         ↓
    ┌───────────┐ ┌──────┐ ┌─────────┐
    │    iOS    │ │ Android│ │ Desktop │
    │  Native   │ │ Native │ │   JVM   │
    └───────────┘ └────────┘ └─────────┘
```

## 📚 Learn More

- [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [iOS Integration Guide](./iosApp/README.md)