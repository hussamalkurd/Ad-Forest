package com.hussam.matricball;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hussam.matricball.Mens.Clothes.ClothesDetailsActivity;
import com.hussam.matricball.Mens.Clothes.ClothesMensActivity;
import com.hussam.matricball.Model.CategoriesModel;
import com.hussam.matricball.Model.Products;
import com.hussam.matricball.ViewHolder.MyRecyclerViewAdapter;
import com.hussam.matricball.ViewHolder.ProductViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewProductsActivity extends AppCompatActivity {

    private String Cname;
    private DatabaseReference ProductsRef;
    private MyRecyclerViewAdapter adapter;
    private ArrayList<CategoriesModel> imageModelArrayList;
    RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    //private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Cname = getIntent().getExtras().get("name").toString();
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products").child(Cname);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_products);



        recyclerView = findViewById(R.id.recycler_new_products);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(ProductsRef, Products.class).build();


        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull final Products products) {

                productViewHolder.txtProductName.setText(products.getPname());
                productViewHolder.txtProductDescription.setText(products.getDescription());
                productViewHolder.txtProductPrice.setText("Price = " + products.getPrice() + "$");
                Picasso.with(getApplicationContext()).load(products.getImage()).into(productViewHolder.imageView);

                productViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(NewProductsActivity.this, ProductDetailsActivity.class);
                        intent.putExtra("pid", products.getPid());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return holder;
            }
        };


        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

}