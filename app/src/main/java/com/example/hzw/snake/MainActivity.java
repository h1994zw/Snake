package com.example.hzw.snake;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/*
* 贪吃蛇程序首先做的是需要绘图
* */
public class MainActivity extends AppCompatActivity {
    int number=10;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            this.update();
            handler.postDelayed(this, 200);// 间隔1 秒
        }
        void update() {
            myview.invalidate();
            //myview.myupdate(number);
            if(number>100)number=10;
            else number++;
            //刷新msg的内容
        }
    };
    View myview=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        myview=new TileView(this);
        setContentView(myview);
        handler.postDelayed(runnable, 200);
    }
    protected void onDestroy() {
        handler.removeCallbacks(runnable); //停止刷新
        super.onDestroy();
    }
}
