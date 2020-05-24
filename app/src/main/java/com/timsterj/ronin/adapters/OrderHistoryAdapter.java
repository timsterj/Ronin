package com.timsterj.ronin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.timsterj.ronin.R;
import com.timsterj.ronin.adapters.holders.OrderViewHolder;
import com.timsterj.ronin.data.model.OrderDone;
import com.timsterj.ronin.listeners.IClickProductListener;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderViewHolder> {

    private List<OrderDone> orderDoneList = new ArrayList<>();
    private IClickProductListener<OrderDone> listener;

    public void setOrderDoneList(List<OrderDone> orderDoneList) {
        this.orderDoneList = orderDoneList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderDone orderDone = orderDoneList.get(position);
        holder.setListener(listener);
        if (position == orderDoneList.size()-1) {
            holder.setData(orderDone);
        } else {
            orderDone.setStatus("");
            holder.setData(orderDone);

        }


    }

    @Override
    public int getItemCount() {
        return orderDoneList.size();
    }

    public void setListener(IClickProductListener<OrderDone> listener) {
        this.listener = listener;
    }
}
