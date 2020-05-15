package com.timsterj.ronin.adapters.holders;

import android.view.View;

import androidx.annotation.NonNull;

import com.timsterj.ronin.data.model.Product;
import com.timsterj.ronin.databinding.ProductItemBinding;
import com.timsterj.ronin.listeners.IClickProductListener;
import com.timsterj.ronin.model.ProductItem;
import com.squareup.picasso.Picasso;

public class ProductViewHolder extends BaseViewHolder<ProductItem> {

    private ProductItemBinding binding;
    private IClickProductListener listener;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = ProductItemBinding.bind(itemView);
    }

    public void setListener(IClickProductListener listener) {
        this.listener = listener;
    }

    @Override
    public void setData(ProductItem data) {
        Product product = data.getProduct();

        Picasso.get()
                .load(product.getImgUrl())
                .into(binding.imgViewFood);

        binding.txtViewName.setText(product.getName());

        binding.txtViewPrice.setText(product.getPrice() + " руб");

        if (listener != null) {
            binding.foodContainer.setOnClickListener(view->{
                listener.onProductClick(data.getProduct());
            });

        }

    }

}
