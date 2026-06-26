# ── DayFlow · R8/ProGuard rules ──────────────────────────────────────────────
# Room and Hilt ship their own consumer rules. The Priority enum is persisted as
# a string in the database (Priority.name), so its constant names must survive
# obfuscation to keep stored tasks readable across updates.
-keep class com.sirko007.dayflow.domain.Priority { *; }

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
