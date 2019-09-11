package com.xiu.core.app.core.pager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.xiu.core.R;
import com.xiu.core.app.adapter.CorePagerAdapter;
import com.xiu.core.app.mvp.MvpActivity;
import com.xiu.core.utils.UIUtil;
import com.xiu.core.view.magicindicator.MagicIndicator;
import com.xiu.core.view.magicindicator.ViewPagerHelper;
import com.xiu.core.view.magicindicator.buildins.commonnavigator.CommonNavigator;
import com.xiu.core.view.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import com.xiu.core.view.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.xiu.core.view.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.xiu.core.view.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import com.xiu.core.view.magicindicator.buildins.commonnavigator.titles.ColorFlipPagerTitleView;
import com.xiu.core.view.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.Arrays;
import java.util.List;

public abstract class MvpBasePagerActivity<V extends MvpBasePagerView,
        P extends MvpBasePagerPresenter<V>> extends MvpActivity<V, P> {

    private CorePagerAdapter adapter;
    private List<String> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
    }

    @Override
    public void onUIContentChanged() {
        super.onUIContentChanged();
        ViewPager pager = getViewCache().getView(R.id.pager);
        adapter = createPagerAdapter();
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(adapter.getCount());
        initIndicator(pager);
    }

    public abstract CorePagerAdapter createPagerAdapter();

    public abstract int tabNameResId();


    private void initIndicator(ViewPager pager) {
        mDataList = Arrays.asList(getCoreActivity().getResources().getStringArray(tabNameResId()));
        MagicIndicator magicIndicator = getViewCache().getView(R.id.magic_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        setNavigatorMode(commonNavigator);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                setPagerTitleView(simplePagerTitleView);
                simplePagerTitleView.setNormalColor(getCoreActivity().getResources().getColor(R.color.tab_normal));
                simplePagerTitleView.setSelectedColor(getCoreActivity().getResources().getColor(R.color.tab_selected));
                simplePagerTitleView.setOnClickListener(v -> pager.setCurrentItem(index));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight( UIUtil.getInstance().dp2px(6));
                indicator.setLineWidth(UIUtil.getInstance().dp2px(20));
                indicator.setRoundRadius(UIUtil.getInstance().dp2px(3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(getCoreActivity().getResources().getColor(R.color.tab_indicator));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, pager);

    }

    public void setNavigatorMode(CommonNavigator commonNavigator) {
        int padding = UIUtil.getInstance().dp2px(10);
        commonNavigator.setLeftPadding(padding);
        commonNavigator.setRightPadding(padding);
    }

    public void setPagerTitleView(SimplePagerTitleView view) {
        int padding = UIUtil.getInstance().dp2px(15);
        view.setPadding(padding, 0, padding, 0);

    }
}
