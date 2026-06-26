# Keep Moshi-generated adapters and model fields used via reflection.
-keep class com.sirko007.smartnotes.data.remote.dto.** { *; }
-keepclassmembers class * {
    @com.squareup.moshi.Json <fields>;
}
