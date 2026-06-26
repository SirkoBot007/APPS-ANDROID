# Keep Moshi-generated adapters / DTO fields used via reflection.
-keep class com.sirko007.cryptopulse.data.remote.dto.** { *; }
-keepclassmembers class * {
    @com.squareup.moshi.Json <fields>;
}
