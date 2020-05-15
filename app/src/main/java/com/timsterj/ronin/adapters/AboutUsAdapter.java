package com.timsterj.ronin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.timsterj.ronin.R;
import com.timsterj.ronin.adapters.holders.AboutUsViewHolder;
import com.timsterj.ronin.adapters.holders.BaseViewHolder;
import com.timsterj.ronin.adapters.holders.PosterViewHolder;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.model.AboutUsItem;

import java.util.List;

public class AboutUsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<AboutUsItem> aboutUsList;

    public void setAboutUsList(List<AboutUsItem> aboutUsList) {
        this.aboutUsList = aboutUsList;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {

            case Contracts.AdapterConstant.ABOUT_US_VIEW_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.about_us_item, parent, false);
                return new AboutUsViewHolder(view);
            case Contracts.AdapterConstant.POSTER_VIEW_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poster_item, parent, false);
                return new PosterViewHolder(view);
            default:
                throw new IllegalArgumentException();
        }

    }

    @Override
    public int getItemViewType(int position) {
        return aboutUsList.get(position).getViewType();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.setData(aboutUsList.get(position));

    }

    @Override
    public int getItemCount() {
        return aboutUsList.size();
    }
}
