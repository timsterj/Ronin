package com.timsterj.ronin.adapters.holders;

import android.view.View;

import androidx.annotation.NonNull;

import com.timsterj.ronin.data.model.Product;
import com.timsterj.ronin.databinding.OrderedProductItemBinding;
import com.timsterj.ronin.listeners.ICallbackBasketProductCountListener;
import com.squareup.picasso.Picasso;

public class OrderedProductViewHolder extends BaseViewHolder<Product> {

    private OrderedProductItemBinding binding;
    private ICallbackBasketProductCountListener listener;

    public OrderedProductViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = OrderedProductItemBinding.bind(itemView);
    }

    public void setListener(ICallbackBasketProductCountListener listener) {
        this.listener = listener;
    }

    @Override
    public void setData(Product data) {

        if (data.getCount() == 0) {
            data.setCount(1);
        }

        Picasso.get()
                .load(data.getImgUrl())
                .into(binding.imgViewFood);

        binding.txtViewName.setText(data.getName());
        binding.txtViewPrice.setText(data.getPrice() + " руб");
        binding.txtCount.setText(String.valueOf(data.getCount()));



        binding.imgBMinus.setOnClickListener(view -> {
            if (data.getCount() > 1) {
                data.setCount(data.getCount()-1);
                listener.addOrderProductCount(data.getCount(), getAdapterPosition());
                binding.txtCount.setText(String.valueOf(data.getCount()));
                listener.updatePrice();
            }

        });

        binding.imgBPlus.setOnClickListener(view -> {
            if (data.getCount() < 20) {
                data.setCount(data.getCount()+1);
                listener.addOrderProductCount(data.getCount(), getAdapterPosition());
                binding.txtCount.setText(String.valueOf(data.getCount()));
                listener.updatePrice();
            }

        });

    }


}
