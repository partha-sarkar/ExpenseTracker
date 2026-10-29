# Expense Tracker Android App

A modern Android application for tracking daily expenses built with Kotlin, Room Database, and Material Design 3.

## Features

- 📝 **Add Expenses** - Record expenses with name, amount, category, and date
- 📋 **View Expenses** - See all expenses in a clean, organized list
- 💰 **Total Tracking** - Automatic calculation of total expenses
- 🗂️ **Categories** - Organize expenses by category (Food, Transport, Shopping, Entertainment, Health, Other)
- ✏️ **Edit/Delete** - Tap to edit, long-press to delete expenses
- 🗑️ **Clear All** - Option to clear all expenses at once
- 💾 **Persistent Storage** - Data saved locally using Room Database
- 🎨 **Material Design 3** - Modern, beautiful UI with dynamic theming

## Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room (SQLite)
- **UI**: Material Design 3 Components, RecyclerView, ViewBinding
- **Async**: Kotlin Coroutines
- **Lifecycle**: ViewModel, LiveData

## Project Structure

```
ExpenseTracker/
├── app/
│   ├── src/main/
│   │   ├── java/com/expensetracker/
│   │   │   ├── Expense.kt              # Data model
│   │   │   ├── ExpenseDao.kt           # Database access
│   │   │   ├── AppDatabase.kt          # Room database
│   │   │   ├── ExpenseRepository.kt    # Data repository
│   │   │   ├── ExpenseViewModel.kt     # ViewModel
│   │   │   ├── ExpenseAdapter.kt       # RecyclerView adapter
│   │   │   ├── AddExpenseDialogFragment.kt # Add/Edit dialog
│   │   │   └── MainActivity.kt         # Main activity
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   ├── activity_main.xml   # Main screen
│   │   │   │   └── item_expense.xml    # Expense list item
│   │   │   ├── drawable/               # Vector icons
│   │   │   ├── values/                 # Strings, colors, themes
│   │   │   ├── menu/                   # Toolbar menu
│   │   │   └── xml/                    # Backup rules
│   │   └── AndroidManifest.xml
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
└── gradle/wrapper/
```

## Building the Project

### Prerequisites
- JDK 17 or higher
- Android SDK (API 34)
- Gradle 8.5+

### Using Command Line

```bash
# Make gradlew executable (Linux/macOS)
chmod +x gradlew

# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Run tests
./gradlew test

# Install on connected device
./gradlew installDebug
```

### Using Android Studio
1. Open Android Studio
2. Select "Open an existing project"
3. Navigate to the `ExpenseTracker` folder
4. Wait for Gradle sync to complete
5. Click Run (▶️) to build and install on device/emulator

### Using GitHub Codespaces / GitPod / Cloud IDE
1. Import this repository
2. The environment will automatically set up Android SDK
3. Run `./gradlew assembleDebug` to build

## APK Output Location

After building, find the APK at:
```
app/build/outputs/apk/debug/app-debug.apk
```

## Screenshots

The app includes:
- Main screen with total expenses card and expense list
- Floating action button to add new expenses
- Category selection with icons
- Edit/Delete functionality
- Empty state when no expenses exist

## License

MIT License - Feel free to use and modify for your own projects.# ExpenseTracker
# ExpenseTracker
