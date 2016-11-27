# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/AirPhebe/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Volley
-keepclasseswithmembernames  interface com.android.volley.** { *; }
-keep class org.json.** {*;}
-keep class android.accounts.**{*;}
-keep class java.io.**{*;}
-keep class android.net.**{*;}
-keep class org.apache.http.**{*;}
-keep class android.net.http.AndroidHttpClient{*;}
-keep class com.android.volley.** {*;}
-keep class com.android.volley.toolbox.** {*;}
-keep class com.android.volley.Response$* { *; }
-keep class com.android.volley.Request$* { *; }
-keep class com.android.volley.RequestQueue$* { *; }
-keep class com.android.volley.toolbox.HurlStack$* { *; }
-keep class com.android.volley.toolbox.ImageLoader$* { *; }
-keep class org.apache.commons.logging.**
-dontwarn javax.management.**
-dontwarn java.lang.management.**
-dontwarn org.apache.log4j.**
-dontwarn org.apache.commons.logging.**
-dontwarn org.slf4j.**
-dontwarn org.json.**
-dontwarn org.apache.**
-dontwarn com.squareup.picasso.**
-dontwarn org.codehaus.jackson.**
-dontwarn android.net.http.**
-dontwarn org.simpleframework.xml.**
-dontwarn org.w3c.dom.**
-dontwarn com.android.volley.**
-dontwarn com.handmark.pulltorefresh.**
-dontwarn com.navercorp.volleyextensions.**


# TwowayView
-keep class org.lucasr.twowayview.** { *; }


# Picasso
#-dontwarn com.squareup.okhttp.**


# For PhotoPicker
# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}
# nineoldandroids
-keep interface com.nineoldandroids.view.** { *; }
-dontwarn com.nineoldandroids.**
-keep class com.nineoldandroids.** { *; }
# support-v7-appcompat
-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }
-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}
# support-design
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }