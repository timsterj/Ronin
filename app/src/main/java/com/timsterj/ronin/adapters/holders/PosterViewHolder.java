package com.timsterj.ronin.adapters.holders;

import android.view.View;

import androidx.annotation.NonNull;

import com.timsterj.ronin.model.Poster;
import com.timsterj.ronin.databinding.PosterItemBinding;
import com.timsterj.ronin.model.AboutUsItem;
import com.squareup.picasso.Picasso;

public class PosterViewHolder extends BaseViewHolder<AboutUsItem> {

    private PosterItemBinding binding;

    @Override
    public void setData(AboutUsItem data) {
        Poster poster = data.getPoster();

        Picasso.get()
                .load(poster.getImgUrl())
                .into(binding.imgBackground);
    }

    public PosterViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = PosterItemBinding.bind(itemView);
    }
}
