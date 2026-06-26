# ── FinTrack · R8/ProGuard rules ─────────────────────────────────────────────
# Room and Hilt ship their own consumer rules. The one app-specific risk is the
# Category enum: its constant names are persisted as strings in the database
# (Category.name), so they MUST survive obfuscation or saved data would break.
-keep class com.sirko007.fintrack.domain.Category { *; }

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
