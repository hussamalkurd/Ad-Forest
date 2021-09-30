package com.hussam.matricball.Mens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.hussam.matricball.Mens.Clothes.ClothesMensActivity;
import com.hussam.matricball.Mens.Hair.HairMensActivity;
import com.hussam.matricball.Mens.Shoes.ShoesMensActivity;
import com.hussam.matricball.Mens.Watches.WatchesMensActivity;
import com.hussam.matricball.Model.CategoriesModel;
import com.hussam.matricball.R;
import com.hussam.matricball.ViewHolder.MyRecyclerViewAdapter;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MensActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
    private MyRecyclerViewAdapter adapter;
    private ArrayList<CategoriesModel> imageModelArrayList;

    private int[] myImageList = new int[]{R.drawable.clothes_men, R.drawable.watshes_mens,R.drawable.shoes_men, R.drawable.hair_mens};
    private String[] myImageNameList = new String[]{"Clothes","Watches" ,"Shoes","Hair"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mens);

        ImageView Back = findViewById(R.id.back_btn);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        RecyclerView recyclerView = findViewById(R.id.rvCategoriesMens);

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
        if (position == 0){

            Intent intent = new Intent(MensActivity.this, ClothesMensActivity.class);
            startActivity(intent);
        }else if (position == 1){
            Intent intent = new Intent(MensActivity.this, WatchesMensActivity.class);
            startActivity(intent);

        }else if (position == 2){
            Intent intent = new Intent(MensActivity.this, ShoesMensActivity.class);
            startActivity(intent);

        }else if (position == 3){
            Intent intent = new Intent(MensActivity.this, HairMensActivity.class);
            startActivity(intent);

        }
    }

}
