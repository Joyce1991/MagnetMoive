# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /develop/android/android-sdk-macosx/tools/proguard/proguard-android.txt
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
# 保留注解
-keepattributes *Annotation*
# 保留签名，解决泛型问题
-keepattributes Signature
# 保留行号信息
-keepattributes LineNumberTable
-ignorewarnings
#代码中使用了反射，加入下面的混淆规则即可.
-keepattributes EnclosingMethod

# Gson
-keep class sun.misc.Unsafe.** {*;}
-keep class com.google.gson.stream.** {*;}
-keep class com.google.gson.examples.android.model.** {*;}
-keep class com.google.gson.** {*;}

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

#otto
-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
}

