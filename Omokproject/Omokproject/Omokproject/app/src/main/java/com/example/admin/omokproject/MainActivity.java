package com.example.admin.omokproject;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();

    }

    public void onVersusButtonClicked(View v) {
        Toast.makeText(this, "대전모드 입장!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), VersusActivity.class);
        startActivity(intent);
    }

    public void onSettingButtonClicked(View v){
        Toast.makeText(this, "세팅페이지", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),SettingActivity.class);
        startActivity(intent);
    }
}
