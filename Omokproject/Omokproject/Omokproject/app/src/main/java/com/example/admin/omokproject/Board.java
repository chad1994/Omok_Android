package com.example.admin.omokproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by admin on 2016-11-13.
 */

public class Board extends View implements View.OnTouchListener {
    Context context;
    Model model = new Model();
    //TimeThread thread;
    private Bitmap bgimage,playerbar_W,playerbar_B,whiteplayer,blackplayer;
    private Bitmap whitestoneimage, blackstoneimage,reverseitemicon,timeitemicon;
    private Bitmap whitewinimage,blackwinimage;
    private Bitmap timeitemimage,surrenderimage,reverseimage;


    public Board(Context context, Model model) {
        super(context);
        this.context=context;
        this.model = model;
        model.initmap();//맵 배열 초기화 (0값)
        model.loadsound();
        //setBackgroundColor(Color.WHITE);

        Resources r = context.getResources();
        bgimage = BitmapFactory.decodeResource(r, R.drawable.badukpan);
        bgimage = Bitmap.createScaledBitmap(bgimage,1300,1300,true);
        whitestoneimage = BitmapFactory.decodeResource(r, R.drawable.whitestone);
        blackstoneimage = BitmapFactory.decodeResource(r, R.drawable.blackstone);
        whitewinimage =BitmapFactory.decodeResource(r,R.drawable.whitewin);
        blackwinimage = BitmapFactory.decodeResource(r,R.drawable.blackwin);
        whitestoneimage = Bitmap.createScaledBitmap(whitestoneimage, 90, 90, false);
        blackstoneimage = Bitmap.createScaledBitmap(blackstoneimage, 90, 90, false);
        whitewinimage = Bitmap.createScaledBitmap(whitewinimage,1000,1000,false);
        blackwinimage = Bitmap.createScaledBitmap(blackwinimage,1000,1000,false);
        reverseimage=BitmapFactory.decodeResource(r,R.drawable.reverseimage);
        reverseimage=Bitmap.createScaledBitmap(reverseimage,300,100,false);
        timeitemimage=BitmapFactory.decodeResource(r,R.drawable.timeitemimage);
        timeitemimage=Bitmap.createScaledBitmap(timeitemimage,300,100,false);
        surrenderimage=BitmapFactory.decodeResource(r,R.drawable.surrenderimage);
        surrenderimage=Bitmap.createScaledBitmap(surrenderimage,300,100,false);
        playerbar_B = BitmapFactory.decodeResource(r,R.drawable.playerbar_black);
        playerbar_B=Bitmap.createScaledBitmap(playerbar_B,1400,200,false);
        playerbar_W = BitmapFactory.decodeResource(r,R.drawable.playerbar_white);
        playerbar_W=Bitmap.createScaledBitmap(playerbar_W,1400,200,false);
        whiteplayer = BitmapFactory.decodeResource(r,R.drawable.whiteplayer);
        whiteplayer=Bitmap.createScaledBitmap(whiteplayer,400,150,false);
        blackplayer = BitmapFactory.decodeResource(r,R.drawable.blackplayer);
        blackplayer=Bitmap.createScaledBitmap(blackplayer,400,150,false);
        //image.setWidth();
        timeitemicon = BitmapFactory.decodeResource(r,R.drawable.timeitemicon);
        timeitemicon=Bitmap.createScaledBitmap(timeitemicon,100,100,false);
        reverseitemicon = BitmapFactory.decodeResource(r,R.drawable.reverseitemicon);
        reverseitemicon=Bitmap.createScaledBitmap(reverseitemicon,100,100,false);
    }

    public void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        /////////////////////////////////////////////배둑판 배경그리기
        canvas.drawBitmap(bgimage, 100, 50, null);
        ////////////////////////////////////////////플레이어 바 그리기
        if (model.turn_count % 2 == 1) {
            canvas.drawBitmap(playerbar_W, 50, 1350, null);
        } else if (model.turn_count % 2 == 0) {
            canvas.drawBitmap(playerbar_B, 50, 1350, null);
        }
        paint.setTextSize(60);
        canvas.drawText(""+model.W_playername, 120, 1470, paint);
        canvas.drawText(""+model.B_playername, 1150, 1470, paint);

