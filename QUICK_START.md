# Quick Start Guide

## 🚀 Run the App in 30 Seconds

### iOS (macOS only)

```bash
# Open in Xcode
open iosApp/iosApp.xcodeproj

# Then click Run (⌘R) in Xcode
# The Kotlin framework builds automatically!
```

### Android

```bash
# Run on connected device/emulator
./gradlew :composeApp:installDebug

# Or open in Android Studio and click Run
```

### Desktop

```bash
# Run directly
./gradlew :composeApp:run

# Or create an installer
./gradlew :composeApp:packageDistributionForCurrentOS
```

## ✏️ Make Your First Change

1. **Open the main UI file**:
   ```
   composeApp/src/commonMain/kotlin/sbddesign/lnwallet/project/App.kt
   ```

2. **Change the button text** on line ~34:
   ```kotlin
   Button(onClick = { showContent = !showContent }) {
       Text("Bitcoin Rocks!")  // ← Change this!
   }
   ```

   To:
   ```kotlin
   Button(onClick = { showContent = !showContent }) {
       Text("Hello Multiplatform!")  // ← Your new text
   }
   ```

3. **Run any platform** (iOS/Android/Desktop) and see your change!

## 🎨 Where to Edit

### UI Changes (applies to all platforms)

- **Main screen**: `composeApp/src/commonMain/kotlin/.../App.kt`
- **Add new screens**: Create new `@Composable` functions in `commonMain`
- **Business logic**: Add Kotlin classes in `commonMain`

### Platform-Specific (rarely needed)

- **iOS only**: `composeApp/src/iosMain/kotlin/`
- **Android only**: `composeApp/src/androidMain/kotlin/`
- **Desktop only**: `composeApp/src/jvmMain/kotlin/`

## 📱 Current App Features

The demo app shows:

- ✅ Material3 theming
- ✅ Button interactions
- ✅ Animated visibility
- ✅ Image loading (from resources)
- ✅ Platform detection
- ✅ Safe area handling

## 🎯 Next Steps

### 1. Add Navigation

```kotlin
// Install: implementation("org.jetbrains.androidx.navigation:navigation-compose")

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen() }
        composable("details") { DetailsScreen() }
    }
}
```

### 2. Add ViewModel

```kotlin
class MyViewModel : ViewModel() {
    private val _state = MutableStateFlow(MyState())
    val state: StateFlow<MyState> = _state.asStateFlow()
    
    fun doSomething() {
        // Your logic here, works on all platforms!
    }
}
```

### 3. Add Networking

```kotlin
// Install: implementation("io.ktor:ktor-client-core")

val client = HttpClient()
val response = client.get("https://api.example.com/data")
```

### 4. Add Database

```kotlin
// Install: SQLDelight or Room (with KMP support)

// Works on iOS, Android, Desktop!
```

## 🔍 Project Structure Quick Reference

```
lnwallet/
├── composeApp/src/
│   ├── commonMain/          ← 95% of your code goes here
│   │   └── kotlin/
│   │       └── ...project/
│   │           ├── App.kt           ← Main UI ⭐
│   │           ├── Greeting.kt      ← Example logic
│   │           └── Platform.kt      ← Platform interface
│   ├── iosMain/             ← iOS-specific (rarely used)
│   ├── androidMain/         ← Android-specific (rarely used)
│   └── jvmMain/             ← Desktop-specific (rarely used)
│
└── iosApp/                  ← Don't edit unless customizing wrapper
```

## 💡 Pro Tips

1. **Hot Reload is enabled**: Changes in `App.kt` can reload without full rebuild (in supported environments)

2. **Preview in Android Studio**: Add `@Preview` to any `@Composable` function to see it in the IDE

3. **Debugging**:
    - iOS: Debug Kotlin code from Xcode
    - Android: Debug from Android Studio
    - Desktop: Debug from IntelliJ IDEA

4. **Shared Resources**: Put images, strings, etc. in:
   ```
   composeApp/src/commonMain/composeResources/
   ```
   Access with:
   ```kotlin
   Image(painterResource(Res.drawable.my_image), null)
   Text(stringResource(Res.string.my_string))
   ```

## 🐛 Common Issues

**Issue**: Xcode build fails with "ComposeApp module not found"

```bash
# Solution: Clean and rebuild
cd iosApp
xcodebuild clean
# Then rebuild in Xcode
```

**Issue**: Android build fails

```bash
# Solution: Clean and rebuild
./gradlew clean
./gradlew :composeApp:assembleDebug
```

**Issue**: Desktop app doesn't start

```bash
# Solution: Ensure Java 17+ is installed
java -version
# Should show version 17 or higher
```

## 📚 Learn More

- **Full Documentation**: [README.md](./README.md)
- **Architecture**: [ARCHITECTURE.md](./ARCHITECTURE.md)
- **Setup Details**: [SETUP_SUMMARY.md](./SETUP_SUMMARY.md)
- **iOS Guide**: [iosApp/README.md](./iosApp/README.md)

## 🎉 You're Ready!

Your Kotlin Multiplatform project is fully set up. Start building your app once, and deploy everywhere!

**Remember**:

- Edit `App.kt` in `commonMain` for UI changes
- Changes apply to iOS, Android, and Desktop automatically
- You're writing ~95% shared code!

Happy coding! 🚀
