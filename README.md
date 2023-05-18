# OpenWeatherApiCollabera Coding Exam

This repository contains the code for the Collabera-G-Xchange Coding Challenge, specifically focused on OpenWeatherAPI integration.

## Technologies Used

- MVVM
- Room Persistence
- Clean Architecture
- Retrofit
- Firebase Auth

## Initialization

Follow the steps below to set up and initialize the project:

### A. Clone/Download the repository

Clone or download the repository to your local machine.

### B. Firebase Auth Setup

1. Open your browser and go to https://firebase.google.com/.
2. Click "Go to console" or visit https://console.firebase.google.com/.
3. Add/Create a new project.
4. Provide a name for your project.
5. Optionally, untick the analytics checkbox and continue.
6. In the left-hand navigation drawer, select "Build", then choose "Authentication".
7. Select "Setup Sign-in Method".
8. Enable the "Email/Password" sign-in method and save the changes.

### C. Android Studio Configuration

1. Open Android Studio.
2. In the top menu bar, click "Tools".
3. Select "Firebase", then choose "Authentication".
4. Select "Authenticate using a custom authentication system".
5. Choose the project you created earlier.
6. Wait for the integration/connection process to complete.
7. All the necessary configurations are now done.

### D. OpenWeatherAPI Setup

1. Register and obtain an API key from https://openweathermap.org/current.
2. Complete the registration process and click your username dropdown, then select "My API keys".
3. Copy the API Key (Note: It may take an hour or two for the key to become active).
4. Check if there is a `local.properties` file in your project directory.
5. If the file does not exist, create a new file named `local.properties`.
6. Add the following line to the `local.properties` file: `API_KEY=yourApiKey`, replacing `yourApiKey` with your actual API key.
7. In Android Studio, click "Build" in the top menu bar, then choose "Clean Project".
8. After the project is cleaned, click "Build" again, then select "Rebuild Project".

The project is now ready to be run and tested. You can explore the code and make any necessary modifications or enhancements based on your requirements.
