package com.blogspot.mowael.molib.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.mowael.molib.R;
import com.blogspot.mowael.molib.utilities.Logger;

import java.util.ArrayList;

/**
 * Created by moham on 6/9/2017.
 */

public abstract class MoRecyclerEndlessAdapter<T, VH extends RecyclerView.ViewHolder>
        extends MoRecyclerBaseAdapter<T, RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_ITEM = 0;
    public static final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;

    public MoRecyclerEndlessAdapter(ArrayList<T> items, RecyclerView mRecyclerView) {
        super(items);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mRecyclerView.getLayoutManager()) {
            @Override
            public int getFooterViewType(int defaultNoFooterViewType) {
                return VIEW_TYPE_LOADING;
            }

            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Logger.d(page + "");
                if (onLoadMoreListener != null)
                    onLoadMoreListener.onLoadMore(page, totalItemsCount);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_LOADING:
                return onCreateFootViewHolder(viewGroup);
            case VIEW_TYPE_ITEM:
                return onCreateMainViewHolder(viewGroup);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        try {
            return getItem(position + 1) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
        } catch (IndexOutOfBoundsException e) {
            return VIEW_TYPE_LOADING;
        }
    }

    protected LoadingViewHolder onCreateFootViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.load_more_layout, viewGroup, false);
        return new LoadingViewHolder(view);
    }

    public abstract VH onCreateMainViewHolder(ViewGroup viewGroup);

    public abstract void onBindMainViewHolder(VH holder, int position);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MoRecyclerEndlessAdapter.LoadingViewHolder) {

        } else {
            onBindMainViewHolder((VH) holder, position);
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int page, int totalItemsCount);
    }
}
