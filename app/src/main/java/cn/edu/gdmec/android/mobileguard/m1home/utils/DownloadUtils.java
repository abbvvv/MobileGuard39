package cn.edu.gdmec.android.mobileguard.m1home.utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;

/**
 * Created by student on 17/9/12.
 */

public class DownloadUtils {
    public void downapk(String url,String targetFile,Context context){
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));
        request.setAllowedOverRoaming(false);
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        String mimeString=mimeTypeMap.getExtensionFromMimeType(mimeTypeMap.getFileExtensionFromUrl(url));
        request.setMimeType(mimeString);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setVisibleInDownloadsUi(true);

        request.setDestinationInExternalPublicDir("/download",targetFile);
        DownloadManager downloadManager=(DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE  );
        long mTaskid=downloadManager.enqueue(request);



    }



}