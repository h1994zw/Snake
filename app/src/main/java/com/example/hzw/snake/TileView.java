package com.example.hzw.snake;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.graphics.Point;
import android.view.View;

import java.util.Random;

/**
 * Created by hzw on 2017/5/6.
 */
public class TileView extends View {
    int length = 4;
    //private static final Random RNG = new Random();
    private static int mXOffset = 60; //原点坐标，按pixel计。
    private static int mYOffset = 60;
    private int[][] mTileGrid = new int[45][45];//标识是否占用47*47
    Point[] snake = new Point[200];

    Point food = new Point(0, 0);
    boolean exitOfFood = false;
    boolean exitOfSnake = false;
    boolean alive = true;
    int direction = 0;//0左 1上 2 右 3 下

    public TileView(Context context) {
        super(context);
        init();
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        canvas.translate(mXOffset, mYOffset);
        Paint paint = new Paint();
        if (alive) {
            drawWall(canvas, paint);
            drawSnake();//更新蛇身
            drawFood(canvas, paint);//更新食物
            drawPa(canvas, paint);
            adjustDirection();
            moveSnake();
            stillAlive();
        } else {
            drawFalied(canvas, paint);
        }

    }

    void init() {
        for (int i = 0; i < 45; i++) {//初始化坐标
            for (int j = 0; j < 45; j++) {
                mTileGrid[i][j] = 0;
            }
        }
    }

    void drawSnake() {
        if (!exitOfSnake) {
            for (int i = 0; i < 200; i++) snake[i] = new Point(0, 0);
            int temp = (int) (Math.random() * 30) + 5;
            snake[0].set(temp, temp);
            for (int i = 1; i < 200; i++) {
                if (i < length)
                    snake[i].set(temp + i, temp);
                else snake[i].set(0, 0);
            }
            exitOfSnake = true;
        }
        drawSnakePos();
    }

    void drawSnakePos() {
        mTileGrid[snake[0].x][snake[0].y] = 2;
        for (int i = 1; i < length; i++) {
            mTileGrid[snake[i].x][snake[i].y] = 1;
        }
    }

    public void myupdate(int number) {
        length = number;
        invalidate();
    }

