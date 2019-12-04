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
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("Build Version: ", "missing");
            e.printStackTrace();
        }


```
```groovy

// We are getting project version code and version name from this code:

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
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("Build Version: ", "missing");
            e.printStackTrace();
        }


```
```groovy

// Checking if database exist

  reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                
                // if datasnapshot is not null
                    Validation();
                } else {

                // if datasnapshot is null, creating table Update and its child.
              
                    Validation validation = new Validation(versionName, versionCode, "android", "1", "Y", "N");
                    referenceUpdate.child(CHILD_ID).setValue(validation);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

```

