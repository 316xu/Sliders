package hust.xujifa.sliders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xujifa on 2015/9/27.
 */
public class SliderLayout extends ViewGroup {
    Line line;
    Circle circle;
    public SliderLayout(Context context) {
        super(context);
        init();
    }



    public SliderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    void init(){
        line=new Line(getContext());
        circle=new Circle(getContext());
        addView(line);
        addView(circle);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("AAA","w"+w+"h"+h+"ow"+oldw+"oh"+oldh);

        float xpad = (float) (getPaddingLeft() + getPaddingRight());
        float ypad = (float) (getPaddingTop() + getPaddingBottom());

        // Account for the label

        float ww = (float) w - xpad;
        float hh = (float) h - ypad;

        // Figure out how big we can make the pie.
        float diameter = Math.min(ww, hh);

        int center=(int)(hh/2.0);
        circle.layout(0, 0, (int) ww, (int) hh);
        line.layout(0, 0, (int) ww, (int) hh);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("AAA",event.toString());
        if(event.getAction()==MotionEvent.ACTION_MOVE||event.getAction()==MotionEvent.ACTION_UP||
                event.getAction()==MotionEvent.ACTION_DOWN
                ){
            moveto(event.getX());

            return true;
        }
        return false;
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    void moveto(float x){
        line.moveto(x);
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
            paint2.setColor(getResources().getColor(R.color.blue));
            paint1.setStyle(Paint.Style.FILL);
            paint2.setStyle(Paint.Style.FILL);
            x=getLeft()+30;
        }

        public void moveto(float x){
            this.x=x;
            invalidate();
        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Log.d("AAA", "line" + x);
            if(x<=getLeft()+30)x=getLeft()+30;
            if(x>getRight()-10)x=getRight()-10;
            canvas.drawRect(getLeft() + 30, (getTop()+getBottom())/2-3, x,(getTop()+getBottom())/2+3, paint2);
            canvas.drawRect(x, (getTop()+getBottom())/2-3, getRight() - 30,(getTop()+getBottom())/2+3, paint1);
        }

    }

    class Circle extends View {
        Paint paint;
        float x;
        public void moveto(float x){
            this.x=x;
            invalidate();
        }

        public Circle(Context context) {
            super(context);
            paint=new Paint();
            paint.setColor(getResources().getColor(R.color.blue));
            paint.setStyle(Paint.Style.FILL);
            x=getLeft()+20;
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if(x>getRight()-20)x=getRight()-20;
            else if(x<=getLeft()+20) {
                x = getLeft() + 20;
                paint.setColor(getResources().getColor(R.color.gray));
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(5);
            }else{
                paint.setColor(getResources().getColor(R.color.blue));
                paint.setStyle(Paint.Style.FILL);

            }
            canvas.drawCircle(x, (getTop()+getBottom())/2 , 15, paint);
        }

    }
}
