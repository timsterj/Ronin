package com.timsterj.ronin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.timsterj.ronin.R;
import com.timsterj.ronin.adapters.holders.BaseViewHolder;
import com.timsterj.ronin.adapters.holders.ProductViewHolder;
import com.timsterj.ronin.adapters.holders.TitleViewHolder;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.listeners.IClickProductListener;
import com.timsterj.ronin.model.ProductItem;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {

    private List<ProductItem> productList = new ArrayList<>();
    private List<ProductItem> productListFiltered = new ArrayList<>();
    private IClickProductListener listener;

    public void setListener(IClickProductListener listener) {
        this.listener = listener;
    }

    public void setProductList(List<ProductItem> productList) {
        this.productList = productList;
        this.productListFiltered = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {

            case Contracts.AdapterConstant.PRODUCT_VIEW_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
                return new ProductViewHolder(view);
            case Contracts.AdapterConstant.TITLE_VIEW_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.title_item, parent, false);
                return new TitleViewHolder(view);
            default:
                throw new IllegalArgumentException();
        }

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (holder instanceof ProductViewHolder) {
            ((ProductViewHolder) holder).setListener(listener);
            holder.setData(productList.get(position));
        } else {
            holder.setData(productList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return productList.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                List<ProductItem> filteredList = new ArrayList<>();

                if (!charString.isEmpty()) {

                    for (ProductItem row : productListFiltered) {
                        if (row.getProduct() != null) {
                            if (row.getProduct().getName().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);

                            }

                        }
                    }

                } else {
                    filteredList = productListFiltered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (!((List<ProductItem>) results.values).isEmpty()) {
                    productList = (List<ProductItem>) results.values;
                    notifyDataSetChanged();
                }

            }
        };
    }

    public List<ProductItem> getProductList() {
        return productList;
    }
}
