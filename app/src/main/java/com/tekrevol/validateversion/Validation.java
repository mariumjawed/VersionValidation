package com.tekrevol.validateversion;

public class Validation {

    public String versionName, versionCode, deviceType, logoutVersion, updateVersion, ignore;

    public Validation() {
    }

    public Validation(String versionName, String versionCode, String deviceType, String logoutVersion, String updateVersion, String ignore) {
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.deviceType = deviceType;
        this.logoutVersion = logoutVersion;
        this.updateVersion = updateVersion;
        this.ignore = ignore;
    }
}
