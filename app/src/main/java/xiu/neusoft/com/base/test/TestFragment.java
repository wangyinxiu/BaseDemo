package xiu.neusoft.com.base.test;

import android.widget.Toast;

import com.xiu.core.app.adapter.CoreAdapter;
import com.xiu.core.app.core.recycler.MvpBaseRecyclerFragment;
import com.xiu.core.app.core.recycler.MvpBaseRecyclerPresenter;
import com.xiu.core.app.core.recycler.MvpBaseRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TestFragment extends MvpBaseRecyclerFragment<
        TestEntity,
        TestCoreAdapter,
        MvpBaseRecyclerView<TestEntity>,
        MvpBaseRecyclerPresenter<MvpBaseRecyclerView<TestEntity>>> implements CoreAdapter.OnItemClickListener<TestEntity> {


    @Override
    protected boolean isRefresh() {
        return true;
    }

    @Override
    protected boolean isLoadMore() {
        return true;
    }

    @Override
    public TestCoreAdapter createAdapter() {
        TestCoreAdapter adapter = new TestCoreAdapter(getCoreActivity());
        adapter.setListener(this::onClick);
        return adapter;
    }

    @Override
    public MvpBaseRecyclerPresenter<MvpBaseRecyclerView<TestEntity>> createPresenter() {
        return new MvpBaseRecyclerPresenter<MvpBaseRecyclerView<TestEntity>>() {
            @Override
            public void requestPageData(int page) {
                List<TestEntity> data = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    data.add(new TestEntity("page:" + page + ":" + i));
                }
                getView().onRefreshData(page, data);
            }
        };
    }

    @Override
    public void onClick(int id, TestEntity testEntity, int position) {
        Toast.makeText(getCoreActivity(), testEntity.getName(), Toast.LENGTH_SHORT).show();
    }
}
