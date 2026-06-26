# ── SmartNotes AI · R8/ProGuard rules ────────────────────────────────────────
# Most libraries (Retrofit, OkHttp, Hilt, Room, Coroutines) ship their own
# consumer rules, so we only add what is specific to this app.

# Moshi: keep the DTO models and any @Json-annotated fields (used by generated
# adapters / reflection at the JSON boundary).
-keep class com.sirko007.smartnotes.data.remote.dto.** { *; }
-keepclassmembers class * {
    @com.squareup.moshi.Json <fields>;
}

# Keep generic signatures and annotations Retrofit/Moshi rely on.
-keepattributes Signature, *Annotation*, InnerClasses, EnclosingMethod

# Standard enum members (defensive).
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
