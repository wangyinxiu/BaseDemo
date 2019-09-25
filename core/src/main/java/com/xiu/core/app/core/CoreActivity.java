package com.xiu.core.app.core;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xiu.core.R;
import com.xiu.core.compat.ViewCache;
import com.xiu.utils.UIUtil;

public class CoreActivity extends AppCompatActivity {

    protected String TAG = this.getClass().getSimpleName();

    private ViewCache viewCache;


    public static final int STATUS_TOOLBAR_NORMAL = 0;
    public static final int STATUS_TOOLBAR_DISMISS = 1;
    public static final int STATUS_TOOLBAR_SUSPENSION = 2;

    private static final int STATUS_NETWORK_NORMAL = 0;
    private static final int STATUS_NETWORK_ERROR = 1;

    private int networkStatus;

    private static Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        immersionStatusBar();
        networkStatus = checkNetworkStatus();
        UIUtil.getInstance().init(getCoreActivity());

    }


    private void immersionStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(android.R.id.content).setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    protected void onNoNetChanged() {

    }


    @Override
    public void onContentChanged() {
        super.onContentChanged();
        if (networkStatus == STATUS_NETWORK_NORMAL) {
            viewCache = new ViewCache(findViewById(android.R.id.content));
            onUIContentChanged();
        } else {
            onNoNetChanged();
        }
    }

    public <T extends View> T inflateView(int resId) {
        return (T) getLayoutInflater().inflate(resId, null, false);
    }


    @Override
    public void setContentView(int layoutResID) {
        View view = inflateView(R.layout.activity_core);
        FrameLayout contentContainer = view.findViewById(R.id.activity_content);
        View content = inflateView(layoutResID);
        contentContainer.addView(content);
        if (setToolbarStatus() == STATUS_TOOLBAR_DISMISS) {
            ((ViewGroup) view).removeView(view.findViewById(R.id.toolbar));
        }
        setContentView(view);
    }


    public int setToolbarStatus() {
        return STATUS_TOOLBAR_NORMAL;
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        TextView textView = getViewCache().getView(R.id.toolbar_title);
        if (textView != null) {
            textView.setText(title);
        }
    }

    private void initUIToolbar() {
        Toolbar toolbar = getViewCache().getView(R.id.toolbar);
        setBackgroundRes(R.color.white);
        int status = setToolbarStatus();
        if (status != STATUS_TOOLBAR_DISMISS) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.mipmap.ic_action_back);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
            RelativeLayout.LayoutParams toolbarLp = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
            toolbarLp.topMargin = UIUtil.getInstance().getStatusBarHeight();
            toolbar.setLayoutParams(toolbarLp);
            if (status == STATUS_TOOLBAR_SUSPENSION) {
                View content = getViewCache().getView(R.id.activity_content);
                RelativeLayout.LayoutParams contentLp = (RelativeLayout.LayoutParams) content.getLayoutParams();
                contentLp.topMargin = UIUtil.getInstance().getStatusBarHeight();
                contentLp.removeRule(RelativeLayout.BELOW);
                content.setLayoutParams(contentLp);
            }
        }

    }


    public Picasso getPicasso() {
        return Picasso.get();
    }

    public ViewCache getViewCache() {
        return viewCache;
    }

    public CoreActivity getCoreActivity() {
        return this;
    }

    public void onUIContentChanged() {
        initUIToolbar();
    }

    public void setBackgroundRes(int resId) {
        getViewCache().getImageView(R.id.activity_bg).setImageResource(resId);
    }

    private int checkNetworkStatus() {
        return STATUS_NETWORK_NORMAL;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            try {
                handler.removeCallbacksAndMessages(null);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                handler = null;
            }
        }

        if (viewCache != null) {
            viewCache.clear();
            viewCache = null;
        }
        AppManager.getAppManager().finishActivity(this);
    }


    public Handler getHandler() {
        if (handler == null) {
            synchronized (this.getClass()) {
                if (handler == null) {
                    handler = new Handler();
                }
            }
        }
        return handler;
    }

    public int dp2px(float dp) {
        return UIUtil.getInstance().dp2px(dp);
    }

    public int sp2px(float sp) {
        return UIUtil.getInstance().sp2px(sp);
    }
}
