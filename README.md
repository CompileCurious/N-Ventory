# N-Ventory

**N-Ventory** is a lightweight Android app for managing inventory items. Whether you're tracking tools, supplies, or collectibles, N-Ventory helps you stay organized with a simple, intuitive interface.

---

## Features

- Add items with name, quantity, and description  
- View all items in a clean list format  
- Delete items from your inventory  
- Local storage using SQLite — no internet required  
- Simple and fast UI built with native Android components

---

## Installation

To run the app locally:

1. Clone the repo:
   ```bash
   git clone https://github.com/CompileCurious/N-Ventory.git
   ```
2. Open the project in Android Studio.
3. Switch to the `Android` branch.
4. Let Gradle sync and build the project.
5. Run the app on an emulator or physical device.

---

## Project Structure

```
N-Ventory/
├── app/
│   └── src/
│       └── main/
│           ├── java/com/example/n_ventory/
│           │   ├── MainActivity.java
│           │   ├── AddItemActivity.java
│           │   ├── DatabaseHelper.java
│           │   └── Item.java
│           └── res/
│               ├── layout/
│               │   ├── activity_main.xml
│               │   └── activity_add_item.xml
│               └── values/
│                   └── strings.xml
```

---


## License

This project is licensed under the **Creative Commons Attribution-NonCommercial 4.0 International License**.

You are free to:
- Share — copy and redistribute the material in any medium or format
- Adapt — remix, transform, and build upon the material

Under the following terms:
- Attribution — You must give appropriate credit.
- NonCommercial — You may not use the material for commercial purposes.

Full license text: [https://creativecommons.org/licenses/by-nc/4.0/](https://creativecommons.org/licenses/by-nc/4.0/)
