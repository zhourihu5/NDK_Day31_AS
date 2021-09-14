package com.darren.ocr;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_CODE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private ImageView mCardIv;
    private Bitmap mCardBitmap;
    private TextView mCardNumberTv;
    ImageView ivCardTour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCardIv = findViewById(R.id.card_iv);
        mCardNumberTv = findViewById(R.id.card_number_tv);
        ivCardTour= findViewById(R.id.card_tour);
        mCardBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.card_n);
        mCardIv.setImageBitmap(mCardBitmap);
//        requestPermision();
    }
    public void requestPermision() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
                return;
            }
            else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
                return;
            }
        }
    }
    volatile boolean granted=false;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            granted=true;
            for (int i = 0; i < permissions.length; i++) {
                Log.i("MainActivity", "申请的权限为：" + permissions[i] + ",申请结果：" +
                        grantResults[i]);
                if(grantResults[i]!= PackageManager.PERMISSION_GRANTED){
                    granted=false;
                    break;
                }
            }
            if(granted){
            }else{
                Toast.makeText(this,"不给权限无法使用哦！",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void cardOcr(View view) {
        String path=new File(getExternalCacheDir(),"card_n.jpg").getAbsolutePath();
        String bankNumber = BankCardOcr.cardOcr(mCardBitmap,path);
        mCardNumberTv.setText(bankNumber);
        ivCardTour.setImageBitmap(BitmapFactory.decodeFile(path));
    }
}
