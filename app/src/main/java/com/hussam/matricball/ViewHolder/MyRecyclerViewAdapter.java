package com.hussam.matricball.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.hussam.matricball.Model.CategoriesModel;
import com.hussam.matricball.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {


    private List<String> mAnimals;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ArrayList<CategoriesModel> imageModelArrayList;


    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, ArrayList<CategoriesModel> imageModelArrayList) {
        this.mInflater = LayoutInflater.from(context);
        this.imageModelArrayList = imageModelArrayList;

    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.myView.setImageResource(imageModelArrayList.get(position).getImage_drawable());
        holder.myTextView.setText(imageModelArrayList.get(position).getName());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView myView;
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myView = itemView.findViewById(R.id.colorView);
            myTextView = itemView.findViewById(R.id.tvAnimalName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());


        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mAnimals.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);


    }
}