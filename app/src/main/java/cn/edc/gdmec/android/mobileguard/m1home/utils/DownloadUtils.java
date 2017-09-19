package cn.edc.gdmec.android.mobileguard.m1home.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;

/**
 * Created by student on 17/9/12.
 */

public class DownloadUtils {
    public void downApk(String url,String targetFile,Context context){
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        String mimetypeString=mimeTypeMap.getExtensionFromMimeType(mimeTypeMap.getFileExtensionFromUrl(""));


    }

}
