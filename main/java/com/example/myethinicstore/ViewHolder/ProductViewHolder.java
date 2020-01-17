package com.example.myethinicstore.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myethinicstore.Interface.ItemClickListner;
import com.example.myethinicstore.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView tvProductName,tvProductDescription,tvProductPrice;
    public ImageView  imageView;
    public ItemClickListner listner;

    public ProductViewHolder(@NonNull View itemView)
    {
        super(itemView);

        imageView = itemView.findViewById(R.id.ivProductImage);
        tvProductName = itemView.findViewById(R.id.tvProductName);
        tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
        tvProductDescription = itemView.findViewById(R.id.tvProductDescription);
    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {

        listner.onClick(view, getAdapterPosition(), false);
    }
}
