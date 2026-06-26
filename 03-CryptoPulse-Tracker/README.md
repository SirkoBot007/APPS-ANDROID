# CryptoPulse 📈🪙

A live **crypto price tracker** powered by the public **CoinGecko API**. Browse the top 50 coins
with 24h change and a 7-day sparkline, open a detail screen, **pull to refresh**, and let prices
update in the background. Fully usable **offline** thanks to a Room cache.

> Portfolio project 3 of 4 — by **Sirko007**. Built with Kotlin + Jetpack Compose.

---

## 🎯 What this project demonstrates

This is the **"real networking + background work"** piece of the portfolio. Compared to the other
projects it specifically shows I can:

- **Consume a real public REST API** (CoinGecko) with Retrofit + Coroutines — typed query params,
  list parsing, nullable handling. No API key required.
- Implement a **network-bound / offline-first cache**: the UI always reads from Room, and a
  `refresh()` writes fresh network data back. Single source of truth = the database.
- Handle **all network states**: loading / success / error (snackbar) / empty, with retry.
- Run **background work with WorkManager** — a periodic `@HiltWorker` refreshes prices every ~30
  min even when the app is closed, wired into **Hilt** via a custom `WorkerFactory`.
- Draw a **custom sparkline chart** with Compose Canvas (no chart library).
- Load remote images with **Coil**, and use Material 3 **pull-to-refresh**.

---

## 🏗️ Architecture (MVVM + Clean layers)

```
data/
 ├─ remote/    Retrofit: CoinGeckoApi + DTOs (CoinDto, SparklineDto)
 ├─ local/     Room: CoinEntity, CoinDao, Converters (List<Double> ⇄ String), CryptoDatabase
 └─ repository/CryptoRepository (network-bound: API → Room, UI observes Room)
work/          PriceRefreshWorker (@HiltWorker periodic background refresh)
di/            Hilt module (Retrofit + OkHttp + Room)
ui/
 ├─ list/      CoinListScreen + CoinListViewModel (pull-to-refresh)
 ├─ detail/    CoinDetailScreen + CoinDetailViewModel
 ├─ components/SparklineChart (Compose Canvas)
 ├─ navigation/AppNavigation (typed nav arg: coinId)
 └─ theme/     Material 3 dark theme
```

**Data flow:** `CryptoRepository.refresh()` calls CoinGecko → maps DTOs → writes to Room. Every
screen observes Room via `Flow`/`StateFlow`, so cached prices render instantly and update reactively.

---

## 🛠️ Tech stack

| Concern | Choice |
|--------|--------|
| Language | Kotlin 2.0 |
| UI | Jetpack Compose + Material 3 |
| Architecture | MVVM + Repository (offline-first) |
| DI | Hilt (incl. Hilt + WorkManager integration) |
| Networking | Retrofit + OkHttp + Moshi |
| Local cache | Room (+ TypeConverters) |
| Background | WorkManager (periodic `@HiltWorker`) |
| Images | Coil |
| Charts | Compose Canvas (custom sparkline) |
| Async | Coroutines + Flow / StateFlow |

---

## ▶️ How to run

1. **Open in Android Studio** (Hedgehog or newer) and let Gradle sync.
2. **Run** on an emulator or device (min SDK 24). The first launch fetches live prices;
   afterwards the cached data shows instantly, even offline.
3. **Pull down** on the list to refresh manually. Background refresh runs automatically.

> Uses CoinGecko's free public endpoint — no key, no signup. The free tier is rate-limited, so
> if a refresh fails you'll see a friendly snackbar and the cached prices stay on screen.

---

## 📌 Possible next steps

- Favorites tab (persist favorite coin ids in Room).
- Price-alert notifications when a coin moves beyond a threshold.
- Currency switch (USD/EUR) and search/filter.
- Paging 3 to load beyond the top 50.
