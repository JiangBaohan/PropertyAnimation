package com.example.hb.valueanimationdemo;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * valueanimation实现动画
 * 无需设置操作的属性,这就是和ObjetAnimator的区别
 * 好处:不需要操作对象的属性,一定有getter和setter方法,可根据当前动画的计算值,操作任何属性
 */
public class MainActivity extends AppCompatActivity {

    private ImageView mBlueBall;
    private int mScreenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBlueBall = (ImageView) findViewById(R.id.id_ball);
        //获取屏幕高度
        DisplayMetrics outMetrice = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrice);
        mScreenHeight = outMetrice.heightPixels;
    }

    /**
     * 抛物线
     *
     * @param view
     */
    public void paowuxian(View view) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(3000);
        valueAnimator.setObjectValues(new PointF(0, 0));
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float v, PointF pointF, PointF t1) {
                Log.e("hb", v * 3 + "");
                // x方向200px/s ，则y方向0.5 * 10 * t
                PointF point = new PointF();
                point.x = 200 * v * 3;
                point.y = 0.5f * 200 * (v * 3) * (v * 3);
                return point;
            }
        });
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                mBlueBall.setX(point.x);
                mBlueBall.setY(point.y);

            }
        });
    }

    /**
     * 自由落体
     *
     * @param view
     */
    public void verticalRun(View view) {
      //参数1.X起始位置 参数2. Y轴的位置
        ValueAnimator animator = ValueAnimator.ofFloat(0, mScreenHeight - mBlueBall.getHeight());
        animator.setTarget(mBlueBall);
        animator.setDuration(1000).start();
        //添加动画更新的监听
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mBlueBall.setTranslationY((Float) valueAnimator.getAnimatedValue());
            }
        });
    }
}
