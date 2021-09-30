package com.hussam.matricball;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hussam.matricball.Ladies.LadiesActivity;
import com.hussam.matricball.Mens.MensActivity;
import com.hussam.matricball.Model.Categories;
import com.hussam.matricball.Model.CategoriesModel;
import com.hussam.matricball.Model.Products;
import com.hussam.matricball.Prevalent.Prevalent;
import com.hussam.matricball.ViewHolder.MyRecyclerViewAdapter;
import com.hussam.matricball.ViewHolder.ProductViewHolder;
import com.hussam.matricball.ViewHolder.UserViewHolder;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeActivityNew extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,MyRecyclerViewAdapter.ItemClickListener{



    private DatabaseReference ProductsRef;

    RecyclerView.LayoutManager layoutManager;
    //private AppBarConfiguration mAppBarConfiguration;
    private MyRecyclerViewAdapter adapter;
    private ArrayList<CategoriesModel> imageModelArrayList;

    private int[] myImageList = new int[]{R.drawable.mens_new, R.drawable.ladies_new,R.drawable.car_neww, R.drawable.food_new};
    private String[] myImageNameList = new String[]{"Men","Ladies" ,"Cars","Foods"};
    LinearLayout mensImage , ladiesImage;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //get from Firebase from products field
        //ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");


        Paper.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_new);


        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Categories");
        Paper.init(this);


        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

       /* Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
*/

        //to display the username
        //View headerView = navigationView.getHeaderView(0);
        //TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        //CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);

        //to display the username
       // userNameTextView.setText(Prevalent.currentOnlineUsers.getName());
        //userNameTextView.setText(Prevalent.currentOnlineUsers.getName());
        //Picasso.with(this).load(Prevalent.currentOnlineUsers.getImage()).placeholder(R.drawable.profile).into(profileImageView);


        /*recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

*/
       /* ImageView mens = findViewById(R.id.mens);
        mens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MensActivity.class);
                startActivity(intent);



            }
        });*/


        mensImage = findViewById(R.id.mens);
        ladiesImage = findViewById(R.id.ladies);

        mensImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivityNew.this, MensActivity.class);
                startActivity(intent);
            }
        });

        ladiesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivityNew.this, LadiesActivity.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.rvCategories);

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

            Intent intent = new Intent(HomeActivityNew.this,MensActivity.class);
            startActivity(intent);

        }else if (position == 1){
            Intent intent = new Intent(HomeActivityNew.this,LadiesActivity.class);
            startActivity(intent);

        }
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
                        Intent intent = new Intent(HomeActivityNew.this, NewProductsActivity.class);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
