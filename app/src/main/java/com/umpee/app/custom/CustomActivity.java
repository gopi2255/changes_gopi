package com.umpee.app.custom;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.umpee.app.R;
import com.umpee.app.cache.Cache;
import com.umpee.app.model.ModelUser;
import com.yanzhenjie.loading.dialog.LoadingDialog;


public class CustomActivity extends AppCompatActivity {

    public CustomActivity mContext;
    private LoadingDialog mDialog;
    private Intent mService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int newUiVisibility =  getWindow().getDecorView().getSystemUiVisibility();
            newUiVisibility |=  View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            getWindow().getDecorView().setSystemUiVisibility(newUiVisibility);
            getWindow().setStatusBarColor(getColor(R.color.colorPrimaryDark));
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.rgb(160, 160, 160));
            }
        }
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    @Override
    public void setContentView(int id) {
        super.setContentView(id);
    }

    protected void setupActionbar(String title, boolean hasBack){
        TextView textTitle = (TextView) findViewById(R.id.textTitle);
        textTitle.setText(title);
        if (hasBack){
            ImageButton buttonBack = (ImageButton)findViewById(R.id.buttonBack);
            buttonBack.setVisibility(View.VISIBLE);
            buttonBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    public void restartActivity(Class<?> cls) {
        finish();
        Intent intent = new Intent(this, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void restartActivity(Intent intent) {
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public void makeToast(String string) {
        if (TextUtils.isEmpty(string)) return;
        Toast.makeText(mContext, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    private void showProgress() {
        if (mDialog == null)
            mDialog = new LoadingDialog(this);
        if (!mDialog.isShowing()) mDialog.show();
    }

    private void hideProgress() {
        if (mDialog != null && mDialog.isShowing()) mDialog.dismiss();
    }

    public ModelUser getUser(){
        return Cache.getUser();
    }
}
