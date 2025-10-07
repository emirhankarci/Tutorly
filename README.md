# Tutorly - AI-Powered Learning Platform

A comprehensive Android application designed to enhance students' learning experience through AI-powered features, personalized study plans, and interactive learning tools.

## 📱 About The Project

Tutorly is a modern educational platform that leverages artificial intelligence to provide personalized learning experiences for students. The application combines traditional study methods with cutting-edge AI technology to help students learn more effectively and efficiently.

### Key Highlights

- **AI-Powered Learning**: Interactive AI chat for personalized study assistance
- **Smart Scheduling**: Automated lesson planning and schedule management
- **Multi-Subject Support**: Comprehensive coverage for grades 9-12
- **English Learning**: Dedicated tools for English language learning
- **Visual Learning**: Study with image analysis and AI-powered explanations
- **Assessment Tools**: Interactive quizzes with instant feedback
- **Progress Tracking**: Monitor your learning journey with detailed analytics

## ✨ Features

### 🤖 AI Assistant
- **Subject-Specific Chat**: Get instant help on Mathematics, Physics, Chemistry, Biology, and more
- **Personalized Explanations**: AI adapts to your learning level and style
- **24/7 Availability**: Study assistance whenever you need it

### 📚 Study Tools
- **AI-Generated Summaries**: Quick chapter reviews and key concept extraction
- **Interactive Quizzes**: Multiple choice and open-ended questions with AI evaluation
- **Image Analysis**: Upload study materials and get AI-powered explanations
- **Topic Deep-Dive**: Comprehensive exploration of specific subjects and chapters

### 📅 Schedule Management
- **Smart Lesson Planner**: AI-generated study schedules based on your profile
- **Custom Timetables**: Create and manage your own lesson schedules
- **Progress Tracking**: Monitor completed lessons and study time
- **Notifications**: Stay on track with timely reminders

### 🌍 English Learning
- **Conversation Practice**: AI-powered English conversation partner
- **Grammar Assistance**: Real-time grammar checking and suggestions
- **Vocabulary Building**: Contextual word learning and usage examples
- **Level-Appropriate Content**: Content adapted to your English proficiency

### 👤 Personalization
- **User Profiles**: Custom learning profiles based on grade level and goals
- **Target Exam Preparation**: Specialized content for university entrance exams
- **Subject Preferences**: Focus on your strong and weak subjects
- **Study Hour Planning**: Daily study time recommendations

### 🔐 Authentication & Security
- **Google Sign-In**: Secure authentication with Google accounts
- **Firebase Backend**: Reliable cloud-based data storage
- **User Data Protection**: Encrypted local storage for sensitive information
- **Secure API Communication**: Protected endpoints with authentication tokens

### 💳 Subscription & Monetization
- **Adapty Integration**: Seamless in-app purchases and subscriptions
- **Flexible Plans**: Multiple subscription tiers to fit different needs
- **Free Trial**: Try premium features before committing
- **Purchase Verification**: Secure transaction validation

## 🛠️ Tech Stack

### Core Technologies
- **Language**: Kotlin
- **Minimum SDK**: API 27 (Android 8.1)
- **Target SDK**: API 36
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: Hilt

### Key Libraries & Frameworks

#### Android Jetpack
- **Lifecycle**: ViewModel and LiveData for lifecycle-aware components
- **Navigation**: Jetpack Compose Navigation for screen management
- **Activity**: Compose-integrated activities
- **Material3**: Modern Material Design components
- **Adaptive**: Responsive layouts for different screen sizes

#### Networking & Data
- **Retrofit**: RESTful API communication
- **OkHttp**: HTTP client with interceptors
- **Gson**: JSON serialization and deserialization
- **Kotlin Serialization**: Type-safe serialization

#### Firebase Services
- **Firebase Auth**: User authentication
- **Cloud Firestore**: Real-time database
- **Firebase Functions**: Serverless backend operations
- **Firebase Analytics**: User behavior tracking

