# Shopping List Application (TDD + CI/CD)

This is an Android application that stores shopping lists in a Room database, fetches shopping item images from the Pixabay API, and is built using the MVI (Model-View-Intent) architecture. The app implements Test-Driven Development (TDD) principles and Continuous Integration/Continuous Deployment (CI/CD) pipelines.

## Features

- **Room Database**: Store shopping list items with local persistence.
- **Pixabay API Integration**: Fetch and display images for shopping items.
- **MVI Architecture**: Implements Model-View-Intent architecture for cleaner, testable code.
- **Test-Driven Development (TDD)**: Application is developed using TDD to ensure high test coverage and code quality.
- **CI/CD**: Automated testing, report & artifact generation and deployment using CI/CD pipelines.
- **XML for UI Design**: Uses XML for designing the user interface.

## Tech Stack

- **Programming Language**: Kotlin
- **Architecture**: MVI (Model-View-Intent)
- **Database**: Room (SQLite abstraction)
- **API**: Pixabay (to fetch item images)
- **UI**: XML layouts
- **Testing**: JUnit, Espresso (for UI testing), Mockito
- **CI/CD**: GitHub Actions

## Screenshots

_Add screenshots of your app here._

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/MrAmmia/shoppingList_TDD_CICD.git
   cd shoppingList_TDD_CICD

2. Get an API key from Pixabay and add it to your local.properties file:
   ```bash
   PIXABAY_API_KEY=your_api_key

3. Build the project in Android Studio or using Gradle:
   ```bash
   ./gradlew build

4. Run the tests:
   ```bash
   ./gradlew test

5. Generate the APK and Lint report
   CI/CD is set up using GitHub Actions. Upon pushing to the main branch, the app will automatically be built, tested, and necessary reports generated.


## Testing

This project uses TDD principles. Unit tests and UI tests have been written to ensure the functionality and quality of the code.
1. Unit Tests: Located in the src/test directory.
2. UI Tests: Located in the src/androidTest directory.
Run all tests using:
   ```bash
   ./gradlew test
   ./gradlew connectedAndroidTest


## CI/CD

The project uses GitHub Actions for CI/CD, with workflows defined for:
1. Running tests on every push and pull request.
2. Automatically building the app when code is merged into the main branch

## Contributing

Contributions are welcome! Please feel free to submit issues, fork the repository, and open pull requests.
