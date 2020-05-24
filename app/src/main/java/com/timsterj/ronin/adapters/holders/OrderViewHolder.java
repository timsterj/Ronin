package com.timsterj.ronin.adapters.holders;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.data.model.OrderDone;
import com.timsterj.ronin.databinding.OrderHistoryItemBinding;
import com.timsterj.ronin.listeners.IClickProductListener;

public class OrderViewHolder extends BaseViewHolder<OrderDone> {

    private OrderHistoryItemBinding binding;
    private IClickProductListener<OrderDone> listener;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = OrderHistoryItemBinding.bind(itemView);
    }

    @Override
    public void setData(OrderDone data) {
        StringBuilder orderInfo = new StringBuilder();

        String status = data.getStatus();

        orderInfo.append("ID заказа: ").append(data.getOrder_id()).append("\n");
        orderInfo.append("Время: ").append(data.getDate()).append("\n");
        orderInfo.append("Кол-во продуктов: ").append(data.getOrderList().size() + "").append("\n");
        orderInfo.append("На сумму: ").append(data.getPrice() + "").append("\n");

        binding.txtOrderInfo.setText(orderInfo.toString());

        binding.btnReorder.setOnClickListener(view->{
            Log.d(Contracts.TAG, "REORDER");
        });

        binding.cardView.setOnClickListener(view->{
            if (listener != null) {
                listener.onProductClick(data);
            }

        });

        if (status.equals("Новый")) {
            binding.txtOrderStatus.setTextColor(Color.RED);
        } else if (status.equals("В производстве")){
            binding.txtOrderStatus.setTextColor(Color.CYAN);
        } else if (status.equals("Произведен")){
            binding.txtOrderStatus.setTextColor(Color.GREEN);
        } else if (status.equals("В пути")){
            binding.txtOrderStatus.setTextColor(Color.YELLOW);
        } else if (status.equals("Выполнен")){
            binding.txtOrderStatus.setTextColor(Color.GRAY);
            binding.btnReorder.setVisibility(View.VISIBLE);
        } else if (status.equals("Списан")) {
            binding.txtOrderStatus.setTextColor(Color.DKGRAY);
        }

        if (!status.equals("")) {
            binding.txtOrderStatus.setText("Статус заказа: " + status);
        }



    }

    public void setListener(IClickProductListener<OrderDone> listener) {
        this.listener = listener;
    }
}
