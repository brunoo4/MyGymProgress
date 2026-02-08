# MyGym Progress 🏋️‍♂️

![Kotlin](https://img.shields.io/badge/Kotlin-1.9.20-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Compose Multiplatform](https://img.shields.io/badge/Compose_Multiplatform-UI-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Platform](https://img.shields.io/badge/Platform-Android_%7C_iOS-important?style=for-the-badge)

**MyGym Progress** is a workout tracker built entirely with **Kotlin Multiplatform (KMP)**.

The primary goal of this project is to explore the capabilities of KMP for sharing business logic, data persistence, and UI (via Compose Multiplatform) between Android and iOS, following a **"Local-First"** approach that will eventually evolve into a Cloud Sync architecture.

---

## 📸 Screenshots

*(Place your screenshots here later. Suggested: Home Screen | Active Session | Dark Mode Details)*

---

## 🛠 Tech Stack & Libraries

This project uses a modern, scalable stack designed for speed and stability:

* **Core:** [Kotlin Multiplatform](https://kotlinlang.org/lp/multiplatform/)
* **UI:** [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform) (Material 3)
* **Navigation:** [Voyager](https://voyager.adriel.cafe/) (Cross-platform navigation)
* **Dependency Injection:** [Koin](https://insert-koin.io/)
* **Database:** [Room KMP](https://developer.android.com/kotlin/multiplatform/room) (SQLite abstraction)
* **Concurrency:** Kotlin Coroutines & Flow
* **Architecture:** Clean Architecture + MVI/MVVM

---

## 🏗 Architecture

The project follows **Clean Architecture** principles to ensure separation of concerns and testability.

### Module Structure
* **:composeApp** - Contains the UI (Compose) and platform-specific entry points (Android Activity / iOS ViewController).
* **:shared** - The brain of the app. Contains:
    * **Domain:** Entities, Use Cases, Repository Interfaces (Pure Kotlin).
    * **Data:** API implementation, Room Database, Repository Implementations.
    * **Presentation:** ViewModels/ScreenModels (State management).

### Design Pattern
The app uses a Unidirectional Data Flow (**UDF**) approach:
1.  **UI** emits events (Intents).
2.  **ViewModel** processes logic/use cases.
3.  **State** is updated and observed by the UI.

---

## 🚀 Features & Roadmap

This project is being developed in two main phases to simulate a real-world product evolution.

### Phase 1: Local-First (Current Status)
- [ ] **Workout Session:** Real-time tracker with stopwatch.
- [ ] **Exercise Management:** Custom database of exercises (Muscle groups, names).
- [ ] **History:** Local persistence using Room KMP.
- [ ] **Theming:** "Cyberpunk Clean" Dark Mode UI.

### Phase 2: Cloud & Sync (Planned)
- [ ] **Backend:** Ktor Server implementation.
- [ ] **Auth:** JWT Authentication.
- [ ] **Sync Engine:** Background synchronization of local data to the cloud.
- [ ] **Public Leaderboards:** Global stats API.

---

## 🔧 Getting Started

### Prerequisites
* JDK 17+
* Android Studio (latest Hedgehog or Iguana recommended)
* Xcode (for iOS build)
* KDoctor (optional, to verify environment)

### Running on Android
Open the project in Android Studio and run the `composeApp` configuration.

### Running on iOS
1.  Open `iosApp/iosApp.xcworkspace` in Xcode.
2.  Select a simulator and run.
    * *Note: Ensure you have the Kotlin Multiplatform Mobile plugin installed.*

---

## 👨‍💻 Author

**Bruno**
Senior Android Developer | Software Engineer

Built with ❤️ and Kotlin.