        canvas.drawBitmap(whitestoneimage, 330, 1410, null);
        canvas.drawBitmap(blackstoneimage, 1050, 1410, null);
        if (model.getW_turnitem() == 1) {
            canvas.drawBitmap(reverseitemicon, 500, 1400, null);
        }
        if (model.getB_turnitem() == 1) {
            canvas.drawBitmap(reverseitemicon, 900, 1400, null);
        }
        if (model.getW_timeitem() == 2) {
            canvas.drawBitmap(timeitemicon, 600, 1400, null);
        }
        if (model.getB_timeitem() == 2) {
            canvas.drawBitmap(timeitemicon, 800, 1400, null);
        }
        //canvas.drawText(String.valueOf(bgimage.getWidth()), 50, 50, paint);
        //canvas.drawText(String.valueOf(bgimage.getHeight()), 50, 70, paint);
        paint.setColor(Color.BLACK);
        //canvas.drawCircle(0, 0, 10, paint);
        //canvas.drawRect(10, 20, 30, 40, paint);
        //canvas.drawLine(150,100,1350,100,paint);

        ///////////////////////////////////////////////바둑판 선 그리기
        paint.setStrokeWidth(3);//선 굵기
        for (int i = 0; i < 13; i++) {
            canvas.drawLine(150, (i + 1) * 100, 1350, (i + 1) * 100, paint);
            canvas.drawLine(((i + 1) * 100) + 50, 100, ((i + 1) * 100) + 50, 1300, paint);
            //바둑판 시작x점 150,시작y점 100 , 바둑판(노란색) 크기 1200*1200 , 가로세로 12*12
        }

        //////////////////////////////////////////////화점찍기
        canvas.drawCircle(750, 700, 10, paint);
        canvas.drawCircle(450, 400, 10, paint);
        canvas.drawCircle(1050, 400, 10, paint);
        canvas.drawCircle(450, 1000, 10, paint);
        canvas.drawCircle(1050, 1000, 10, paint);

