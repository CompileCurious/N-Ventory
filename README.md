# N-Ventory

**N-Ventory** is a lightweight Android inventory management app designed to help users track items, log transactions, and manage stock levels with ease. Built using Kotlin and the Android SDK, it's ideal for small businesses, hobbyists, or anyone who wants a simple mobile inventory tool.

## Features

- Add, update, and delete inventory items  
- Search and filter items by name or category  
- View transaction logs for stock changes  
- Track quantities and restock thresholds  
- Local data persistence using Room database  
- Clean UI built with XML layouts and Material Design

## Tech Stack

| Layer         | Technology         |
|---------------|--------------------|
| Language      | Kotlin             |
| UI            | XML + Material     |
| Database      | Room (SQLite)      |
| Architecture  | MVVM (planned)     |
| Build System  | Gradle             |
| Target SDK    | Android 13+        |

## Project Structure

```
N-Ventory/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/compilecurious/nventory/
│   │   │   ├── res/
│   │   │   └── AndroidManifest.xml
│   ├── build.gradle
├── build.gradle
├── settings.gradle
```

## Installation

This project is currently being built and maintained directly on GitHub, without Android Studio setup.

To clone the repository:

```
git clone https://github.com/CompileCurious/N-Ventory.git
```

You can open it in Android Studio or build manually using Gradle.

## Development Status

This project is under active development. Current focus areas include:

- Core item management  
- Transaction logging  
- UI polish  
- MVVM architecture refactor  
- Export/import functionality

## Contributing

Pull requests are welcome. If you spot a bug or have a feature idea, feel free to open an issue or submit a PR.

## License

This project is licensed under the MIT License. See the LICENSE file for details.
