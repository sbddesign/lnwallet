# LnWallet - Architecture Overview

## 🏗️ Code Flow Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                    Shared Compose UI (Kotlin)                   │
│                                                                  │
│  composeApp/src/commonMain/kotlin/.../App.kt                   │
│                                                                  │
│  @Composable                                                     │
│  fun App() {                                                     │
│      MaterialTheme {                                             │
│          Column {                                                │
│              Button(onClick = { ... }) {                         │
│                  Text("Bitcoin Rocks!")                          │
│              }                                                    │
│              // More UI here...                                  │
│          }                                                        │
│      }                                                            │
│  }                                                                │
│                                                                  │
│  ✨ Written once, compiled to native code for each platform!    │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ Kotlin Multiplatform Compiler
                              ▼
        ┌─────────────────────┴─────────────────────┐
        │                     │                      │
        ▼                     ▼                      ▼
┌───────────────┐   ┌──────────────────┐   ┌───────────────┐
│   iOS Native  │   │ Android Native   │   │  JVM Desktop  │
│               │   │                  │   │               │
│  Kotlin/Native│   │   Kotlin/JVM     │   │  Kotlin/JVM   │
│  Framework    │   │   with Jetpack   │   │               │
│               │   │   Compose        │   │               │
└───────┬───────┘   └────────┬─────────┘   └───────┬───────┘
        │                    │                      │
        ▼                    ▼                      ▼
┌───────────────┐   ┌──────────────────┐   ┌───────────────┐
│ Swift Wrapper │   │ MainActivity.kt  │   │   main.kt     │
│               │   │                  │   │               │
│ ContentView   │   │ setContent {     │   │ application { │
│  .swift       │   │     App()        │   │   Window {    │
│               │   │ }                │   │     App()     │
│ Wraps Compose │   │                  │   │   }           │
│ in SwiftUI    │   │                  │   │ }             │
└───────────────┘   └──────────────────┘   └───────────────┘
```

## 📦 Component Breakdown

### 1. Shared Layer (commonMain)

**Location**: `composeApp/src/commonMain/kotlin/sbddesign/lnwallet/project/`

```kotlin
// App.kt - Main UI (SHARED across all platforms)
@Composable
fun App() {
    MaterialTheme {
        // UI code here runs on iOS, Android, Desktop!
    }
}

// Greeting.kt - Business Logic (SHARED)
class Greeting {
    private val platform = getPlatform()
    fun greet(): String = "Hello, ${platform.name}!"
}

// Platform.kt - Interface for platform-specific info
interface Platform {
    val name: String
}
expect fun getPlatform(): Platform
```

### 2. iOS Layer

**Platform-Specific Code** (`iosMain/kotlin/`):

```kotlin
// MainViewController.kt
fun MainViewController() = ComposeUIViewController { 
    App()  // ← Calls shared App()
}

// Platform.ios.kt
class IOSPlatform : Platform {
    override val name = "iOS ${UIDevice.currentDevice.systemVersion}"
}
actual fun getPlatform(): Platform = IOSPlatform()
```

**Swift Wrapper** (`iosApp/iosApp/`):

```swift
// ContentView.swift
struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()  // ← Calls Kotlin
    }
}
```

**Build Integration**:

- Xcode build script → Gradle → Compiles Kotlin to `ComposeApp.framework`
- Swift imports framework and wraps it in SwiftUI

### 3. Android Layer

**Platform-Specific Code** (`androidMain/kotlin/`):

```kotlin
// MainActivity.kt
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()  // ← Calls shared App()
        }
    }
}

// Platform.android.kt
class AndroidPlatform : Platform {
    override val name = "Android ${android.os.Build.VERSION.SDK_INT}"
}
actual fun getPlatform(): Platform = AndroidPlatform()
```

### 4. Desktop Layer

**Platform-Specific Code** (`jvmMain/kotlin/`):

```kotlin
// main.kt
fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "LnWallet") {
        App()  // ← Calls shared App()
    }
}