        /////////////////////////////////////////////돌 그리기
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                if (model.getMap(i, j) == 1) {
                    //canvas.drawCircle(i*100+150,j*100+100,50,paint); //배열값에 따라 돌그려넣기
                    canvas.drawBitmap(whitestoneimage, i * 100 + 105, j * 100 + 55, paint);
                } else if (model.getMap(i, j) == 2) {
                    canvas.drawBitmap(blackstoneimage, i * 100 + 105, j * 100 + 55, paint);
                }
            }
        }
        ////////////////////////////////////////////최근 돌 표식찍기
        paint.setColor(Color.RED);
        if (model.turn_count != 1) { //시작을 제외하고
            if (model.turn_count % 2 == 1) //검은돌을 놓고 흰색턴이 되었을 때
                canvas.drawCircle(model.getBlack_X() * 100 + 150, model.getBlack_Y() * 100 + 100, 10, paint);
            else if (model.turn_count % 2 == 0) { //흰돌을 놓고 검은색턴이 되었을 때
                canvas.drawCircle(model.getWhite_X() * 100 + 150, model.getWhite_Y() * 100 + 100, 10, paint);
            }
        }
        ////////////////////////////////////////////승리 도장
        if (model.whitewinstate == true) {
            canvas.drawBitmap(whitewinimage, 250, 200, paint);
            model.playwinsound();
        }
        if (model.blackwinstate == true){
            canvas.drawBitmap(blackwinimage, 250, 200, paint);
            model.playwinsound();
        }


        ///////////////////////////////////////////아이템 그리기
        canvas.drawBitmap(reverseimage,225,1600,paint);
        //canvas.drawBitmap(surrenderimage,600,1600,paint);
        canvas.drawBitmap(timeitemimage,975,1600,paint);
        //////////////////////////////////////////남은 시간 그리기
        if(model.getW_timeitem()==1&&model.turn_count%2==0)
            canvas.drawText("흑돌 남은 시간:"+model.B_time,600,50,paint);
        if(model.getB_timeitem()==1&&model.turn_count%2==1)
            canvas.drawText("백돌 남은 시간:"+model.W_time,600,50,paint);
        if(model.getW_timeitem()==1&&model.turn_count%2==1)
            canvas.drawText("백돌 시간제한 아이템 사용",500,50,paint);
        if(model.getB_timeitem()==1&&model.turn_count%2==0)
            canvas.drawText("흑돌 시간제한 아이템 사용",500,50,paint);

        //fragment 해보기! 11.17
        super.onDraw(canvas);
    }

    public boolean onTouch(View v, MotionEvent event) {

        float X = event.getX();
        float Y = event.getY();
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                if ((X > (100 + i * 100) && X < 100 + (i + 1) * 100) && (Y > (50 + j * 100) && Y < (50 + (j + 1) * 100))) {   // 좌표임계 범위에 배열값에 값넣기
                    if (model.turn_count % 2 == 1) { //백돌의 차례일 때
                        if (model.getMap(i, j) == 0&&model.blackwinstate==false&&model.whitewinstate==false) { //빈칸이고 상대가 승리하지 않았을때
                            model.setMap(1, i, j);
                            model.setWhite_X(i);
                            model.setWhite_Y(j);
                            model.playstonesound();
                            model.turn_count++;
                            if(model.getW_timeitem()==1){
                                TimeThread th = new TimeThread(model,this);
                                model.threadstate=true;
                                th.start();
                            }
                            if(model.getB_timeitem()==1){
                                model.threadstate=false;
                                model.W_time=10;
                                model.setB_timeitem(0);
                            }
                            invalidate();

                        }
                    } else if (model.turn_count % 2 == 0) {
                        if (model.getMap(i, j) == 0&&model.whitewinstate==false&&model.blackwinstate==false) {
                            model.setMap(2, i, j);
                            model.setBlack_X(i);
                            model.setBlack_Y(j);
                            model.playstonesound();
                            model.turn_count++;
                            if(model.getB_timeitem()==1){
                                TimeThread th = new TimeThread(model,this);
                                model.threadstate=true;
                                th.start();
                            }
                            if(model.getW_timeitem()==1){
                                model.threadstate=false;
                                model.B_time=10;
                                model.setW_timeitem(0);
                            }
                            invalidate();


                        }
                    }
                }
            }
        }
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                if (model.white_widthcheck(i, j)==true||model.white_heightcheck(i,j)==true||model.white_leftdiagcheck(i,j)==true||model.white_rightdiagcheck(i,j)==true) {
                    model.whitewinstate = true;
                    invalidate();
                }
                if (model.black_widthcheck(i, j)==true||model.black_heightcheck(i,j)==true||model.black_leftdiagcheck(i,j)==true||model.black_rightdiagcheck(i,j)==true) {
                    model.blackwinstate = true;
                    invalidate();
                }
            }
        }

        if((X>225&&X<525)&&(Y>1600&&Y<1700)) {
            if(model.whitewinstate==false&&model.blackwinstate==false) {
                model.reverseturn();
                model.playclicksound();
                invalidate();
            }
        }

        if((X>975&&X<1275)&&(Y>1600&&Y<1700)){
            if(model.turn_count%2==1){
                if(model.getW_timeitem()==2&&model.whitewinstate==false&&model.blackwinstate==false){
                    model.setW_timeitem(1);
                    model.playclicksound();
                    invalidate();
                }
            }
            else if(model.turn_count%2==0){
                if(model.getB_timeitem()==2&&model.whitewinstate==false&&model.blackwinstate==false){
                    model.setB_timeitem(1);
                    model.playclicksound();
                    invalidate();
                }
            }

        }



        return false;
    }

}