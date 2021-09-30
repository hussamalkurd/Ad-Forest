package com.hussam.matricball.Mens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.hussam.matricball.Mens.Clothes.ClothesMensAdminActivity;
import com.hussam.matricball.Mens.Hair.HairMensAdminActivity;
import com.hussam.matricball.Mens.Shoes.ShoesMensAdminActivity;
import com.hussam.matricball.Mens.Watches.WatchesMensAdminActivity;
import com.hussam.matricball.Model.CategoriesModel;
import com.hussam.matricball.R;
import com.hussam.matricball.ViewHolder.MyRecyclerViewAdapter;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MensAdminActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
    private MyRecyclerViewAdapter adapter;
    private ArrayList<CategoriesModel> imageModelArrayList;

    private int[] myImageList = new int[]{R.drawable.clothes_men, R.drawable.watshes_mens,R.drawable.shoes_men, R.drawable.hair_mens};
    private String[] myImageNameList = new String[]{"Clothes","Watches" ,"Shoes","Hair"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mens_admin);

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

            Intent intent = new Intent(MensAdminActivity.this, ClothesMensAdminActivity.class);
            intent.putExtra("category", "clothes");
            startActivity(intent);
        }else if (position == 1){
            Intent intent = new Intent(MensAdminActivity.this, WatchesMensAdminActivity.class);
            intent.putExtra("category", "watches");
            startActivity(intent);

        }else if (position == 2){
            Intent intent = new Intent(MensAdminActivity.this, ShoesMensAdminActivity.class);
            intent.putExtra("category", "shoes");
            startActivity(intent);
        }else if (position == 3){
            Intent intent = new Intent(MensAdminActivity.this, HairMensAdminActivity.class);
            intent.putExtra("category", "hair");
            startActivity(intent);
        }
    }

}
