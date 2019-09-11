package xiu.neusoft.com.base.test;

import android.support.annotation.NonNull;

import com.xiu.core.app.adapter.CorePagerAdapter;
import com.xiu.core.app.core.pager.MvpBasePagerActivity;
import com.xiu.core.app.core.pager.MvpBasePagerPresenter;
import com.xiu.core.app.core.pager.MvpBasePagerView;
import com.xiu.core.view.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

import xiu.neusoft.com.base.R;

public class TestPagerActivity extends MvpBasePagerActivity<MvpBasePagerView,
        MvpBasePagerPresenter<MvpBasePagerView>> implements MvpBasePagerView {

    @Override
    public CorePagerAdapter createPagerAdapter() {
        CorePagerAdapter<TestFragment> adapter =
                new CorePagerAdapter(getSupportFragmentManager());
        List<TestFragment> fragments = new ArrayList<>();
        fragments.add(new TestFragment());
        fragments.add(new TestFragment());
        fragments.add(new TestFragment());
        fragments.add(new TestFragment());
        adapter.addData(fragments);
        return adapter;
    }

    @Override
    public int tabNameResId() {
        return R.array.tab_test;
    }

    @NonNull
    @Override
    public MvpBasePagerPresenter<MvpBasePagerView> createPresenter() {
        return new MvpBasePagerPresenter<>();
    }

    @Override
    public void setNavigatorMode(CommonNavigator commonNavigator) {
        commonNavigator.setAdjustMode(true);
    }
}
