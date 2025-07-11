# Compose Multiplatform Application

```markdown
# Mobile Development Interview Answers

## 1. Optimizing Network Consumption for Large Address Lists

**Use pagination and lazy loading** - Load only 20-30 addresses at a time, show more when user scrolls down. Use pull-to-refresh with 2-3 second delay to avoid too many API calls.

## 2. Transaction State Management Strategy

**Use WebSocket or smart polling** - Connect to WebSocket for real-time updates, or check transaction status every 30 seconds initially, then reduce frequency for old transactions.

## 3. Alternative to Address Lists

**Use HD wallet with xpub key** - Instead of storing many addresses, use one master key to generate new addresses when needed. This is more secure and efficient.

## 4. Protecting Sensitive Bitcoin Address Information

**Generate addresses locally** - Don't store addresses on device or server. Generate them from seed phrase when required, so sensitive data never gets exposed or transmitted.
```

To build the application bundle:  
 - run `./gradlew :composeApp:assembleDebug`  
 - find `.apk` file in `composeApp/build/outputs/apk/debug/composeApp-debug.apk`  

Run android UI tests on the connected device: `./gradlew :composeApp:connectedDebugAndroidTest`

### Desktop
Run the desktop application: `./gradlew :composeApp:run`  
Run the desktop **hot reload** application: `./gradlew :composeApp:jvmRunHot`  
Run desktop UI tests: `./gradlew :composeApp:jvmTest`  

### iOS
To run the application on iPhone device/simulator:  
 - Open `iosApp/iosApp.xcproject` in Xcode and run standard configuration  
 - Or use [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile) for Android Studio  

Run iOS simulator UI tests: `./gradlew :composeApp:iosSimulatorArm64Test`  

