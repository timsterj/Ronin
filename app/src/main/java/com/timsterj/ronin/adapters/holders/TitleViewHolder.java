package com.timsterj.ronin.adapters.holders;

import android.view.View;

import androidx.annotation.NonNull;

import com.timsterj.ronin.databinding.TitleItemBinding;
import com.timsterj.ronin.model.ProductItem;

public class TitleViewHolder extends BaseViewHolder<ProductItem> {

    private TitleItemBinding binding;

    public TitleViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = TitleItemBinding.bind(itemView);
    }

    @Override
    public void setData(ProductItem data) {
        binding.txtTitle.setText(data.getTitle());
    }
}
