package com.blogspot.mowael.molib.adapters;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by moham on 6/5/2017.
 */

public abstract class MoRecyclerBaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected ArrayList<T> items;

    public MoRecyclerBaseAdapter(ArrayList<T> items) {
        this.items = items;
    }

    public void addItem(T item) {
        items.add(item);
        notifyItemInserted(this.items.size());
        notifyDataSetChanged();
    }

    @Override
    public abstract void onBindViewHolder(VH holder, int position);

    public void addItem(int position, T item) {
        items.add(position, item);
        notifyItemInserted(this.items.size());
//        notifyDataSetChanged();
    }

    public void setItems(ArrayList<T> items) {
        if (this.items != null) {
            items.clear();
        }
        this.items = items;
        notifyDataSetChanged();
    }

    public void copyItems(ArrayList<T> items) {
        this.items = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    public void addMoreItems(ArrayList<T> items) {
        int lastItem = this.items.size();
        this.items.addAll(items);
        notifyItemRangeInserted(lastItem, this.items.size());
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }
}
