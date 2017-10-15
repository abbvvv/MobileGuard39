package cn.edu.gdmec.android.mobileguard.m1home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m1home.adapter.HomeAdapter;
import cn.edu.gdmec.android.mobileguard.m2theftguard.LostFindActivity;
import cn.edu.gdmec.android.mobileguard.m2theftguard.dialog.InterPasswordDialog;
import cn.edu.gdmec.android.mobileguard.m2theftguard.dialog.SetupPasswordDialog;
import cn.edu.gdmec.android.mobileguard.m2theftguard.utils.MD5Utils;

/**
 * Created by student on 17/9/19.
 */

public class HomeActivity extends AppCompatActivity{
    private GridView gv_home;
    private long mExitTime;
    private SharedPreferences msharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        msharedPreferences=getSharedPreferences("config",MODE_PRIVATE);
        gv_home=(GridView)findViewById(R.id.gv_home);
        gv_home.setAdapter(new HomeAdapter(HomeActivity.this));
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                System.out.print(i);
                switch (i){
                    case 0:
                        if(isSetUpPassword()){
                            showInterPswdDislog();
                        }else{
                            showSetUpPswdDialog();
                        }
                        break;

                }
            }
        });


    }
    public void startActivity(Class<?> cls){
        Intent intent=new Intent(HomeActivity.this,cls);
        startActivity(intent);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if((System.currentTimeMillis()-mExitTime)<2000){

                System.exit(0);
            }else{
                Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_LONG).show();
                mExitTime=System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void showSetUpPswdDialog(){
        final SetupPasswordDialog setUpPasswordDialog=new SetupPasswordDialog(HomeActivity.this);
        setUpPasswordDialog.setCallBack(new SetupPasswordDialog.MyCallBack(){

            @Override
            public void ok() {
                String firstPwsd=setUpPasswordDialog.mFirstPWDET.getText().toString().trim();
                String affirmPwsd=setUpPasswordDialog.mAffirmET.getText().toString().trim();
                if (!TextUtils.isEmpty(firstPwsd)&&!TextUtils.isEmpty(affirmPwsd)){
                    if(firstPwsd.equals(affirmPwsd)){
                        savePwsd(affirmPwsd);
                        setUpPasswordDialog.dismiss();
                    }else{
                        Toast.makeText(HomeActivity.this,"两次密码不一致！",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(HomeActivity.this,"密码不能为空！",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void cancel() {
                setUpPasswordDialog.dismiss();
            }




        });
        setUpPasswordDialog.setCancelable(true);
        setUpPasswordDialog.show();
    }
    private void showInterPswdDislog(){
        final String password=getPassword();
        final InterPasswordDialog mInPswdDialog= new InterPasswordDialog(HomeActivity.this);
        mInPswdDialog.setCallBack(new InterPasswordDialog.MyCallBack() {
            @Override
            public void confirm() {
                if(TextUtils.isEmpty(mInPswdDialog.getPassword())){
                    Toast.makeText(HomeActivity.this,"密码不能为空！",0).show();
                }else if(password.equals(MD5Utils.encode(mInPswdDialog.getPassword()))){
                    mInPswdDialog.dismiss();
                    startActivity(LostFindActivity.class);
                    Toast.makeText(HomeActivity.this,"可以进入手机防盗模式",Toast.LENGTH_LONG).show();
                }else{
                    mInPswdDialog.dismiss();
                    Toast.makeText(HomeActivity.this,"密码有误，请重新输入！",0).show();
                }
            }

            @Override
            public void cancle() {
                mInPswdDialog.dismiss();
            }
        });
        mInPswdDialog.setCancelable(true);
        mInPswdDialog.show();

    }
    private void savePwsd(String affirmPwsd){
        SharedPreferences.Editor edit=msharedPreferences.edit();
        edit.putString("PhoneAntiTheftPWD",MD5Utils.encode(affirmPwsd));
        edit.commit();
    }

    private String getPassword() {
        String password=msharedPreferences.getString("PhoneAntiTheftPWD",null);
        if(TextUtils.isEmpty(password)){
            return "";
        }
        return password;
    }
    private boolean isSetUpPassword(){
        String password=msharedPreferences.getString("PhoneAntiTheftPWD",null);
        if(TextUtils.isEmpty(password)){
            return false;
        }
        return true;
    }
}
