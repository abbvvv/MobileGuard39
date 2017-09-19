package cn.edc.gdmec.android.mobileguard.m1home.utils;

import android.app.Activity;

import cn.edc.gdmec.android.mobileguard.m1home.entity.VersionEntity;

/**
 * Created by student on 17/9/12.
 */

public class VersionUpdateUtils {
    private String mVersion;
    private Activity context;
    private VersionEntity versionEntity;

    public VersionUpdateUtils(String mVersion) {
        this.mVersion = mVersion;
        this.context=context;
    }
    public void getCloudVersion(){
        HttpClient httpClient=new DefaultHttpClient();


    }
}