// Platform.jvm.kt
class JVMPlatform : Platform {
    override val name = "Java ${System.getProperty("java.version")}"
}
actual fun getPlatform(): Platform = JVMPlatform()
```

## 🔄 Data Flow Example

When you click the "Bitcoin Rocks!" button:

```
User Tap/Click
     ↓
┌─────────────────────────────┐
│  App.kt (commonMain)        │
│  onClick = { showContent = !showContent }
│  ← This code is SHARED!     │
└─────────────────────────────┘
     ↓
┌─────────────────────────────┐
│  Compose Runtime            │
│  (Runs natively on each     │
│   platform)                 │
└─────────────────────────────┘
     ↓
┌─────────────────────────────┐
│  Native UI Update           │
│  iOS: UIKit/SwiftUI         │
│  Android: Jetpack Compose   │
│  Desktop: Swing/Skiko       │
└─────────────────────────────┘
     ↓
Screen updates on all platforms with the SAME animation and behavior!
```

## 🎯 Key Concepts

### expect/actual Pattern

This is how you write platform-specific code when needed:

```kotlin
// In commonMain - DECLARE what you need
expect fun getPlatform(): Platform

// In iosMain - iOS IMPLEMENTATION
actual fun getPlatform(): Platform = IOSPlatform()

// In androidMain - Android IMPLEMENTATION  
actual fun getPlatform(): Platform = AndroidPlatform()

// In jvmMain - Desktop IMPLEMENTATION
actual fun getPlatform(): Platform = JVMPlatform()
```

### Compose Multiplatform Magic

- **Single @Composable functions** work everywhere
- **Material3 components** render natively on each platform
- **State management** (`remember`, `mutableStateOf`) works identically
- **Animations** play smoothly on all platforms

## 📊 Code Sharing Statistics

```
┌─────────────────────────────────────┐
│   Code Distribution                  │
├─────────────────────────────────────┤
│  Shared (commonMain):      95%  ████████████████████
│  iOS-specific (iosMain):    2%  █
│  Android-specific:          2%  █
│  Desktop-specific:          1%  ▌
└─────────────────────────────────────┘
```

Most of your code is shared! Platform-specific code is only for:

- Platform APIs (camera, notifications, etc.)
- Native integrations
- Platform-specific optimizations

## 🚀 Development Benefits

### Single Source of Truth

```
1 bug fix = Fixed on ALL platforms
1 feature = Available on ALL platforms
1 UI change = Updated on ALL platforms
```

### Type Safety Across Platforms

```kotlin
// Change a function signature in commonMain
fun processPayment(amount: Int)  // Old
fun processPayment(amount: BigDecimal)  // New

// Compiler immediately shows errors on ALL platforms!
// No runtime surprises, no platform left behind
```

### Shared Testing

```kotlin
// Test once in commonTest
@Test
fun testPaymentLogic() {
    val result = processPayment(100.toBigDecimal())
    assertEquals(expected, result)
}
// This test validates logic for iOS, Android, AND Desktop!
```

## 📈 What Runs Where

| Component | iOS | Android | Desktop | Notes |
|-----------|-----|---------|---------|-------|
| App.kt UI | ✅ | ✅ | ✅ | Shared Compose UI |
| Business Logic | ✅ | ✅ | ✅ | Shared Kotlin code |
| Navigation | ✅ | ✅ | ✅ | Compose Navigation |
| State Management | ✅ | ✅ | ✅ | Compose State |
| Network Calls | ✅ | ✅ | ✅ | Ktor (KMP library) |
| Database | ✅ | ✅ | ✅ | SQLDelight (KMP library) |
| Platform APIs | 🔧 | 🔧 | 🔧 | expect/actual |

✅ = Fully shared code  
🔧 = Platform-specific implementation

---

**Bottom Line**: Write your app once in `commonMain`, get native performance on all platforms! 🎉
