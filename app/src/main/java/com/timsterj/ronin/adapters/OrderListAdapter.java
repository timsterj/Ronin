package com.timsterj.ronin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.timsterj.ronin.R;
import com.timsterj.ronin.adapters.holders.OrderedProductViewHolder;
import com.timsterj.ronin.data.model.Product;
import com.timsterj.ronin.listeners.ICallbackBasketProductCountListener;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderedProductViewHolder> {

    private List<Product> productList  = new ArrayList<>();
    private ICallbackBasketProductCountListener listener;

    public void setListener(ICallbackBasketProductCountListener listener) {
        this.listener = listener;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderedProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordered_product_item, parent, false);
        return new OrderedProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderedProductViewHolder holder, int position) {
        holder.setListener(listener);
        holder.setData(productList.get(position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

}