#### Authentication
- **Google Sign-In**: OAuth 2.0 authentication
- **Firebase Authentication**: Secure user management

#### UI & UX
- **Coil**: Image loading and caching
- **Lottie**: Animation support
- **Compose Material3**: Modern UI components
- **Custom Themes**: Light mode optimized interface

#### Security
- **EncryptedSharedPreferences**: Secure local data storage
- **Security Crypto**: Android security library

#### Subscription Management
- **Adapty SDK**: In-app subscription handling
- **Adapty UI**: Pre-built paywall interfaces

#### Development Tools
- **Hilt**: Compile-time dependency injection
- **KAPT**: Kotlin Annotation Processing

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **Android Studio**: Arctic Fox or later
- **JDK**: Version 11 or higher
- **Android SDK**: API 27 or higher
- **Git**: For version control
- **Firebase Account**: For backend services
- **Adapty Account**: For subscription management

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/emirhankarci/Tutorly.git
cd Tutorly
```

### 2. Configure API Keys

#### Create `local.properties`

Create a `local.properties` file in the root directory (if not exists):

```properties
sdk.dir=/path/to/your/Android/Sdk
```

**Note**: This file is automatically generated by Android Studio and should not be committed to version control.

#### Configure Firebase

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a new project or use an existing one
3. Add an Android app with package name: `com.emirhankarci.tutorly`
4. Download `google-services.json`
5. Place it in the `app/` directory

#### Configure Adapty

1. Visit [Adapty Dashboard](https://app.adapty.io/)
2. Create a new app or use an existing one
3. Get your Public API Key
4. Update the key in `app/src/main/java/com/emirhankarci/tutorly/TutorlyApplication.kt`

#### Configure Keystore (For Release Builds)

Create `keystore.properties` in the root directory:

```properties
storePassword=your_store_password
keyPassword=your_key_password
keyAlias=your_key_alias
storeFile=path/to/your/keystore.jks
```

**Important**: Never commit this file to version control!

### 3. Sync and Build

1. Open the project in Android Studio
2. Wait for Gradle sync to complete
3. Build the project:
   ```bash
   ./gradlew clean build
   ```

### 4. Run the Application

1. Connect an Android device or start an emulator
2. Select your target device in Android Studio
3. Click the "Run" button or press `Shift + F10`

## 📁 Project Structure

```
Tutorly/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/emirhankarci/tutorly/
│   │   │   │   ├── data/              # Data layer
│   │   │   │   │   ├── api/           # API interfaces
│   │   │   │   │   ├── manager/       # Data managers
│   │   │   │   │   ├── model/         # Data models
│   │   │   │   │   ├── network/       # Network configuration
│   │   │   │   │   └── repository/    # Repository implementations
│   │   │   │   ├── di/                # Dependency injection modules
│   │   │   │   ├── domain/            # Domain layer
│   │   │   │   │   ├── entity/        # Domain entities
│   │   │   │   │   ├── repository/    # Repository interfaces
│   │   │   │   │   └── usecase/       # Business logic use cases
│   │   │   │   ├── presentation/      # Presentation layer
│   │   │   │   │   ├── navigation/    # Navigation setup
│   │   │   │   │   ├── ui/            # UI components
│   │   │   │   │   │   ├── components/  # Reusable components
│   │   │   │   │   │   ├── screen/      # Screen composables
│   │   │   │   │   │   └── theme/       # App theming
│   │   │   │   │   └── viewmodel/     # ViewModels
│   │   │   │   └── TutorlyApplication.kt
│   │   │   ├── res/               # Resources
│   │   │   └── AndroidManifest.xml
│   │   └── test/                  # Unit tests
│   ├── build.gradle.kts           # App-level build configuration
│   └── proguard-rules.pro         # ProGuard rules
├── build.gradle.kts               # Project-level build configuration
├── settings.gradle.kts            # Gradle settings
├── firebase.json                  # Firebase configuration
├── firestore.rules                # Firestore security rules
└── README.md
```

## 🏗️ Architecture

This project follows the **MVVM (Model-View-ViewModel)** architecture pattern combined with **Clean Architecture** principles.

### Layer Overview

#### 1. Presentation Layer
- **UI (Jetpack Compose)**: Composable functions for UI rendering
- **ViewModel**: UI state management and business logic coordination
- **Navigation**: Screen routing and navigation handling

#### 2. Domain Layer
- **Entities**: Core business models
- **Use Cases**: Encapsulated business logic operations
- **Repository Interfaces**: Abstract data source contracts

#### 3. Data Layer
- **Repository Implementations**: Concrete data source implementations
- **API Services**: Network data sources (Retrofit)
- **Local Storage**: Encrypted SharedPreferences
- **Remote Database**: Cloud Firestore

### Design Patterns

- **Repository Pattern**: Abstract data sources
- **Dependency Injection**: Hilt for loose coupling
- **Observer Pattern**: StateFlow for reactive UI
- **Singleton Pattern**: Single instance managers
- **Factory Pattern**: ViewModel creation

### Data Flow

```
UI (Composable)
    ↓
