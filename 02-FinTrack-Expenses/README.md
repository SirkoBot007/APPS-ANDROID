# FinTrack 💸📊

An **offline-first** expense & income tracker. Add transactions, see your live balance, and get a
**spending breakdown by category** drawn with a hand-built donut chart. Works fully without internet.

> Portfolio project 2 of 4 — by **Sirko007**. Built with Kotlin + Jetpack Compose.

---

## 🎯 What this project demonstrates

This is the **"data & architecture"** piece of the portfolio. Where project 1 (SmartNotes AI)
shows networking/LLM integration, this one shows I can:

- Build a genuinely **offline-first** app — Room is the single source of truth, zero network code.
- Drive a fully **reactive UI**: the balance, totals and chart recompute automatically via `Flow`
  whenever a transaction is added or deleted (`observeAll()` → `map` → `StateFlow`).
- Write real **business/aggregation logic**: balance = income − expense, grouping and summing
  expenses per category, and computing each category's share of the total.
- Render **custom data visualization** with **Compose Canvas** (a donut chart) — no paid/3rd-party
  charting library.
- Apply **MVVM + Repository**, **Hilt** DI, input **validation**, and Material 3 components
  (segmented buttons, filter chips, flow layout).

---

## 🏗️ Architecture (MVVM + Clean layers)

```
domain/        Category enum (label + color + income flag)
data/
 ├─ local/     Room: TransactionEntity, TransactionDao, FinTrackDatabase
 └─ repository/FinanceRepository (single source of truth, offline-only)
ui/
 ├─ home/      HomeScreen + HomeViewModel (balance + breakdown + list)
 ├─ add/       AddTransactionScreen + AddTransactionViewModel (validated form)
 ├─ components/CategoryDonutChart (Compose Canvas)
 ├─ navigation/AppNavigation
 └─ theme/     Material 3 theme
```

**Key idea:** the ViewModel never stores derived numbers — it *derives* them from the transaction
list every time the data changes. One source of truth, no stale totals.

---

## 🛠️ Tech stack

| Concern | Choice |
|--------|--------|
| Language | Kotlin 2.0 |
| UI | Jetpack Compose + Material 3 |
| Architecture | MVVM + Repository pattern |
| DI | Hilt |
| Local storage | Room (offline-first) |
| Async / reactivity | Coroutines + Flow / StateFlow |
| Charts | Compose Canvas (custom donut) |
| Navigation | Navigation-Compose |

---

## ▶️ How to run

1. **Open in Android Studio** (Hedgehog or newer) and let Gradle sync.
2. **Run** on an emulator or device (min SDK 24).
3. Add a few expenses/incomes with different categories and watch the donut + balance update live.

No API keys, no accounts, no internet required.

---

## 📌 Possible next steps

- Filter by month / date range.
- Export transactions to CSV.
- Budgets per category with progress bars and alerts.
- Room migration example (schema v1 → v2) to show migration handling.
