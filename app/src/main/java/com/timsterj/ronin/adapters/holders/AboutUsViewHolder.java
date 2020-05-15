package com.timsterj.ronin.adapters.holders;

import android.view.View;

import androidx.annotation.NonNull;

import com.timsterj.ronin.model.AboutUs;
import com.timsterj.ronin.databinding.AboutUsItemBinding;
import com.timsterj.ronin.model.AboutUsItem;
import com.squareup.picasso.Picasso;

public class AboutUsViewHolder extends BaseViewHolder<AboutUsItem> {

    private AboutUsItemBinding binding;

    @Override
    public void setData(AboutUsItem data) {
        AboutUs aboutUs = data.getAboutUs();

        Picasso.get()
                .load(aboutUs.getImgUrl())
                .into(binding.imgBackground);

        binding.textViewTitle.setText(aboutUs.getTitle());
        binding.textViewDescription.setText(aboutUs.getDescription());

    }

    public AboutUsViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = AboutUsItemBinding.bind(itemView);
    }
}