ViewModel (StateFlow)
    ↓
Use Case (Business Logic)
    ↓
Repository (Data Abstraction)
    ↓
Data Source (API/Firestore/Local)
```

## 🔑 Key Components

### Authentication Flow

1. **Splash Screen**: Initial app load and auth state check
2. **Onboarding**: First-time user introduction
3. **Google Sign-In**: OAuth authentication
4. **Profile Building**: User preference collection
5. **Subscription Paywall**: Premium feature unlock
6. **Main Application**: Full feature access

### Main Features Navigation

- **Home**: Dashboard with quick access to all features
- **AI Chat**: Subject-specific AI assistance
- **Schedule**: Lesson planning and management
- **English Learning**: Dedicated English practice
- **Study Tools**: Summaries, quizzes, and image analysis
- **Settings**: Profile management and app configuration

## 🔒 Security Considerations

### Firebase Security Rules

The project includes Firestore security rules to protect user data:

```javascript
// Users can only access their own data
match /users/{userId} {
  allow read, write: if request.auth != null && request.auth.uid == userId;
}

// User profiles are private
match /user_profiles/{userId} {
  allow read, write: if request.auth != null && request.auth.uid == userId;
}
```

**Important**: Deploy these rules to your Firebase project:

```bash
firebase deploy --only firestore:rules
```

### API Key Management

- **Never commit** API keys to version control
- Use `local.properties` for local development
- Use environment variables for CI/CD pipelines
- Rotate keys regularly

### Data Protection

- User tokens stored in `EncryptedSharedPreferences`
- HTTPS-only API communication
- Firebase Authentication for user verification
- Regular security audits

## 📦 Dependencies

### Core Dependencies

```kotlin
// Android Core
androidx.core:core-ktx:1.13.1
androidx.lifecycle:lifecycle-runtime-ktx:2.8.1

// Compose
androidx.compose:compose-bom:2024.05.00
androidx.activity:activity-compose:1.9.0
androidx.navigation:navigation-compose:2.7.7

// Firebase
com.google.firebase:firebase-bom:33.4.0
com.google.firebase:firebase-auth-ktx
com.google.firebase:firebase-firestore-ktx

// Networking
com.squareup.retrofit2:retrofit:2.9.0
com.squareup.okhttp3:okhttp:4.12.0

// Dependency Injection
com.google.dagger:hilt-android:2.51.1

// Subscription
io.adapty:android-sdk:3.11.1

