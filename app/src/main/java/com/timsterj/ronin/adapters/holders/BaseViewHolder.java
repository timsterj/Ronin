package com.timsterj.ronin.adapters.holders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {


    public abstract void setData(T data);

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
