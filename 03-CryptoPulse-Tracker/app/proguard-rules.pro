# ── CryptoPulse · R8/ProGuard rules ──────────────────────────────────────────
# Retrofit, OkHttp, Hilt, Room, WorkManager and Coil ship their own consumer
# rules. We add the Moshi-specific keeps for the network DTOs.

-keep class com.sirko007.cryptopulse.data.remote.dto.** { *; }
-keepclassmembers class * {
    @com.squareup.moshi.Json <fields>;
}

-keepattributes Signature, *Annotation*, InnerClasses, EnclosingMethod

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
