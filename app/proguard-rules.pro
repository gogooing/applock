# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Adt-bundle\android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

#-libraryjars class_path 应用的依赖包，如android-support-v4
#-keep [,modifier,...] class_specification 不混淆某些类
#-keepclassmembers [,modifier,...] class_specification 不混淆类的成员
#-keepclasseswithmembers [,modifier,...] class_specification 不混淆类及其成员
#-keepnames class_specification 不混淆类及其成员名
#-keepclassmembernames class_specification 不混淆类的成员名
#-keepclasseswithmembernames class_specification 不混淆类及其成员名
#-assumenosideeffects class_specification 假设调用不产生任何影响，在proguard代码优化时会将该调用remove掉。如system.out.println和Log.v等等
#-dontwarn [class_filter] 不提示warnning

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontpreverify
#-dontoptimize
-dontusemixedcaseclassnames
-optimizationpasses 7
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
#把一些系统的和第三方jar设置为不提示warnning，添加了jar再后面写上就好
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-dontwarn android.support.v4.**
-dontwarn android.inputmethodservice.**
-dontwarn android.widget.**
-dontwarn android.app.**
-dontwarn android.inputmethodservice.**
-dontwarn android.view.**


-dontwarn com.alibaba.**

#-dontwarn de.greenrobot.**
#-dontwarn com.tencent.stat.**
#-dontwarn com.tencent.mid.**
#-dontwarn com.alipay.**
-dontwarn android.support.v7.**
#-dontwarn com.loopj.android.http.**
#-dontwarn it.sephiroth.android.library.exif2.**
-dontwarn org.apache.commons.io.**
#-dontwarn org.lasque.tusdk.core.**
#-dontwarn com.nostra13.universalimageloader.cache.disc.**
-dontwarn android.support.**
-dontwarn org.apache.commons.**

-renamesourcefileattribute ProGuard
-keepattributes SourceFile,LineNumberTable,Signature,Exceptions,InnerClasses,*Annotation*
#把系统的和第三方的进行不混淆
-keep class com.alibaba.**{*;}

-keep class com.sanqiu.loro.applocktest.model.**{*;}
-keep class android.support.v8.renderscript.** { *; }

-keep class com.tencent.mm.opensdk.** {*;}
-keep class * extends android.app.Dialog
-keep class retrofit.** { *; }
-keep class com.guoxiaoxing.phoenix.** { *; }

-keep class com.iflytek.**{*;}
-keepattributes Signature

-keep public class com.tencent.bugly.**{*;}

#-keep class io.rong.** {*;}
-keep public class de.greenrobot.dao.** {*;}
-keep class de.greenrobot.**{*;}
-keep class android.view.**{*;}
-keep class kotlin.reflect.**{*;}

-keep public class com.sanqiu.loro.applocktest.R**{*;}
-keep class *.R$ { *; }

#-libraryjars ../rongim/libs/imkit.jar

#-keep class io.rong.** {*;}
#-keep class io.rong.imkit.** {*;}
#-keep class io.rong.imlib.** {*;}

-keep public class android.support.v4.view.* {*;}
-keep public class android.view.* {*;}

#-keep class org.lasque.tusdk.core.*{*;}
#-keep class org.apache.commons.io.*{*;}
#-keep class com.nostra13.universalimageloader.cache.disc.*{*;}
#-keep class it.sephiroth.android.library.exif2.*{*;}
#-keep class com.loopj.android.http.*{*;}

-keep class android.support.**{ *; }
-keep class org.apache.commons.**{ *; }
#-keep class com.nostra13.universalimageloader.**{ *; }
#-keep class com.loopj.android.http.**{ *; }
#-keep class it.sephiroth.android.library.exif2.**{ *; }

#-keep class org.lasque.tusdk.**{public *; protected *; }

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

-keep class * extends java.lang.annotation.Annotation { *; }
-keep interface * extends java.lang.annotation.Annotation { *; }


#有些项目的类不能进行混淆

#针对greendao数据库不混淆数据库模型
-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
    public static java.lang.String TABLENAME;
}
-keep public class de.greenrobot.dao.R$*{
    public static final int *;
      public static final Objects *;
}
-keep class **$Properties
-dontwarn  org.eclipse.jdt.annotation.**
-keep class * extends java.lang.annotation.Annotation { *; }

-keepclasseswithmembernames class * {
native <methods>;
}

-keepclasseswithmembers class * {
public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
public void *(android.view.View);
}

-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
	public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
  public static <fields>;
}

#这货导致所有类都不会被混淆，先注释掉
#-keep class * {
#    void set*(***);
#    void set*(int, ***);
#    boolean is*();
#    boolean is*(int);
#    *** get*();
#    *** get*(int);
#}

-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