    void drawPa(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < 45; i++) {
            for (int j = 0; j < 45; j++) {
                if (mTileGrid[i][j] == 1)
                    canvas.drawPoint(i * 20, j * 20, paint);
            }
        }
        paint.setColor(Color.GREEN);
        canvas.drawPoint(snake[0].x * 20, snake[0].y * 20, paint);

    }


    //draw wall
    void drawWall(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, 0, 940, 940, paint);//0-47 47*47
    }

    void drawFood(Canvas canvas, Paint paint) {
        int tempX, tempY;
        if (!exitOfFood) {
            while (true) {//随机生成食物  1-46
                tempX = (int) (Math.random() * 44)+1;
                tempY = (int) (Math.random() * 44)+1;
                if (mTileGrid[tempX][tempY] == 0)
                    break;
            }
            food.set(tempX, tempY);
            paint.setColor(Color.RED);
            paint.setStrokeWidth(20);
            paint.setStyle(Paint.Style.STROKE);//60,60-70,70
            canvas.drawPoint(tempX * 20, tempY * 20, paint);
            exitOfFood = true;
        } else {
            paint.setColor(Color.RED);
            paint.setStrokeWidth(20);
            paint.setStyle(Paint.Style.STROKE);//60,60-70,70
            canvas.drawPoint(food.x * 20, food.y * 20, paint);
        }

    }

    public void setTile(int tileindex, int x, int y) {
        mTileGrid[x][y] = tileindex;
    }

    void moveSnake() {//蛇移动
        //0左 1上 2 右 3 下
        //先判断方向
        int tempX = 0, tempY = 0;
        if (direction == 0)//0-1-2-3  q-0-1-2
        {
            tempX = snake[0].x - 1;
            tempY = snake[0].y;
        } else if (direction == 1) {
            tempX = snake[0].x;
            tempY = snake[0].y - 1;
        } else if (direction == 2) {
            tempX = snake[0].x + 1;
            tempY = snake[0].y;
        } else if (direction == 3) {
            tempX = snake[0].x;
            tempY = snake[0].y + 1;
        }
        eatFood(tempX, tempY);
        if (exitOfSnake) {
            for (int i = length - 1; i > 0; i--) {
                snake[i].set(snake[i - 1].x, snake[i - 1].y);
            }
            snake[0].set(tempX, tempY);
            /*if (direction == 0)//0-1-2-3  q-0-1-2
                snake[0].set(snake[0].x - 1, snake[0].y);
            else if (direction == 1)
                snake[0].set(snake[0].x, snake[0].y - 1);
            else if (direction == 2)
                snake[0].set(snake[0].x + 1, snake[0].y);
            else if (direction == 3) snake[0].set(snake[0].x - 1, snake[0].y + 1);*/
        }
    }

    void adjustDirection() {
        //用平方和算
        //先判断下一步会不会死 在选择分数
        int tempX = food.x - snake[0].x;//>0 向右
        int tempY = food.y - snake[0].y;//>0 向上
        int valueX = 0, valueY = 0,valueZ=0;
        //Point up=new Point(snake[0].x-food.x,snake[0].y-1-food.y);
/*        Point down=new Point(snake[0].x-food.x,snake[0].y+1-food.y);
        Point left=new Point(snake[0].x-1-food.x,snake[0].y-food.y);
        Point right=new Point(snake[0].x+1-food.x,snake[0].y-food.y);*/
        if (direction == 0) {
            //先判断是不是在一条直线上
            if (tempX <= 0 && tempY == 0) return;//在正对面
            if (tempX > 0 && tempY == 0) {//屁股后面
                if(judgealive(1)!=0)
                {
                    direction=1;
                    return;
                }
                if(judgealive(3)!=0){
                    direction=3;
                    return;
                }
            }
            valueX=judgealive(0);
            valueY=judgealive(1);
            valueZ=judgealive(3);
            if(valueX<valueY&&valueX<valueZ){
                direction=0;
                return;
            }else if(valueY<valueX&&valueY<valueZ){
                direction=1;
                return;
            }else {
                direction=3;
                return;
            }
        } else if (direction == 1) {
            if(tempY<=0&&tempX==0)return;
            if(tempY>0&&tempX==0){
                if(judgealive(0)!=0){
                    direction=0;
                    return;
                }
                if(judgealive(2)!=0)
                {
                    direction=2;
                    return;
                }
            }
            valueX=judgealive(1);
            valueY=judgealive(0);
            valueZ=judgealive(2);
            if(valueX<valueY&&valueX<valueZ){
                direction=1;
                return;
            }else if(valueY<valueX&&valueY<valueZ){
                direction=0;
                return;
            }else {
                direction=2;
                return;
            }
        } else if (direction == 2) {
            if(tempX>=0&&tempY==0)return;
            if(tempX<0&&tempY==0)
            {
                if(judgealive(1)!=0)
                {
                    direction=1;
                    return;
                }
                if(judgealive(3)!=0){
                    direction=3;
                    return;
                }
            }
            valueX=judgealive(2);
            valueY=judgealive(1);
            valueZ=judgealive(3);
            if(valueX<valueY&&valueX<valueZ){
                direction=2;
                return;
            }else if(valueY<valueX&&valueY<valueZ){
                direction=1;
                return;
            }else {
                direction=3;
                return;
            }
        } else if (direction == 3) {
            if(tempY>=0&&tempX==0)return;
            if(tempY<0&&tempX==0){
                if(judgealive(0)!=0){
                    direction=0;
                    return;
                }
                if(judgealive(2)!=0)
                {
                    direction=2;
                    return;
                }
            }
            valueX=judgealive(3);
            valueY=judgealive(0);
            valueZ=judgealive(2);
            if(valueX<valueY&&valueX<valueZ){
                direction=3;
                return;
            }else if(valueY<valueX&&valueY<valueZ){
                direction=0;
                return;
            }else {
                direction=2;
                return;
            }
        }

    }
    int judgealive(int dire)//dir为接下来的方向
    {
        int tempX = 0, tempY = 0;
        if (dire == 0)//0-1-2-3  q-0-1-2
        {
            tempX = snake[0].x - 1;
            tempY = snake[0].y;
        } else if (dire == 1) {
            tempX = snake[0].x;
            tempY = snake[0].y - 1;
        } else if (dire == 2) {
            tempX = snake[0].x + 1;
            tempY = snake[0].y;
        } else if (dire == 3) {
            tempX = snake[0].x;
            tempY = snake[0].y + 1;
        }
        if(tempX <= 0 || tempY <= 0 || tempX >= 47 || tempY >= 47)
        {
            return 0;
        }
        for(int i=1;i<length-1;i++)
        {
            if(tempX==snake[i].x&&tempY==snake[i].y) {
                return 0;
            }
        }
        return (tempX-food.x)*(tempX-food.x)+(tempY-food.y)*(tempY-food.y)-((snake[0].x-food.x)*(snake[0].x-food.x)+(snake[0].y-food.y)*(snake[0].y-food.y));
        //return true;
    }
    void stillAlive() {
        if (snake[0].x <= 0 || snake[0].y <= 0 || snake[0].x >= 47 || snake[0].y >= 47)//碰墙
        {
            alive = false;
        }
        for (int i = 2; i < length; i++)
            if (snake[0].x == snake[i].x && snake[0].y == snake[i].y)
                alive = false;
        //alive=true;
    }

    void eatFood(int tempX, int tempY) {
        if (tempX == food.x && tempY == food.y) {
            length++;
            exitOfFood = false;
        }
    }

    void drawFalied(Canvas canvas, Paint paint) {
        paint.setColor(Color.RED);
        paint.setTextSize(40);//设置字体大小
        canvas.drawText("死亡退出！", 500, 500, paint);
    }

}
