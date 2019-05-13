package com.example.json.mytouzhisystem;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.json.mytouzhisystem.Utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    public final String TAG = getClassName();

    private View loadingView;
    protected Handler mHandler;

    private Runnable mDismissLoadingRunnable = new Runnable() {

        @Override
        public void run() {
            dismissAllLoading();
        }
    };

    protected float[][] points = new float[][]{{1,10}, {2,47}, {3,11}, {4,38}, {5,9},{6,52}, {7,14}, {8,37}, {9,29}, {10,31}};
    protected float[][] points2 = new float[][]{{1,52}, {2,13}, {3,51}, {4,20}, {5,19},{6,20}, {7,54}, {8,7}, {9,19}, {10,41}};
    protected int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    protected float pxTodp(float value){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float valueDP= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,metrics);
        return valueDP;
    }
    // 当前activity是否是前台运行状态
    public boolean isForegroundRunning = false;

    // 当前app是否是前台状态
    private static boolean isActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YiLog.D(getClassName() + ":onCreate");
        isForegroundRunning = false;

    }

    public Handler getHandler(){
        if(mHandler == null){
            synchronized (this){
                if(mHandler == null){
                    mHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return mHandler;
    }


    /**
     * 显示loading视图
     *
     * @param loadingId 与取消loading时调用dismissLoading(id)的参数对应
     *                  <p>调用了多少次showLoading(id) 就必须对应的调用多少次 dismissLoding(id)。
     *                  <p>不同的耗时操作应有不同的loadingId
     */
    public void showLoading(int loadingId) {
        showLoading(loadingId, null);
        getHandler().removeCallbacks(mDismissLoadingRunnable);
        getHandler().postDelayed(mDismissLoadingRunnable, 20 * 1000);
    }

    /**
     * 调用了多少次该方法 就必须对应的调用多少次 dismissLoding()。
     */
    public void showLoading() {
        showLoading(0);
    }

    /**
     * @param message loading 提示文字
     */
    public void showLoading(int loadingId, String message) {
        if (loadingView == null) {
            loadingView = initLoadingView();
        }
        if (message != null) {
            TextView tv = (TextView) loadingView.findViewById(R.id.loadingMessage);
            if (tv != null) {
                tv.setText(message);
                tv.setVisibility(View.VISIBLE);
            }
        }
        ArrayList<Integer> list = (ArrayList<Integer>) loadingView.getTag();
        list.add(loadingId);
        if (loadingView.getParent() != null) {
            return;
        }
        ViewGroup vg = (ViewGroup) getWindow().getDecorView();
        vg.addView(loadingView);
    }


    /**
     * 取消对应id的loading
     *
     * @param loadingId 和调用showLoading传的id对应
     */
    public void dismissLoading(int loadingId) {
        if (loadingView != null && loadingView.getParent() != null) {
            ArrayList<Integer> list = (ArrayList<Integer>) loadingView.getTag();
            list.remove(Integer.valueOf(loadingId));
            if (list.isEmpty()) {
                ((ViewGroup) loadingView.getParent()).removeView(loadingView);
            }
        }
    }

    public void dismissLoading() {
        dismissLoading(0);
    }

    /**
     * 取消所有loading
     */
    public void dismissAllLoading() {
        if (loadingView != null && loadingView.getParent() != null) {
            ArrayList<Integer> list = (ArrayList<Integer>) loadingView.getTag();
            list.clear();
            ((ViewGroup) loadingView.getParent()).removeView(loadingView);
        }
    }

    /**
     * 是否在加载
     *
     * @return
     */
    public boolean isLoading() {
        if (loadingView != null && loadingView.getParent() != null) {
            return true;
        }
        return false;
    }

    public void showMessage(int text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void showMessage(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    public void doInUI(Runnable runnable){
        doInUI(runnable, 0);
    }
    
    public void doInUI(Runnable runnable, long delayMillis){
        getHandler().postDelayed(runnable, delayMillis);
    }

    public void toActivity(Class<? extends Activity> clazz) {
        startActivity(new Intent(this, clazz));
    }

    @Override
    protected void onResume() {
        super.onResume();
        YiLog.D(getClassName() + ":onResume");
        isForegroundRunning = true;

        ScreenUtil.makeStatusBarTransparent(this, true);

        if (!isActive) {
            //app 从后台唤醒，进入前台
            YiLog.D("----app is in foreground----");
            isActive = true;

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isForegroundRunning = false;
        YiLog.D(getClassName() + ":onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        YiLog.D(getClassName() + ":onStop");
        isForegroundRunning = false;

        if (!isAppOnForeground()) {
            //app 进入后台
            YiLog.D("----app is in background----");
            isActive = false;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        YiLog.D(getClassName() + ":onDestroy");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        YiLog.D(getClassName() + ":onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        YiLog.D(getClassName() + ":onSaveInstanceState");
        isForegroundRunning = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        YiLog.D(getClassName() + ":onActivityResult");
    }

    @Override
    public void onBackPressed() {
        if (loadingView == null || loadingView.getParent() == null) {
            super.onBackPressed();
            return;
        }
        ArrayList<Integer> list = (ArrayList<Integer>) loadingView.getTag();
        list.clear();
        ((ViewGroup) loadingView.getParent()).removeView(loadingView);
    }

    public <V extends View> V findView(int id) {
        return (V) findViewById(id);
    }


    /**
     * 初始化loading视图，可自定义重写，
     */
    protected View initLoadingView() {
        View loadingView = getLayoutInflater().inflate(R.layout.progress_loading, null);
        loadingView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        ArrayList<Integer> list = new ArrayList<Integer>();
        loadingView.setTag(list);
        return loadingView;
    }


    public String getClassName(){
        return getClass().getSimpleName();
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    private boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the device

        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

}