// Security
androidx.security:security-crypto:1.1.0-alpha06
```

For a complete list, see `app/build.gradle.kts`.

## 🧪 Testing

### Run Unit Tests

```bash
./gradlew test
```

### Run Instrumented Tests

```bash
./gradlew connectedAndroidTest
```

### Test Coverage

```bash
./gradlew jacocoTestReport
```

## 🚢 Deployment

### Debug Build

```bash
./gradlew assembleDebug
```

### Release Build

```bash
./gradlew assembleRelease
```

### Generate Signed APK

1. Ensure `keystore.properties` is configured
2. Run:
   ```bash
   ./gradlew assembleRelease
   ```
3. APK location: `app/release/app-release.apk`

### Generate AAB (Android App Bundle)

```bash
./gradlew bundleRelease
```

AAB location: `app/release/app-release.aab`

## 🤝 Contributing

Contributions are welcome! Please follow these guidelines:

### How to Contribute

1. **Fork the Project**
   ```bash
   git clone https://github.com/your-username/Tutorly.git
   ```

2. **Create a Feature Branch**
   ```bash
   git checkout -b feature/AmazingFeature
   ```

3. **Commit Your Changes**
   ```bash
   git commit -m 'feat: Add some AmazingFeature'
   ```

4. **Push to the Branch**
   ```bash
   git push origin feature/AmazingFeature
   ```

5. **Open a Pull Request**

### Commit Message Convention

Follow the [Conventional Commits](https://www.conventionalcommits.org/) specification:

- `feat:` New feature
- `fix:` Bug fix
- `docs:` Documentation changes
- `style:` Code style changes (formatting, etc.)
- `refactor:` Code refactoring
- `test:` Adding or updating tests
- `chore:` Maintenance tasks

### Code Style

- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable and function names
- Write comments for complex logic
- Keep functions small and focused
- Write unit tests for new features

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **Emirhan Karcı** - *Project Lead & Developer* - [GitHub](https://github.com/emirhankarci)

## 🙏 Acknowledgments

- **Firebase** - Backend infrastructure
- **Adapty** - Subscription management
- **Google** - Authentication services
- **Android Jetpack** - Modern Android development
- **Kotlin Community** - Language and ecosystem support

## 📞 Contact & Support

### Report Issues

Found a bug? Have a feature request? Please open an issue:

[Create New Issue](https://github.com/emirhankarci/Tutorly/issues/new)

### Questions?

For questions and discussions, please use:

- **GitHub Discussions**: [Start a Discussion](https://github.com/emirhankarci/Tutorly/discussions)
- **Email**: emirhankarci2@gmail.com

## 🗺️ Roadmap

### Current Version (v1.0.1)

- ✅ Core AI chat functionality
- ✅ Schedule management
- ✅ English learning tools
- ✅ User authentication
- ✅ Subscription system

### Planned Features

- [ ] Offline mode support
- [ ] Dark theme
- [ ] Multi-language support
- [ ] Voice interaction with AI
- [ ] Collaborative study rooms
- [ ] Parent/teacher dashboard
- [ ] Achievement system
- [ ] Study streak tracking
- [ ] Flashcard creation
- [ ] Video lessons integration

## 📊 Project Status

**Current Version**: 1.0.1
**Status**: Active Development
**Last Updated**: October 2025

## 📈 Performance

- **App Size**: ~25 MB
- **Minimum Memory**: 2 GB RAM
- **Supported Devices**: Phones and tablets
- **Orientation**: Portrait (primary)
- **Internet**: Required for AI features

## 🔧 Troubleshooting

### Common Issues

#### Build Fails

**Problem**: Gradle sync fails
**Solution**:
```bash
./gradlew clean
./gradlew build --refresh-dependencies
```

#### Firebase Connection Issues

**Problem**: Google Sign-In not working
**Solution**: Verify `google-services.json` is in `app/` directory and package name matches

#### Adapty Integration Issues

**Problem**: Subscriptions not loading
**Solution**: Check Adapty public key in `TutorlyApplication.kt`

### Need More Help?

Check the [Wiki](https://github.com/emirhankarci/Tutorly/wiki) or [open an issue](https://github.com/emirhankarci/Tutorly/issues).

---

**Made with ❤️ for students by students**

**Star ⭐ this repository if you find it helpful!**
