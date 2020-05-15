package com.timsterj.ronin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.timsterj.ronin.R;
import com.timsterj.ronin.adapters.holders.AdditionalFoodViewHolder;
import com.timsterj.ronin.listeners.ICallbackAdditionalProductListener;
import com.timsterj.ronin.model.ProductItem;

import java.util.List;

public class AdditionalFoodAdapter extends RecyclerView.Adapter<AdditionalFoodViewHolder> {

    private List<ProductItem> productItemList;
    private ICallbackAdditionalProductListener listener;

    public void setProductItemList(List<ProductItem> productItemList) {
        this.productItemList = productItemList;
        notifyDataSetChanged();
    }

    public void setListener(ICallbackAdditionalProductListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdditionalFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.additional_order_item, parent, false);
        return new AdditionalFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdditionalFoodViewHolder holder, int position) {
        holder.setData(productItemList.get(position));
        holder.setListener(listener);
    }

    @Override
    public int getItemCount() {
        return productItemList.size();
    }

    public List<ProductItem> getProductItemList() {
        return productItemList;
    }
}
