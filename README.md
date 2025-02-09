# BonFire

BonFire is an Android application built with Jetpack Compose, offering a  way to browse books, view detailed information, manage favorites, search for books, and customize the app theme. The app also features user authentication with Firebase, enabling login and sign-up functionality.

## Features

- **Browse Books**: Explore a collection of books with a user-friendly interface.
- **View Book Details**: Access detailed information about each book.
- **Add to Favorites**: Save your favorite books using Room Database for offline storage.
- **Search Books**: Quickly find books with an integrated search feature.
- **Change System Theme**: Switch between light and dark themes for a personalized experience.
- **User Authentication**: Secure login and sign-up functionality powered by Firebase.

---

## Tech Stack

- **Kotlin**: Programming language used for app development.
- **Jetpack Compose**: Modern UI toolkit for building native Android user interfaces.
- **Room**: Persistent library for local data storage.
- **Firebase Authentication**: Secure user login and registration.
- **MVVM Architecture**: Ensures a clean separation of concerns and easier testing.
- **Coroutines and Flow**: Handle asynchronous operations seamlessly.
- **Material Design 3**: Implements modern and consistent design patterns.

---

## Setup and Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/luka-c/books_app.git
   cd BooksCompose
   ```

2. **Open the project in Android Studio:**
   - Make sure you have the latest version of Android Studio installed.
   - Open the `FirebaseApp` folder.

3. **Sync Gradle:**
   - Let Gradle sync automatically or manually sync it by clicking "Sync Now" in the top-right corner.

4. **Configure Firebase:**
   - Download the `google-services.json` file from your Firebase console.
   - Place it in the `app` directory.

5. **Build and Run the Project:**
   - Connect your Android device or use an emulator.
   - Click the "Run" button in Android Studio or use `Shift + F10`.

---

## Usage

1. **Login/Sign Up:**
   - Use your email and password to create an account or log in.

2. **Browse Books:**
   - Explore the catalog and view a list of books.

3. **View Book Details:**
   - Tap on a book to view more details.

4. **Favorites:**
   - Save books to your favorites list for quick access.

5. **Search:**
   - Use the search bar to find books by title, author, or keywords.

6. **Theme Change:**
   - Switch between light and dark themes from the settings menu.

---

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

