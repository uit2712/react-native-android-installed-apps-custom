
package com.androidinstalledapps;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.Arguments;

import android.content.pm.PackageInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.File;

import javax.annotation.Nullable;

import com.helper.*;

public class RNAndroidInstalledAppsModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNAndroidInstalledAppsModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNAndroidInstalledApps";
  }

  @ReactMethod
  public void getApps(Promise promise) {
    try {
      PackageManager pm = this.reactContext.getPackageManager();
      List<PackageInfo> pList = pm.getInstalledPackages(0);
      WritableMap appInfo = new WritableNativeMap();
      for (int i = 0; i < pList.size(); i++) {
        PackageInfo packageInfo = pList.get(i);
        Drawable icon = pm.getApplicationIcon(packageInfo.applicationInfo);
        String apkDir = packageInfo.applicationInfo.publicSourceDir;
        File file = new File(apkDir);
        double size = file.length();
        appInfo.putString("[" + i + "]",
            ((String) packageInfo.applicationInfo.loadLabel(pm)).trim() + "," + packageInfo.packageName + ","
                + packageInfo.versionName + "," + packageInfo.versionCode + "," + packageInfo.firstInstallTime + ","
                + packageInfo.lastUpdateTime + "," + Utility.convert(icon) + "," + apkDir + "," + size);
      }
      promise.resolve(appInfo);
    } catch (Exception ex) {
      promise.reject(ex);
    }
  }

  @ReactMethod
  public void getNonSystemApps(Promise promise) {
    try {
      PackageManager pm = this.reactContext.getPackageManager();
      List<PackageInfo> pList = pm.getInstalledPackages(0);
      WritableMap appInfo = Arguments.createMap();
      for (int i = 0; i < pList.size(); i++) {
        PackageInfo packageInfo = pList.get(i);

        if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
          Drawable icon = pm.getApplicationIcon(packageInfo.applicationInfo);
          String apkDir = packageInfo.applicationInfo.publicSourceDir;
          File file = new File(apkDir);
          double size = file.length();
          appInfo.putString("[" + i + "]",
              ((String) packageInfo.applicationInfo.loadLabel(pm)).trim() + "," + packageInfo.packageName + ","
                  + packageInfo.versionName + "," + packageInfo.versionCode + "," + packageInfo.firstInstallTime + ","
                  + packageInfo.lastUpdateTime + "," + Utility.convert(icon) + "," + apkDir + "," + size);
        }
      }
      promise.resolve(appInfo);
    } catch (Exception ex) {
      promise.reject(ex);
    }

  }

  @ReactMethod
  public void getSystemApps(Promise promise) {
    try {
      PackageManager pm = this.reactContext.getPackageManager();
      List<PackageInfo> pList = pm.getInstalledPackages(0);
      WritableMap appInfo = Arguments.createMap();
      for (int i = 0; i < pList.size(); i++) {
        PackageInfo packageInfo = pList.get(i);

        if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
          Drawable icon = pm.getApplicationIcon(packageInfo.applicationInfo);
          String apkDir = packageInfo.applicationInfo.publicSourceDir;
          File file = new File(apkDir);
          double size = file.length();
          appInfo.putString("[" + i + "]",
              ((String) packageInfo.applicationInfo.loadLabel(pm)).trim() + "," + packageInfo.packageName + ","
                  + packageInfo.versionName + "," + packageInfo.versionCode + "," + packageInfo.firstInstallTime + ","
                  + packageInfo.lastUpdateTime + "," + Utility.convert(icon) + "," + apkDir + "," + size);
        }
      }
      promise.resolve(appInfo);
    } catch (Exception ex) {
      promise.reject(ex);
    }
  }

  @ReactMethod
  public void openApp(String packageName, final Promise promise) {
    Intent sendIntent = this.reactContext.getPackageManager().getLaunchIntentForPackage(packageName);
    if (sendIntent == null) {
      promise.reject("Cannot open app.");
      return;
    }

    sendIntent.addCategory(Intent.CATEGORY_LAUNCHER);
    this.reactContext.startActivity(sendIntent);
    promise.resolve("Open app success.");
  }
}