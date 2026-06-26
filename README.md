# Android Portfolio — Sirko007

[![Android CI](https://github.com/SirkoBot007/APPS-ANDROID/actions/workflows/android-ci.yml/badge.svg)](https://github.com/SirkoBot007/APPS-ANDROID/actions/workflows/android-ci.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0-blueviolet.svg)](https://kotlinlang.org)

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
- **Min SDK 24 · Target/Compile SDK 35 · Kotlin 2.0.**
- **Release-ready:** R8 + resource shrinking, keystore-based signing (via `keystore.properties`),
  and CI that builds all four apps on every push.

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

---

## 🚀 Google Play readiness

Every app is prepared for store submission — see **[PLAY-STORE.md](PLAY-STORE.md)** for the full
step-by-step guide. Already in place:

- **Signed release builds** via `keystore.properties` (template per app), R8 + `shrinkResources`.
- **Store listings** (ES + EN) in Fastlane format under each app's `fastlane/metadata/android/`.
- **Privacy policy** and **Data safety** answers per app (`PRIVACY-POLICY.md`, `DATA-SAFETY.md`).
- **`targetSdk 35`**, `versionName 1.0.0` — compliant with current Play requirements.

What still needs the account owner: generating the signing keystore, the Play Console account,
screenshots/graphics, and the upload itself. All documented in `PLAY-STORE.md`.

Built by **Sirko007**.
