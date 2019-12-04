# VersionValidation

### Description
```groovy

// In this we are getting project version code and version name

    try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            Log.d("Build Version Name: ", info.versionName);
            versionName = info.versionName;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                Log.d("Build Version Code: ", String.valueOf(info.getLongVersionCode()));
                versionCode = String.valueOf(info.getLongVersionCode());
            } else {
                Log.d("Build Version Code: ", "missing");
            }
            /*txtVersionNumber.setText("Build Version: " + info.versionName);*/
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("Build Version: ", "missing");
            e.printStackTrace();
        }


```
