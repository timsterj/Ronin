package com.timsterj.ronin.adapters.holders;

import android.view.View;

import androidx.annotation.NonNull;

import com.timsterj.ronin.data.model.Product;
import com.timsterj.ronin.databinding.AdditionalOrderItemBinding;
import com.timsterj.ronin.listeners.ICallbackAdditionalProductListener;
import com.timsterj.ronin.model.ProductItem;
import com.squareup.picasso.Picasso;

public class AdditionalFoodViewHolder extends BaseViewHolder<ProductItem> {

    private AdditionalOrderItemBinding binding;
    private ICallbackAdditionalProductListener listener;

    @Override
    public void setData(ProductItem productItem) {
        Product data = productItem.getProduct();

        Picasso.get()
                .load(data.getImgUrl())
                .into(binding.imgViewFood);

        binding.txtViewName.setText(data.getName());
        binding.txtViewPrice.setText(data.getPrice() + " руб");
        binding.txtCount.setText(String.valueOf(data.getCount()));

        binding.imgBMinus.setOnClickListener(view -> {
            if (data.getCount() > 0 ) {
                data.setCount(data.getCount()-1);
                binding.txtCount.setText(String.valueOf(data.getCount()));
                listener.updatePrice();
            }

        });

        binding.imgBPlus.setOnClickListener(view -> {
            if (data.getCount() < 20) {
                data.setCount(data.getCount()+1);
                binding.txtCount.setText(String.valueOf(data.getCount()));
                listener.updatePrice();
            }

        });
    }

    public void setListener(ICallbackAdditionalProductListener listener) {
        this.listener = listener;
    }

    public AdditionalFoodViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = AdditionalOrderItemBinding.bind(itemView);
    }





}
