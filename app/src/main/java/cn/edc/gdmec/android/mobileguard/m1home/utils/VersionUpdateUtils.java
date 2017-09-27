package cn.edc.gdmec.android.mobileguard.m1home.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.Experimental;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import cn.edc.gdmec.android.mobileguard.R;
import cn.edc.gdmec.android.mobileguard.m1home.HomeActivity;
import cn.edc.gdmec.android.mobileguard.m1home.entity.VersionEntity;

/**
 * Created by student on 17/9/12.
 */

public class VersionUpdateUtils {


    private static final int MESSAGE_NET_ERROR=101;
    private static final int MESSAGE_IO_ERROR=102;
    private static final int MESSAGE_JSON_ERROR=103;
    private static final int MESSAGE_SHOW_DIALOG=104;
    private static final int MESSAGE_ENTERHOME=105;

 private android.os.Handler handler=new android.os.Handler(){
     @Override
     public void handleMessage(Message msg) {
         switch (msg.what) {
             case MESSAGE_IO_ERROR:
                 Toast.makeText(context, "IO错误", Toast.LENGTH_SHORT).show();
                 break;
             case MESSAGE_JSON_ERROR:
                 Toast.makeText(context, "JSON解析错误", Toast.LENGTH_SHORT).show();
                 break;
             case MESSAGE_NET_ERROR:
                 Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
                 enterHome();
                 break;
             case MESSAGE_SHOW_DIALOG:
                 showUpdateDialog(versionEntity);
                 break;
             case MESSAGE_ENTERHOME:
                 Intent intent = new Intent(context, HomeActivity.class);
                 context.startActivity(intent);
                 context.finish();
                 break;
         }
     }
 };

    private String mVersion;
    private Activity context;
    private VersionEntity versionEntity;
    private ProgressDialog mProgressDialog;
    public VersionUpdateUtils(String mVersion,Activity context) {
        this.mVersion = mVersion;
        this.context=context;
    }
    public void getCloudVersion(){

        try {
            HttpClient httpClient=new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),5000);
            HttpConnectionParams.setSoTimeout(httpClient.getParams(),5000);
            HttpGet httpGet=new HttpGet("http://android2017.duapp.com/updateinfo.html");
            HttpResponse execute= httpClient.execute(httpGet);
            if (execute.getStatusLine().getStatusCode()==200){
                HttpEntity httpEntity=execute.getEntity();
                String result= EntityUtils.toString(httpEntity,"utf-8");
                JSONObject jsonObject=new JSONObject(result);
                versionEntity=new VersionEntity();
                String code=jsonObject.getString("code");
                versionEntity.versionCode=code;
                String des=jsonObject.getString("des");
                versionEntity.description=des;
                String apkurl=jsonObject.getString("apkurl");
                versionEntity.apkurl=apkurl;
                if(!mVersion.equals(versionEntity.versionCode)){
                    /*System.out.print(versionEntity.description);
                    DownloadUtils downloadUtils=new DownloadUtils();
                    downloadUtils.downloadApk(versionEntity.apkurl,"mobileguard.apk",context);*/
                    handler.sendEmptyMessage(MESSAGE_SHOW_DIALOG);
                }
            }
        } catch (IOException e) {
            handler.sendEmptyMessage(MESSAGE_IO_ERROR);
            e.printStackTrace();
        } catch (JSONException e) {
            handler.sendEmptyMessage(MESSAGE_JSON_ERROR);
            e.printStackTrace();
        }


    }
    private void showUpdateDialog(final VersionEntity versionEntity){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("检查到有新版本"+versionEntity.versionCode);
        builder.setMessage(versionEntity.description);
        builder.setCancelable(false);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setPositiveButton("立刻升级",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface,int i){
                downloadNewApk(versionEntity.apkurl);
            }
        });
        builder.setNegativeButton("暂不升级",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface,int i){
                dialogInterface.dismiss();
               enterHome();
            }
        });
        builder.show();
    }
    private void enterHome(){
        handler.sendEmptyMessageDelayed(MESSAGE_ENTERHOME,2000);
    }
    private void downloadNewApk(String apkurl){
        DownloadUtils downloadUtils=new DownloadUtils();
        downloadUtils.downapk(apkurl,"/mnt/sdcard/mobilesafe2.0.spk",context);
    }
}
