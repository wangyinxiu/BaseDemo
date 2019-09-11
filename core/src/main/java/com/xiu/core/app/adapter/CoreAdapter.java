package com.xiu.core.app.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.xiu.core.app.core.CoreActivity;

import java.util.ArrayList;
import java.util.List;

public abstract class CoreAdapter<T> extends RecyclerView.Adapter<Holder> implements View.OnClickListener {

    private CoreActivity coreActivity;
    private int[] onClickViews;
    private List<T> data;
    private OnItemClickListener<T> listener;

    public CoreAdapter(CoreActivity coreActivity) {
        this.coreActivity = coreActivity;
        data = new ArrayList<>();
    }

    public void setListener(OnItemClickListener<T> listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Holder holder = new Holder(coreActivity.inflateView(setLayoutId()));
        onClickViews = onClickViews();
        if (hasClickViews()) {
            setOnClickListener(holder);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        setTag(holder, position);
        bind(holder,getItem(position),position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    private void setOnClickListener(Holder holder) {
        for (int id : onClickViews) {
            View view = holder.getViewCache().getView(id);
            if (view != null) {
                view.setOnClickListener(this::onClick);
            }
        }
    }

    private void setTag(Holder holder, int position) {
        for (int id : onClickViews) {
            View view = holder.getViewCache().getView(id);
            if (view != null) {
                view.setTag(new int[]{id, position});
            }
        }
    }

    private boolean hasClickViews() {
        if (onClickViews != null && onClickViews.length > 0) {
            return true;
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        int[] tags = (int[]) v.getTag();
        if (tags != null) {
            onClick(tags[0], getItem(tags[1]), tags[1]);
        }
        if(listener != null){
            listener.onClick(tags[0],getItem(tags[1]),tags[1]);
        }
    }

    public T getItem(int position) {
        return data.get(position);
    }

    public void addData(T t) {
        if (t != null) {
            this.data.add(t);
            notifyDataSetChanged();
        }
    }

    public void addData(List<T> t) {
        if (t != null && t.size() > 0) {
            this.data.addAll(t);
            notifyDataSetChanged();
        }
    }

    public void remove(int position) {
        if (position >= 0 && position < data.size()) {
            this.data.remove(position);
            notifyDataSetChanged();
        }
    }

    public void remove(T t) {
        if (this.data.contains(t)) {
            this.data.remove(t);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        if (data.size() > 0) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    public CoreActivity getCoreActivity() {
        return coreActivity;
    }

    public List<T> getData() {
        return data;
    }

    public abstract void onClick(int id, T t, int position);

    public abstract int setLayoutId();

    public abstract int[] onClickViews();

    public abstract void bind(Holder holder,T t,int position);

    public interface OnItemClickListener<T>{
        void onClick(int id,T t,int position);
    }
}

