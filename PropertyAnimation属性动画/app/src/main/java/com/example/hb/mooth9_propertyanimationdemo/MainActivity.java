package com.example.hb.mooth9_propertyanimationdemo;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * ObjectAnimator属性动画特点:动画效果会改变控件的位置,且开启动画的是动画对象,而不是控件对象
 * <p>
 * 注意:属性动画是再android3.0以后出现的新特性,所以把清单文件里的最低兼容版本修改为11以上
 * <p>
 * 如果想实现APP的与众不同,就用自定义控件,如果想实现APP的酷炫非凡就用android动画
 * 如果想实现APP的与众不同且酷炫非凡就自定义控件和动画结合使用----------->尼古拉斯(赵四)
 *
 * 监听器
 * ObjectAnimator是继承自ValueAnimator的，而ValueAnimator又是继承自Animator的，
 * 因此不管是ValueAnimator还是ObjectAnimator都是可以使用addListener()这个方法的。
 * 另外AnimatorSet也是继承自Animator的，因此addListener()这个方法算是个通用的方法。
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton imageView;
    private Button alpha_bt;
    private Button rotationY_bt;
    private Button scaleX_bt;
    private Button translationX_bt;
    private Button AnimatorSet_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化
        init();
        //就是调用动画XML文件
        //B.此处用xml的形式,先进行引用
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.set);
        //B.使动画对象和要做动画的控件相关联
        animator.setTarget(imageView);
        //B.开启动画
        animator.start();
    }


    private void init() {
        //找到ImageView控件对象
        imageView = (ImageButton) findViewById(R.id.animation_iv);
        //找到Button控件对象.
        alpha_bt = (Button) findViewById(R.id.alpha_bt);
        rotationY_bt = (Button) findViewById(R.id.rotationY_bt);
        scaleX_bt = (Button) findViewById(R.id.scaleX_bt);
        translationX_bt = (Button) findViewById(R.id.translationY_bt);
        AnimatorSet_bt = (Button) findViewById(R.id.AnimatorSet_bt);
        //为button设置点击事件
        alpha_bt.setOnClickListener(this);
        rotationY_bt.setOnClickListener(this);
        scaleX_bt.setOnClickListener(this);
        translationX_bt.setOnClickListener(this);
        AnimatorSet_bt.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.alpha_bt://透明
                //得到ObjectAnimator对象,参数1,view控件所要修改的控件属性;参数2,什么类型的动画;参数3,控件修改的参数float数组
                ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", new float[]{0.0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f});
                //设置动画的执行时间
                alpha.setDuration(2000);
                //设置动画执行的模式
                alpha.setRepeatMode(ObjectAnimator.RESTART);
                //设置动画执行后再执行几次的次数
                alpha.setRepeatCount(1);
                //开启动画
                alpha.start();
                break;
            case R.id.rotationY_bt://旋转,X是轴,必须大些,也可以不写
                ObjectAnimator rotationY = ObjectAnimator.ofFloat(imageView, "rotationY", new float[]{90f, 180f, 270f, 360f});
                rotationY.setDuration(2000);
                rotationY.setRepeatMode(ObjectAnimator.RESTART);
                rotationY.start();
                break;
            case R.id.scaleX_bt://缩放
                ObjectAnimator scalex = ObjectAnimator.ofFloat(imageView, "scaleX", new float[]{1f, 2f, 3f, 4f, 5f, 6f, 5f, 4f, 3f, 2f, 1f});
                scalex.setDuration(3000);
                scalex.setRepeatMode(ObjectAnimator.RESTART);
                scalex.start();
                break;
            case R.id.translationY_bt://平移
                ObjectAnimator translationY = ObjectAnimator.ofFloat(imageView, "translationY", new float[]{10f, 20f, 30f, 40f, 30f, 20f, 10f}).setDuration(2000);
                translationY.setDuration(2000);
                translationY.start();
                break;
            case R.id.AnimatorSet_bt://集合
                //创建两个动画对象
                AnimatorSet animatorSet = new AnimatorSet();
                ObjectAnimator oa = ObjectAnimator.ofFloat(imageView, "rotationY", new float[]{90f, 180f, 270f, 360f});
                oa.setDuration(3000);
                ObjectAnimator ta = ObjectAnimator.ofFloat(imageView, "translationY", new float[]{10f, 20f, 30f, 40f, 30f, 20f, 10f}).setDuration(2000);
                ta.setDuration(2000);
                //设置到animatorSet动画中有两种方法
                //playTogether:同时进行
                //playSequentially:有顺序的执行动画效果
                animatorSet.playTogether(oa, ta);
                //animatorSet.playSequentially(oa,ta);
                animatorSet.start();
               /* 这个是设置动画监听.但是得重写接口中四个方法,所以可以直接new动画的适配器
               animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    //动画开始的时候调用
                    }
                    @Override
                    public void onAnimationEnd(Animator animator) {
                    //动画重复执行的时候调用
                    }
                    @Override
                    public void onAnimationCancel(Animator animator) {
                        在动画结束的时候调用
                    }
                    @Override
                    public void onAnimationRepeat(Animator animator) {
                        在动画被取消的时候调用
                    }
                });*/
                //这个类就可以解决掉实现接口繁琐的问题了
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    //用这种适配器的方法就可以减少不需要的方法,想在方法里调用就重写哪个方法就好
                    public void onAnimationEnd(Animator animator) {
                        //动画开始的时候调用
                        Log.e("hb","onAnimationEnd");
                       Toast.makeText(MainActivity.this,"组合式动画结束时调用的方法",Toast.LENGTH_SHORT).show();
                   }
                });
                break;
            default:
                break;
        }
    }
}
