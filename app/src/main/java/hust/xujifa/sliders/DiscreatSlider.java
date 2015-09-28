package hust.xujifa.sliders;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.v4.animation.ValueAnimatorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xujifa on 2015/9/27.
 */
public class DiscreatSlider extends ViewGroup {
    Line line;
    Circle circle;
    Bubble bubble;
    public DiscreatSlider(Context context) {
        super(context);
        init();
    }
    public DiscreatSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    void init(){
        line=new Line(getContext());
        circle=new Circle(getContext());
        bubble=new Bubble(getContext());
        addView(line);
        addView(circle);
        addView(bubble);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float xpad = (float) (getPaddingLeft() + getPaddingRight());
        float ypad = (float) (getPaddingTop() + getPaddingBottom());
        float ww = (float) w - xpad;
        float hh = (float) h - ypad;
        circle.layout(0, 0, (int) ww, (int) hh);
        line.layout(0, 0, (int) ww, (int) hh);
        bubble.layout(0, 0, (int) ww, (int) hh);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                circle.scalel();
                circle.startAnimation(200);
                moveto((int)event.getX(), true);
                bubble.startAnim(200);
                return true;
            case MotionEvent.ACTION_UP:
                circle.scales();
                circle.startAnimation(200);
                moveto((int)event.getX(), false);
                bubble.startAnim(200);
                return true;
            case MotionEvent.ACTION_MOVE:
                moveto((int)event.getX(),true);
                return true;
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    void moveto(int x,boolean b){
        line.moveto(x);
        bubble.moveto(x,b);
        circle.moveto(x);
    }
    class Line extends View {
        Paint paint1;
        Paint paint2;
        float x;
        public Line(Context context) {
            super(context);
            paint1=new Paint();
            paint2=new Paint();
            paint1.setColor(getResources().getColor(R.color.gray));
            paint1.setAlpha(80);
            paint2.setAlpha(80);
            paint2.setColor(getResources().getColor(R.color.blue));
            paint1.setStyle(Paint.Style.FILL);
            paint2.setStyle(Paint.Style.FILL);
            x=getLeft()+55;
        }
        public void moveto(float x){
            this.x=x;
            invalidate();
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if(x<=getLeft()+55)x=getLeft()+55;
            if(x>getRight()-55)x=getRight()-55;
            canvas.drawRect(getLeft() + 55, (getTop()+getBottom())/2-3, x,(getTop()+getBottom())/2+3, paint2);
            canvas.drawRect(x, (getTop()+getBottom())/2-3, getRight() - 55,(getTop()+getBottom())/2+3, paint1);
        }
    }
    class Circle extends View implements ValueAnimator.AnimatorUpdateListener{
        Paint paint;
        Paint bg;
        float x;
        int radius;
        int radius2;
        boolean temp;
        ValueAnimator animator;
        public void moveto(float x){
            this.x=x;
            invalidate();
        }
        public Circle(Context context) {
            super(context);
            paint=new Paint();
            paint.setColor(getResources().getColor(R.color.blue));
            bg=new Paint();

            bg.setStyle(Paint.Style.FILL);
            bg.setColor(getResources().getColor(R.color.blue));
            bg.setAlpha(26);

            paint.setStyle(Paint.Style.FILL);
            x=getLeft()+20;
            radius=15;
            radius2=15;
            animator=new ValueAnimator();
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if(x>getRight()-30)x=getRight()-30;
            else if(x<=getLeft()+30) {
                x = getLeft() + 30;
                paint.setColor(getResources().getColor(R.color.gray));
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(5);
                bg.setColor(getResources().getColor(R.color.gray));
                bg.setAlpha(80);
            }else{
                paint.setColor(getResources().getColor(R.color.blue));
                paint.setStyle(Paint.Style.FILL);
                bg.setColor(getResources().getColor(R.color.blue));
                bg.setAlpha(80);

            }
            canvas.drawCircle(x, (getTop()+getBottom())/2 , radius2, bg);

            canvas.drawCircle(x, (getTop()+getBottom())/2 , radius, paint);
        }
        public void scalel() {
            temp=true;
        }
        public void scales() {
            temp=false;
        }
        public void startAnimation(int duration){
            ValueAnimator v=ValueAnimator.ofInt(0,100);
            v.setStartDelay(0);
            v.setDuration(duration);
            v.addUpdateListener(this);
            v.start();
        }
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            int test = (int) animation.getAnimatedValue();
            if(temp){
                radius=15+(15*test)/100;
                radius2=15+(35*test)/100;
            }else{
                radius2=50-(35*test)/100;
                radius=30-(15*test)/100;
            }
            invalidate();
        }
    }
    class Bubble extends View implements ValueAnimator.AnimatorUpdateListener{
        Paint circle;
        Paint triangle;
        Paint text;
        int x;
        Point a,b,c;
        boolean show=false;
        int ay,by,cy,x1;
        float radius;
        int ciry;
        int width;
        public Bubble(Context context) {
            super(context);
            circle=new Paint();
            triangle=new Paint();
            text=new Paint();
            circle.setColor(getResources().getColor(R.color.green));
            triangle.setColor(getResources().getColor(R.color.green));
            text.setColor(getResources().getColor(R.color.white));
            circle.setStyle(Paint.Style.FILL);
            triangle.setStyle(Paint.Style.FILL_AND_STROKE);
            triangle.setStrokeWidth(4);
            triangle.setAntiAlias(true);
             a = new Point();
             b = new Point();
             c = new Point();
            text.setTextSize(50);
        }
        public void moveto(int x, boolean b){
            this.show=b;
            this.x=x;
            if(x>getRight()-30)x=getRight()-30;
            else if(x<=getLeft()+30) {
                x=getLeft()+30;
            }
            a.set(x, ay);
            this.b.set(x+x1,by);
            c.set(x-x1,cy);

            invalidate();
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if(x>getRight()-30)x=getRight()-30;
            else if(x<=getLeft()+30) {
                x=getLeft()+30;
                circle.setColor(getResources().getColor(R.color.gray));
                triangle.setColor(getResources().getColor(R.color.gray));
            }else{
                circle.setColor(getResources().getColor(R.color.green));
                triangle.setColor(getResources().getColor(R.color.green));
            }
            Path path = new Path();
            path.setFillType(Path.FillType.EVEN_ODD);
            path.moveTo(b.x, b.y);
            path.lineTo(c.x, c.y);
            path.lineTo(a.x, a.y);
            path.close();
            canvas.drawPath(path, triangle);
            canvas.drawCircle(x,ciry,radius,circle);
            int l=x-15;
            if(getInt()==10) l=x-30;
            if(ciry==(getTop()+getBottom())/2-105) canvas.drawText(getInt()+"",l,ciry+20,text);

        }
        public int getInt(){
            int a;
            width=getRight()-getLeft()-60;

            a=(int)(((x-getLeft()-30)/(float)width)*10)+1;
            if(x==getLeft()+30)a=0;
            if(a==11)a=10;
            return a;
        }
        public void startAnim(int duration){
            ValueAnimator a=ValueAnimator.ofInt(0,100);
            a.setDuration(duration);
            a.setStartDelay(0);
            a.addUpdateListener(this);
            a.start();
        }
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            int test=(int)animation.getAnimatedValue();

            if(show) {
                ciry=(getTop()+getBottom())/2-105*test/100;
                radius=40*test/100;
                ay=(getTop() + getBottom()) / 2 -40*test/100;
                by=(getTop() + getBottom()) / 2 - 90*test/100;
                cy= (getTop() + getBottom()) / 2 - 90*test/100;
                x1=35*test/100;

            }else{
                

                ciry=(getTop()+getBottom())/2-105+105*test/100;
                radius=40-40*test/100;
                ay=(getTop() + getBottom()) / 2 -40+40*test/100;
                by=(getTop() + getBottom()) / 2 -90+ 90*test/100;
                cy= (getTop() + getBottom()) / 2 -90+ 90*test/100;
                x1=35-35*test/100;
            }
            a.set(x, ay);
            b.set(x+x1,by);
            c.set(x-x1,cy);
            invalidate();
        }
    }




}
