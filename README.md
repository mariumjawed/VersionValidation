# VersionValidation

![Alt text](https://github.com/mariumjawed/VersionValidation/blob/master/vc1.PNG)

## Integration
### In Java File
```groovy
   //  Integrete FireBase
```
```groovy
   
package com.tekrevol.validateversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String CHILD_ID = "Android";
    private String versionName, versionCode;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference referenceUpdate, reference;
    private Dialog dialog2;
    private String verCode, verName, logoutVersion, updateVersion, ignoreUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        onBind();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onBind();
    }

    private void onBind() {

        firebaseDatabase = FirebaseDatabase.getInstance();
        referenceUpdate = firebaseDatabase.getReference("Updates");
        reference = firebaseDatabase.getReference();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Validation();
                } else {

                    Validation validation = new Validation(versionName, versionCode, "android", "1", "Y", "N");
                    referenceUpdate.child(CHILD_ID).setValue(validation);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void Validation() {
        referenceUpdate.child(CHILD_ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //   for (DataSnapshot datas : dataSnapshot.getChildren()) {
                verCode = dataSnapshot.child("versionCode").getValue().toString();
                verName = dataSnapshot.child("versionName").getValue().toString();
                // deviceType = dataSnapshot.child("deviceType").getValue().toString();
                logoutVersion = dataSnapshot.child("logoutVersion").getValue().toString();
                updateVersion = dataSnapshot.child("updateVersion").getValue().toString();
                ignoreUpdate = dataSnapshot.child("ignore").getValue().toString();
                int vCodeFirebase, vCode, logoutV;
                vCodeFirebase = Integer.parseInt(verCode);
                vCode = Integer.parseInt(versionCode);
                logoutV = Integer.parseInt(logoutVersion);

                if (updateVersion.equals("Y")) {
                    if (vCode < logoutV) {
                        UpdateForcefully();
                    } else if (vCodeFirebase > vCode) {
                        Update();
                    } else if ((vCodeFirebase < vCode) && (ignoreUpdate.equals("N"))) {
                        referenceUpdate.child(CHILD_ID).child("versionCode").setValue(versionCode);
                        referenceUpdate.child(CHILD_ID).child("versionName").setValue(versionName);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void UpdateForcefully() {

        dialog2 = new Dialog(MainActivity.this);
        dialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog2.setContentView(R.layout.dialog_box_logout_update);
        dialog2.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog2.setCancelable(false);
        dialog2.show();
        TextView btnUpdate = dialog2.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    dialog2.dismiss();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    //    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

    }

    private void Update() {

        final Dialog dialog1 = new Dialog(MainActivity.this);
        dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog1.setContentView(R.layout.dialog_box_update);
        dialog1.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog1.show();
        TextView btnUpdate = dialog1.findViewById(R.id.btnUpdate);
        TextView txtVersionNumder = dialog1.findViewById(R.id.txtVersionNumder);
        txtVersionNumder.setText("New version " + versionName + " is available now, please update");
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    dialog1.dismiss();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    //    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
   
```
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

```groovy

// getting data from database and checking validation

    private void Validation() {
        referenceUpdate.child(CHILD_ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                verCode = dataSnapshot.child("versionCode").getValue().toString();
                verName = dataSnapshot.child("versionName").getValue().toString();
                // deviceType = dataSnapshot.child("deviceType").getValue().toString();
                logoutVersion = dataSnapshot.child("logoutVersion").getValue().toString();
                updateVersion = dataSnapshot.child("updateVersion").getValue().toString();
                ignoreUpdate = dataSnapshot.child("ignore").getValue().toString();
                int vCodeFirebase, vCode, logoutV;
                vCodeFirebase = Integer.parseInt(verCode);
                vCode = Integer.parseInt(versionCode);
                logoutV = Integer.parseInt(logoutVersion);

// if "updateVersion is equal to Y" then allow updating new version.

                if (updateVersion.equals("Y")) {
                    if (vCode < logoutV) {
                        UpdateForcefully();
                    } else if (vCodeFirebase > vCode) {
                        Update();
                    } else if ((vCodeFirebase < vCode) && (ignoreUpdate.equals("N"))) {
                        referenceUpdate.child(CHILD_ID).child("versionCode").setValue(versionCode);
                        referenceUpdate.child(CHILD_ID).child("versionName").setValue(versionName);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

```


