# DayFlow ✅🗓️

A clean **task & habit manager**: create tasks with priority and an optional due date, check them
off, filter by All / Today / Pending / Done, and **swipe to delete**. Fully offline, with a polished
Material 3 UI and subtle list animations.

> Portfolio project 4 of 4 — by **Sirko007**. Built with Kotlin + Jetpack Compose.

---

## 🎯 What this project demonstrates

This is the **"polished product baseline"** of the portfolio — the kind of clean, complete CRUD app
that proves day-to-day Android fundamentals are solid. It specifically shows I can:

- Build a **complete CRUD** flow (create / read / update / delete) on Room with clean MVVM.
- Derive UI state **reactively** by `combine`-ing the task stream with the selected filter — the
  pending/done counts and the visible list recompute themselves.
- Use modern Material 3 interactions: **`SwipeToDismissBox`** to delete, **`FilterChip`** filters,
  checkbox completion with strikethrough, and **`Modifier.animateItem()`** for smooth reordering.
- Keep a tidy **layered architecture** (UI → ViewModel → Repository → Room) identical to the other
  portfolio apps, showing consistency across a codebase.
- Handle **form validation** and edit-vs-create flows with `SavedStateHandle` navigation arguments.

---

## 🏗️ Architecture (MVVM + Clean layers)

```
domain/        Priority + TaskFilter enums
data/
 ├─ local/     Room: TaskEntity, TaskDao, DayFlowDatabase
 └─ repository/TaskRepository (single source of truth)
ui/
 ├─ tasks/     TasksScreen + TasksViewModel (filters, swipe, counts)
 ├─ edit/      EditTaskScreen + EditTaskViewModel (create/edit + validation)
 ├─ navigation/AppNavigation (typed nav arg: taskId)
 ├─ DateUtils  due-date helpers
 └─ theme/     Material 3 theme (light + dark)
```

---

## 🛠️ Tech stack

| Concern | Choice |
|--------|--------|
| Language | Kotlin 2.0 |
| UI | Jetpack Compose + Material 3 |
| Architecture | MVVM + Repository pattern |
| DI | Hilt |
| Local storage | Room |
| Async / reactivity | Coroutines + Flow / StateFlow |
| Navigation | Navigation-Compose |

---

## ▶️ How to run

1. **Open in Android Studio** (Hedgehog or newer) and let Gradle sync.
2. **Run** on an emulator or device (min SDK 24).
3. Add tasks, set priorities, toggle "Due today", check them off, switch filters, and swipe a card
   left to delete.

No accounts, no internet, no keys required.

---

## 📌 Possible next steps

- Local reminder notifications (AlarmManager + `POST_NOTIFICATIONS` runtime permission).
- Recurring habits with streak tracking.
- Drag-to-reorder and a full date/time picker for due dates.
