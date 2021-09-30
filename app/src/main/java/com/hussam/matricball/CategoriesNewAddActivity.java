package com.hussam.matricball;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.paperdb.Paper;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hussam.matricball.Model.Categories;
import com.hussam.matricball.ViewHolder.UserViewHolder;
import com.squareup.picasso.Picasso;

public class CategoriesNewAddActivity extends AppCompatActivity {

    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider);

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Categories");
        Paper.init(this);


        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Categories> options = new FirebaseRecyclerOptions.Builder<Categories>()
                .setQuery(ProductsRef, Categories.class).build();



        FirebaseRecyclerAdapter<Categories, UserViewHolder> adapter = new FirebaseRecyclerAdapter<Categories, UserViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i, @NonNull final Categories categories) {

                userViewHolder.txtUserName.setText(categories.getPname());
             //   userViewHolder.txtUserPhone.setText("Number : " + users.getPhone());
            //    userViewHolder.txtUserCompany.setText("Company : " + users.getCompany());
                Picasso.with(getApplicationContext()).load(categories.getImage()).into(userViewHolder.imageView);

                userViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CategoriesNewAddActivity.this, AddProductsActivity.class);
                        intent.putExtra("category", "category");
                        intent.putExtra("name",categories.getPname());
                        intent.putExtra("pid", categories.getPid());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_item, parent, false);
                UserViewHolder holder = new UserViewHolder(view);
                return holder;
            }
        };


        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}
