package com.example.hzw.snake;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by hzw on 2017/5/6.
 */
public class SnakeView {
    /*private int mMode = READY;
    public static final int PAUSE = 0;  //暂停
    public static final int READY = 1;  //准备好了，预备开始
    public static final int RUNNING = 2;//正在运行
    public static final int LOSE = 3;   //结束,输了游戏

    *//**
     * Current direction the snake is headed.
     * 蛇体运动的方向标识。
     *//*
    private int mDirection = NORTH;
    private int mNextDirection = NORTH;
    private static final int NORTH = 1;
    private static final int SOUTH = 2;
    private static final int EAST = 3;
    private static final int WEST = 4;
    private static final int RED_STAR = 1;
    private static final int YELLOW_STAR = 2;
    private static final int GREEN_STAR = 3;
    private long mScore = 0;   //记录获得的分数。
    private long mMoveDelay = 600;  //每移动一步的延时。初始时设置为600ms，以后每吃一个果子，打个9折
    //造成的结果是速度越来越快。
    private long mLastMove;

    private TextView mStatusText;

    private ArrayList<Coordinate> mSnakeTrail = new ArrayList<Coordinate>();
    private ArrayList<Coordinate> mAppleList = new ArrayList<Coordinate>();

    private static final Random RNG = new Random();

    private RefreshHandler mRedrawHandler = new RefreshHandler();

    class RefreshHandler extends Handler {

        //获取消息并处理
        @Override
        public void handleMessage(Message msg) {
            SnakeView.this.update();
            SnakeView.this.invalidate(); //刷新view为基类的界面
        }

        //定时发送消息给UI线程，以此达到更新的效果。
        public void sleep(long delayMillis) {
            this.removeMessages(0); //清空消息队列，Handler进入对新消息的等待
            sendMessageDelayed(obtainMessage(0), delayMillis); //定时发送新消息,激活handler
        }
    };
    public SnakeView(Context context, AttributeSet attrs) {
        //super(context, attrs);
        //initSnakeView();  //构造函数中，别忘了，初始化游戏～
    }

    public SnakeView(Context context, AttributeSet attrs, int defStyle) {
        //super(context, attrs, defStyle);
        //initSnakeView();
    }
*/
}
