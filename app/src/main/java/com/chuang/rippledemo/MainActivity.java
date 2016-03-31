package com.chuang.rippledemo;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.chuang.ripppledemo.R;

public class MainActivity extends AppCompatActivity {

    private RevealView mLayoutRevealView;
    boolean isRevealOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayoutRevealView = (RevealView) findViewById(R.id.reveal_layout);


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Point p = getLocationInView(mLayoutRevealView, fab);
                if (isRevealOpen == false) {
                    showLayoutRevealColorView(p, new RevealView.RevealAnimationListener() {
                        @Override
                        public void finish() {
                            Toast.makeText(getApplicationContext(), "水波绘制完成", Toast.LENGTH_SHORT).show();
                            //以下写水波结束后的动作
                            //.......

                        }
                    });
//                    showAlbumRevealColorView(p);
                    Log.d("123", "打开");
                } else {
                    closeLayoutRevealColorView(p);
                    Log.d("123", "关闭");

                }


            }
        });

    }


    ///////////////////////////////////////////////////////////////////////////////////////////
    /* 动画 */
    int DURATION = 500;//动画持续时间
    int RADIUS = 10;

    /**
     * 关闭水波回退到一点
     *
     * @param p
     */
    private void closeLayoutRevealColorView(Point p) {
//        Point p = getLocationInView(mLayoutRevealView, mFloatingView);
        mLayoutRevealView.hide(p.x, p.y, Color.TRANSPARENT, RADIUS, DURATION, null);
        isRevealOpen = false;
    }


    /**
     * 仅绘制水波
     *
     * @param p 水波中心坐标
     */
    private void showAlbumRevealColorView(Point p) {
        mLayoutRevealView.reveal(p.x, p.y, getResources().getColor(R.color.colorAccent), RADIUS, DURATION, null);
        isRevealOpen = true;
    }

    /**
     * 可实现完成事件的水波
     *
     * @param listener
     */
    public void showLayoutRevealColorView(Point p, RevealView.RevealAnimationListener listener) {
        mLayoutRevealView.reveal(p.x, p.y, getThemeColor(), RADIUS, DURATION, listener);
        isRevealOpen = true;
    }

    /**
     * 获取主题Theme颜色
     *
     * @return
     */
    public int getThemeColor() {
        TypedValue typedValue = new TypedValue();
        int[] colorAttr = new int[]{R.attr.colorPrimary};
        int indexOfAttrColor = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, colorAttr);
        int color = a.getColor(indexOfAttrColor, -1);
        a.recycle();
        return color;
    }

    /**
     * 计算view控件的坐标
     *
     * @param src    控件来源（父容器）
     * @param target view对象
     * @return point对象。point.x和point.y为横纵坐标
     */
    public Point getLocationInView(View src, View target) {
        final int[] l0 = new int[2];
        src.getLocationOnScreen(l0);

        final int[] l1 = new int[2];
        target.getLocationOnScreen(l1);

        l1[0] = l1[0] - l0[0] + target.getWidth() / 2;
        l1[1] = l1[1] - l0[1] + target.getHeight() / 2;

        return new Point(l1[0], l1[1]);
    }
}
