# OpenWeatherApiCollabera Coding Exam
Collabera-G-Xchange Coding Challenge

##Technologies Used
MVVM
Room Persistence
Clean Architecture
Retrofit
Firebase Auth

##Initialization
A. Clone/Download the repository
B. For Firebase Auth
In your browser
    1. Go to https://firebase.google.com/
    2. Click "Go to console" or https://console.firebase.google.com/
    3. Add/Create Project
    4. Type a name
    5. Optional untick the analytics then continue
    6. In the drawer select "Build"
    7. Then select "Authentication"
    8. Select Setup Sign Method
    9. Enable Email/Password then Save

C. In Android Studio
    1. At the top bar menu click "Tools"
    2. Select "Authentication" then select "Authenticate using a custom authentication system"
    3. Select the project you created earlier
    4. Wait for the integration/connection to be finish
    5. All done

D. For OpenWeatherAPI
    1. Register and get your API key at https://openweathermap.org/current
    2. Complete the registration and click your username dropdown and select "My API keys"
    3. Copy the API Key (But wait for an hour or two for it to work)
    4. Check there's already a `local.properties` file in your directory
    5. If there's not create one, if yes add this line to the directory
    6. API_KEY=yourApiKey
    7. Click tools from the top bar then "Clean Project"
    8. Click tools again then "Rebuild Project"

