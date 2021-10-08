// let RNAndroidInstalledApps = require("react-native").NativeModules
//   .RNAndroidInstalledApps;

import { NativeModules } from "react-native";

const { RNAndroidInstalledApps } = NativeModules;

const itemPropertyType = {
  appName: 0,
  packageName: 1,
  versionName: 2,
  versionCode: 3,
  firstInstallTime: 4,
  lastUpdateTime: 5,
  icon: 6,
  apkDir: 7,
  size: 8,
};

/**
 * @typedef {Object} AppInfo
 * @property {string} packageName
 * @property {string} versionName
 * @property {number} versionCode
 * @property {number} firstInstallTime
 * @property {number} lastUpdateTime
 * @property {string} appName
 * @property {string} icon
 * @property {string} apkDir
 * @property {number} size
 */

/**
 *
 * @param {string} input
 * @returns {Array<AppInfo>}
 */
function convertToListAppInfo(input) {
  let lastResult = [];
  const arr = Object.values(input);
  for (let i = 0; i < arr.length; i++) {
    const items = arr[i].split(",");
    if (items.length === 9) {
      lastResult.push({
        appName: items[itemPropertyType.appName],
        packageName: items[itemPropertyType.packageName],
        versionName: items[itemPropertyType.versionName],
        versionCode: Number(items[itemPropertyType.versionCode]),
        firstInstallTime: Number(items[itemPropertyType.firstInstallTime]),
        lastUpdateTime: Number(items[itemPropertyType.lastUpdateTime]),
        icon: items[itemPropertyType.icon],
        apkDir: items[itemPropertyType.apkDir],
        size: Number(items[itemPropertyType.size]),
      });
    }
  }

  return lastResult;
}

/**
 * @returns {Promise<Array<AppInfo>>}
 */
export function getApps() {
  return new Promise((resolve, reject) => {
    RNAndroidInstalledApps.getApps()
      .then((result) => {
        resolve(convertToListAppInfo(result));
      })
      .catch((error) => reject(error));
  });
}

/**
 * @returns {Promise<Array<AppInfo>>}
 */
export function getNonSystemApps() {
  return new Promise((resolve, reject) => {
    RNAndroidInstalledApps.getNonSystemApps()
      .then((result) => {
        resolve(convertToListAppInfo(result));
      })
      .catch((error) => reject(error));
  });
}

/**
 * @returns {Promise<Array<AppInfo>>}
 */
export function getSystemApps() {
  return new Promise((resolve, reject) => {
    RNAndroidInstalledApps.getSystemApps()
      .then((result) => {
        resolve(convertToListAppInfo(result));
      })
      .catch((error) => reject(error));
  });
}

/**
 *
 * @param {string} packageName
 */
export function openApp(packageName) {
  return new Promise((resolve, reject) => {
    RNAndroidInstalledApps.openApp(packageName)
      .then((message) => {
        resolve(message);
      })
      .catch((error) => reject(error));
  });
}
