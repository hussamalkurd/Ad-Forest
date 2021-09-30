package com.hussam.matricball.Ladies;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.hussam.matricball.Model.CategoriesModel;
import com.hussam.matricball.R;
import com.hussam.matricball.ViewHolder.MyRecyclerViewAdapter;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LadiesActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener{


    private MyRecyclerViewAdapter adapter;
    private ArrayList<CategoriesModel> imageModelArrayList;

    private int[] myImageList = new int[]{R.drawable.clothes_ladies, R.drawable.ladies_watshes,R.drawable.shoes_ladies, R.drawable.hair_ladies};
    private String[] myImageNameList = new String[]{"Clothes","Jewelery" ,"Shoes","Hair"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ladies);

        ImageView Back = findViewById(R.id.back_btn);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.rvCategoriesLadies);

        imageModelArrayList = eatFruits();


        adapter = new MyRecyclerViewAdapter(this, imageModelArrayList);

        recyclerView.setAdapter(adapter);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter.setClickListener(this);
    }


    private ArrayList<CategoriesModel> eatFruits(){

        ArrayList<CategoriesModel> list = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            CategoriesModel fruitModel = new CategoriesModel();
            fruitModel.setName(myImageNameList[i]);
            fruitModel.setImage_drawable(myImageList[i]);
            list.add(fruitModel);


        }

        return list;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
    }

}
