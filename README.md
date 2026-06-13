# 📱 TaskFlow — To-Do & Task Manager

A clean and modern Android task management app built with **Kotlin**, **Room Database**, and **MVVM Architecture**. Organize your daily tasks with categories, priorities, due dates, and offline storage.

---

## 📸 Screenshots

| Categories | Tasks |
|------------|-------|
| ![](Images/First.png) | ![](Images/Task.png) |

---

## ✨ Features

- Create and manage task categories
- Add, edit, delete, and complete tasks
- Search tasks instantly
- High, Medium, and Low priority levels
- Set due dates
- Offline-first with Room Database
- Cascade delete (deleting a category removes its tasks)

---

## 🏗️ Architecture

This app follows the **MVVM (Model-ViewModel-Repository)** architecture.

```text
UI (Fragments)
      ↓
ViewModel
      ↓
Repository
      ↓
Room Database
```

---

## 🛠️ Tech Stack

- Kotlin
- Room Database
- MVVM Architecture
- ViewModel
- LiveData
- Coroutines
- Navigation Component
- RecyclerView
- Material Design 3
- ViewBinding
- KSP

---

## 📂 Project Structure

```text
com.example.taskflow
│
├── data
├── repository
├── viewmodel
├── ui
│   ├── category
│   └── task
├── MainActivity.kt
└── TaskFlowApplication.kt
```

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- Android SDK 24+
- Kotlin 2.0+

### Installation

```bash
git clone https://github.com/your-username/TaskFlow.git
```

1. Open the project in Android Studio.
2. Let Gradle sync.
3. Run the app on an emulator or physical device.

---

## 🌱 Default Categories

- ☀️ Today
- 👤 Personal
- 💼 Work
- 🛒 Shopping
- 📚 Study

---

## 🔮 Future Improvements

- Date Picker
- Notifications & Reminders
- Dark Mode
- Task Sorting
- Cloud Backup
- Home Screen Widget
- Task Statistics

---

## 📄 License

This project is licensed under the **MIT License**.
