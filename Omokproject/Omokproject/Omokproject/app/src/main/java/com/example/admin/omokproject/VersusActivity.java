package com.example.admin.omokproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class VersusActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Model model = new Model();
        Board board = new Board(this,model);
        board.setOnTouchListener(board);
        setContentView(R.layout.activity_versusmode);
        FrameLayout blo = (FrameLayout)findViewById(R.id.boardlayout);
        blo.addView(board);
    }
    public void onBackPressed(){
        //super.onBackPressed();
        Toast.makeText(this, "뒤로가기", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder dial = new AlertDialog.Builder(this);
        dial.setTitle("게임 종료");
        dial.setMessage("게임을 종료하고 메인메뉴로 돌아가시겠습니까?");
        dial.setCancelable(false);
        dial.setPositiveButton("확인",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int WhichButton) {
                finish();
            }
        });
        dial.setNegativeButton("취소",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int WhichButton) {
                dialog.cancel();
            }
        });
        dial.show();
    }

    public void onSurrenderButtonClicked(View v){
        AlertDialog.Builder dial = new AlertDialog.Builder(this);
        dial.setTitle("항복 선언");
        dial.setMessage("패배를 인정하시겠습니까??");
        dial.setCancelable(false);
        dial.setPositiveButton("확인",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int WhichButton) {
                finish();
            }
        });
        dial.setNegativeButton("취소",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int WhichButton) {
                dialog.cancel();
            }
        });
        dial.show();
    }




}
