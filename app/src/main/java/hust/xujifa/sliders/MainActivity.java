package hust.xujifa.sliders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private CircleMaskView v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v=(CircleMaskView )findViewById(R.id.main_circle_mask_view);
    }


    protected void onResume(){
        super.onResume();
        BitmapFactory.Options opts=new BitmapFactory.Options();
        opts.inPreferredConfig= Bitmap.Config.RGB_565;
        Bitmap b= BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, opts);
        v.setBitmap(b);
        v.startAnimation(200);
    }
}
