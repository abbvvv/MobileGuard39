package cn.edu.gdmec.android.mobileguard.m4appmanager.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.android.mobileguard.m4appmanager.entity.AppInfo;

/**
 * Created by Administrator on 2017/11/7.
 */

public class AppInfoParser {
    public static List<AppInfo> getAppInfos(Context context){
        PackageManager pm=context.getPackageManager();
        List<PackageInfo> packageInfos=pm.getInstalledPackages(0);
        List<AppInfo> appinfos=new ArrayList<AppInfo>();
        for(PackageInfo packageInfo:packageInfos){
            AppInfo appinfo=new AppInfo();
            String packname=packageInfo.packageName;
            appinfo.packageName=packname;
            Drawable icon=packageInfo.applicationInfo.loadIcon(pm);
            appinfo.icon=icon;
            String appname=packageInfo.applicationInfo.loadLabel(pm).toString();
            appinfo.appName=appname;
            String apkpath=packageInfo.applicationInfo.sourceDir;
            appinfo.apkPath=apkpath;
            File file=new File(apkpath);
            long appSize=file.length();
            appinfo.appSize=appSize;
            int flags=packageInfo.applicationInfo.flags;
            if((ApplicationInfo.FLAG_EXTERNAL_STORAGE & flags)!=0){
                appinfo.isInRoom=false;
            }else{
                appinfo.isInRoom=true;
            }
            if((ApplicationInfo.FLAG_SYSTEM & flags)!=0){
                appinfo.isUserApp=false;
            }else{
                appinfo.isUserApp=true;
            }
            appinfos.add(appinfo);
            appinfo=null;
        }
        return appinfos;
    }
}
