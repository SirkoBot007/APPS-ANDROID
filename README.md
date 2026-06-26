# Android Portfolio — Sirko007

Four production-style Android apps, each built to demonstrate a **distinct, in-demand competency**
for 2026 hiring. All share the same modern stack — **Kotlin · Jetpack Compose · MVVM · Hilt · Room**
— so the portfolio reads as a consistent, opinionated codebase rather than four random demos.

> Research-driven selection: in 2026 recruiters expect **at least one real AI/LLM project**, strong
> **architecture (MVVM/Clean + offline-first)**, **real API + background work**, and **polished UX** —
> and value *a few excellent, well-documented apps over many unfinished ones*. These four cover that
> spread with no overlap.

---

## The four apps

| # | App | The competency it proves | Headline tech |
|---|-----|--------------------------|---------------|
| 1 | [**SmartNotes AI**](01-SmartNotes-AI/) | Real **LLM/AI integration** (the 2026 must-have) | Claude API, Retrofit, secure key handling |
| 2 | [**FinTrack**](02-FinTrack-Expenses/) | **Offline-first data & visualization** | Room, Flow aggregation, Compose Canvas donut |
| 3 | [**CryptoPulse**](03-CryptoPulse-Tracker/) | **Real REST API + background work + cache** | CoinGecko, WorkManager+Hilt, offline cache, sparklines |
| 4 | [**DayFlow**](04-DayFlow-Tasks/) | **Polished CRUD & modern Material 3 UX** | SwipeToDismiss, filters, animations |

Each folder has its own README explaining exactly what it demonstrates, the architecture, and how
to run it.

---

## Shared engineering conventions

- **Architecture:** UI (Compose) → ViewModel (`StateFlow`) → Repository → data source. The UI never
  touches a DAO or network client directly.
- **DI:** Hilt across the whole graph (`@HiltAndroidApp`, `@HiltViewModel`, modules).
- **Async:** Kotlin Coroutines + Flow; `collectAsStateWithLifecycle` in the UI.
- **Build:** Gradle Kotlin DSL + **version catalogs** (`gradle/libs.versions.toml`), KSP for Room/Hilt.
- **UI:** 100% Jetpack Compose + Material 3, no XML layouts. Adaptive launcher icons are vector-based.
- **Min SDK 24 · Target/Compile SDK 34 · Kotlin 2.0.**

---

## How to open any project

1. Open the **individual app folder** (e.g. `01-SmartNotes-AI/`) in **Android Studio** (Hedgehog or
   newer) — open each app as its own project, not this parent folder.
2. Let Gradle sync; Android Studio downloads the Gradle wrapper and dependencies automatically.
3. Press **Run**.

> **SmartNotes AI only** needs a key: copy `local.properties.example` → `local.properties` and add
> your `ANTHROPIC_API_KEY`. The other three need no keys, accounts, or setup. CryptoPulse uses
> CoinGecko's free public API.

---

## Why this selection (the short version)

- **AI is table stakes now** → SmartNotes AI integrates a real LLM, not a mock.
- **Architecture is what gets tested** → FinTrack is genuinely offline-first with real aggregation
  logic and a hand-drawn chart.
- **APIs + background are everyday Android** → CryptoPulse does real networking, a Room cache, and a
  WorkManager job wired into Hilt.
- **Fundamentals must look polished** → DayFlow is a clean, complete CRUD app with modern Material 3
  interactions.

Built by **Sirko007**.
