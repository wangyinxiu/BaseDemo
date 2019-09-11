package xiu.neusoft.com.base.test;

import com.squareup.picasso.Picasso;
import com.xiu.core.app.adapter.CoreAdapter;
import com.xiu.core.app.adapter.Holder;
import com.xiu.core.app.core.CoreActivity;

import xiu.neusoft.com.base.R;

public class TestCoreAdapter extends CoreAdapter<TestEntity> {

    public TestCoreAdapter(CoreActivity coreActivity) {
        super(coreActivity);
    }

    @Override
    public void onClick(int id, TestEntity testEntity, int position) {

    }

    @Override
    public int setLayoutId() {
        return R.layout.item_test;
    }

    @Override
    public int[] onClickViews() {
        return new int[]{R.id.item_text};
    }

    @Override
    public void bind(Holder holder, TestEntity testEntity, int position) {
        holder.getViewCache().getTextView(R.id.item_text).setText(testEntity.getName());
        Picasso.get().load(testEntity.getName()).into(holder.getViewCache().getImageView(R.id.item_image));
    }
}
