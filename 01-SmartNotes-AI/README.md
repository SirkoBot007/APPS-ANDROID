# SmartNotes AI 🧠📝

A notes app with a built-in **AI writing assistant** powered by the **Anthropic Claude API**.
Write a note, then tap **Summarize**, **Improve**, or **Expand** and Claude rewrites it for you.

> Portfolio project 1 of 4 — by **Sirko007**. Built with Kotlin + Jetpack Compose.

---

## 🎯 What this project demonstrates

This is the **"AI integration"** piece of the portfolio — in 2026 recruiters treat at least one
real LLM-powered project as table stakes. It shows I can:

- **Integrate a real LLM API** (Claude Messages API) over the network — not a mock.
- **Handle API keys securely**: the key lives in `local.properties` (git-ignored) and is exposed
  through `BuildConfig`, never hard-coded or committed.
- Model **loading / success / error** states cleanly with `StateFlow` and a `sealed interface`.
- Apply **MVVM + Clean separation**: UI → ViewModel → Repository → (Room + Retrofit).
- Use **Hilt** for dependency injection across the whole graph.
- Persist data locally with **Room** (offline notes survive without network).
- Build a 100% **Jetpack Compose + Material 3** UI with type-safe navigation.

---

## 🏗️ Architecture (MVVM + Clean layers)

```
ui/            Compose screens + ViewModels (presentation)
 ├─ notes/     NotesListScreen + NotesViewModel
 ├─ editor/    NoteEditorScreen + NoteEditorViewModel
 ├─ navigation/AppNavigation (NavHost)
 └─ theme/     Material 3 theme

data/          (data layer — single source of truth)
 ├─ local/     Room: NoteEntity, NoteDao, SmartNotesDatabase
 ├─ remote/    Retrofit: AnthropicApi + DTOs
 └─ repository/NotesRepository, AiRepository

di/            Hilt module wiring Room + Retrofit singletons
```

**Data flow:** `Composable` collects a `StateFlow` from the `ViewModel`; the ViewModel calls a
`Repository`; the repository talks to Room (local) or the Anthropic API (remote). The UI never
touches the DAO or Retrofit directly.

---

## 🛠️ Tech stack

| Concern | Choice |
|--------|--------|
| Language | Kotlin 2.0 |
| UI | Jetpack Compose + Material 3 |
| Architecture | MVVM + Repository pattern |
| DI | Hilt |
| Local storage | Room |
| Networking | Retrofit + OkHttp + Moshi |
| Async | Coroutines + Flow / StateFlow |
| Navigation | Navigation-Compose |
| AI | Anthropic Claude API (`claude-haiku-4-5`) |

---

## ▶️ How to run

1. **Open in Android Studio** (Hedgehog or newer). Let Gradle sync — it will download the
   Gradle wrapper and dependencies automatically.
2. **Add your API key.** Copy `local.properties.example` → `local.properties` and set:
   ```properties
   ANTHROPIC_API_KEY=sk-ant-your-real-key
   ```
   Get a key at <https://console.anthropic.com/>. `local.properties` is git-ignored.
3. **Run** on an emulator or device (min SDK 24).

> Notes are saved locally and work offline. The AI buttons require the API key + an internet
> connection. Without a key, the app still runs and shows a friendly error on AI actions.

---

## 🔒 Security notes

- The API key is **never** committed — it is read from `local.properties` at build time.
- In a production app the key would live on a **backend proxy**, not in the client. This project
  keeps it client-side for portfolio simplicity, and documents the trade-off here on purpose.

---

## 📌 Possible next steps

- Stream tokens as they arrive (SSE) for a live "typing" effect.
- Tag/search notes; full-text search with Room FTS.
- Backend proxy for the API key + per-user rate limiting.
