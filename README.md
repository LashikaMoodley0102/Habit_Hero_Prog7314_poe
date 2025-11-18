# Habit_Hero_Prog7314_poe

*NOTE: Due to a recent error that a fellow team member made by submitting the wrong module work in the wrong github link, we had to upload the code in a different way as it was not supporting pushing it directly from android studio.* 

Overview
HabitHero is a simple and functional Android application that is designed to help users build positive habits and track their daily progress. The app allows users to register and log in securely, create, manage, and monitor personal habits, view progress reports, and stay motivated with reminders and notifications. This app is built using Kotlin and XML layouts, following basic Android development practices such as structured Activity navigation, use of Shared Preferences for local data storage, and proper separation of screens.

1. Features
1.1 Secure Login (Firebase Authentication)
•	Email and password login.
•	Firebase identity management fully implemented.
•	Automatic session persistence after login.
1.2 Biometric Authentication (Optional)
•	Fingerprint unlock supported on devices that allow it.
•	Can be turned on/off in Settings.
1.3 Settings Page
•	Change language (English or Afrikaans).
•	Toggle between Light Mode and Dark Mode.
•	Enable or disable notifications.
•	Settings saved with SharedPreferences.
•	App refreshes automatically after saving.
1.4 Multi-Language Support
•	English (default)
•	Afrikaans
1.5 Habit Tracking System
•	Add new habits.
•	Edit or delete existing habits.
•	View habit progress.
•	Habit data stored in Firebase.
1.6 Additional Features
•	Clean and simple UI design.
•	Responsive layouts.
•	Error handling for login, database, and settings.
•	Firebase Storage prepared for future expansions.

2. Architecture
The application follows a layered, MVVM-inspired structure:
 Java/Kotlin Classes
•	MainActivity
•	LoginActivity
•	RegisterActivity
•	HomescreenActivity
•	ProfileActivity
•	SettingsActivity
•	ReportActivity
Habit & Tracker Activities
•	NewHabitActivity
•	MoodTrackerActivity
•	SleepTrackerActivity
•	WaterTrackerActivity
•	WorkoutTrackerActivity
•	MeditationActivity
•	ReadingTrackerActivity
•	ToDoListActivity
•	TrackExpensesActivity
Entry Models
•	MeditationEntry
•	ReadingEntry
Custom Habit Activities
•	CustomHabitActivity
•	CustomGeneralHabitActivity
•	CustomHealthHabitActivity
•	CustomLifestyleHabitActivity
•	CustomFinancialHabitActivity
Category Activities & Goals
•	GeneralActivity
•	HealthActivity
•	LifestyleActivity
•	FinancialActivity
•	BiometricsActivity
Goal Models
•	GeneralGoal
•	HealthGoal
•	FinanceGoal
Data Models
•	Expense
•	Savings
•	LifestyleHabit

Layout XML Files
General Layouts
•	activity_main.xml
•	activity_login.xml
•	activity_register.xml
•	activity_profile.xml
•	activity_homescreen.xml
Settings and Report
•	activity_settings.xml
•	activity_report.xml
To-Do and Journal
•	activity_todolist.xml
•	activity_journal.xml
Habit Tracking
•	activity_new_habit.xml
•	activity_custom_habit.xml
•	activity_custom_general.xml
•	activity_custom_health.xml
•	activity_custom_lifestyle.xml
•	activity_custom_finance.xml
Category Screens
•	activity_general.xml
•	activity_health.xml
•	activity_lifestyle.xml
•	activity_financial.xml
Specific Habits
•	habit_mood.xml
•	habit_sleep.xml
•	habit_water.xml
•	habit_workout.xml
•	activity_meditation.xml
•	activity_reading.xml
•	activity_savings.xml
•	activity_expense.xml
•	activity_biometrics.xml

values/
•	colors.xml
•	font_certs.xml
•	preloaded_fonts.xml
•	strings.xml
•	themes.xml
values-af/
•	strings.xml (Afrikaans localization)
values-night/
•	(Night mode resources)
xml/
•	(Custom XML configurations)
Root File
•	AndroidManifest.xml

3. Database (Firebase Integrated)
The app uses Firebase services:
1.	Firebase Authentication
Handles user login, password validation, and session management.
2.	Firestore or Firebase Realtime Database
Stores habit details and user-related data.
Examples of stored data:
o	User ID
o	Habit title
o	Habit frequency
o	Timestamp and progress
3.	Firebase Storage
Prepared for potential document or image uploads.

4. Authentication
The app uses Firebase Authentication for:
•	User sign-in.
•	User account management.
•	Secure token handling.
An optional biometric layer adds:
•	Fingerprint login.
•	Faster secure access.

5. Multi-Language Support
Languages supported:
•	English
•	Afrikaans
All translations are stored in:
•	values/strings.xml (English)
•	values-af/strings.xml (Afrikaans)
Language is saved in SharedPreferences and applied through configuration updates.

6. Tools and Technologies Used
•	Kotlin
•	Android Studio
•	Firebase Authentication
•	Firebase Firestore or Realtime Database
•	Firebase Storage
•	SharedPreferences
•	ConstraintLayout and Android UI components
•	GitHub for version control
•	GitHub Actions (build and test pipeline placeholder)

7. GitHub Actions (CI/CD Pipeline)
A placeholder pipeline is included for:
•	Automatic building of the project.
•	Running tests.
•	Ensuring code compiles before merging updates.
This includes:
•	Checking out the repository.
•	Setting up the JDK environment.
•	Building the debug APK.
•	Executing basic tests.

8. Installation and Setup
Step 1: Clone the repository
Copy the link from GitHub and clone the project.
Step 2: Open the project in Android Studio
Select "Open" → Choose the project folder.
Step 3: Configure Firebase
Ensure that the file "google-services.json" is placed in:
app/src/main/
Step 4: Build and run
Connect a device or use an emulator, then click Run.

