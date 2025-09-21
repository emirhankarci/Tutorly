# Gemini API Integration for Android

This project integrates with the FastAPI Gemini service to provide AI text generation capabilities in an Android Jetpack Compose application.

## Architecture

The implementation follows MVVM architecture with the following components:

### Data Layer
- **Models** (`data/model/`): Data classes for API requests and responses
- **API Service** (`data/api/GeminiApiService`): Retrofit interface for API calls
- **Repository** (`data/repository/GeminiRepository`): Handles API communication
- **Network Config** (`data/network/NetworkConfig`): Retrofit configuration

### UI Layer
- **ViewModel** (`ui/viewmodel/GeminiViewModel`): Manages UI state and business logic
- **Screen** (`ui/screen/GeminiScreen`): Main Compose UI

## Setup Instructions

1. **Start your FastAPI server** with the Python code you provided
2. **Update the base URL** in `NetworkConfig.kt` if needed:
   - For Android emulator: `http://10.0.2.2:8000/` (default)
   - For physical device: Replace with your computer's IP address
3. **Build and run** the Android app

## Features

- **Connection Status**: Shows if the app is connected to the FastAPI server
- **Text Generation**: Send prompts to Gemini AI and receive generated text
- **Configurable Parameters**: Adjust max tokens and temperature
- **Error Handling**: Displays connection and generation errors
- **Model Information**: Shows current model being used

## Usage

1. Launch the app
2. Check connection status (should show "Connected" if your FastAPI server is running)
3. Enter your prompt in the text field
4. Optionally adjust max tokens and temperature
5. Tap "Generate Text" to get AI-generated response
6. View the generated text in the results section

## API Endpoints Used

- `GET /health` - Health check
- `POST /generate` - Text generation
- `GET /models` - Available models
- `GET /` - Root endpoint info

## Dependencies Added

```kotlin
// Networking
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("com.squareup.okhttp3:okhttp:4.12.0")
implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

// ViewModel
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
implementation("androidx.compose.runtime:runtime-livedata:1.5.4")
```

## Network Configuration

The app uses `10.0.2.2:8000` as the base URL, which maps to `localhost:8000` on the host machine when using the Android emulator.

For physical devices, update the BASE_URL in `NetworkConfig.kt` to your computer's IP address:
```kotlin
private const val BASE_URL = "http://YOUR_COMPUTER_IP:8000/"
```