package com.hussam.matricball.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.hussam.matricball.Interface.ItemClickListner;
import com.hussam.matricball.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView txtUserName, txtUserPhone, txtUserCompany;
    public ImageView imageView;
    public ItemClickListner listner;
    public UserViewHolder(@NonNull View itemView) {
        super(itemView);


        imageView = itemView.findViewById(R.id.image);
        txtUserName = itemView.findViewById(R.id.image_name);
       // txtUserPhone = itemView.findViewById(R.id.user_number);
       // txtUserCompany = itemView.findViewById(R.id.user_company);
    }

    public void setItemClickListner(ItemClickListner listner){
        this.listner = listner;

    }

    @Override
    public void onClick(View v) {
        listner.onClick(v, getAdapterPosition(), false);

    }
}
