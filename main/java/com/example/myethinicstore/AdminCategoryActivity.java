package com.example.myethinicstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView dress, dupatta, kurta, nehruJecket, pyjamas, sarees, sherwani, wKurtaSets, wNehruJackets, kurtaSets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        dress = findViewById(R.id.ivDresses);
        dupatta = findViewById(R.id.ivDupatta);
        kurta = findViewById(R.id.ivKurta);
        nehruJecket = findViewById(R.id.ivNehruJecket);
        pyjamas = findViewById(R.id.ivPyjamas);
        sarees = findViewById(R.id.ivSarees);
        sherwani = findViewById(R.id.ivSherwani);
        wKurtaSets = findViewById(R.id.ivWKurtaSets);
        wNehruJackets = findViewById(R.id.ivWNehruJacket);
        kurtaSets = findViewById(R.id.ivKurtaSets);

        kurta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Kurta");
                startActivity(intent);
            }
        });

        kurtaSets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","KurtaSets");
                startActivity(intent);
            }
        });

        nehruJecket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","NehruJecket");
                startActivity(intent);
            }
        });

        pyjamas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Pyjamas");
                startActivity(intent);
            }
        });

        sherwani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Sherwani");
                startActivity(intent);
            }
        });

        wKurtaSets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","WKurtaSets");
                startActivity(intent);
            }
        });

        dress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Dress");
                startActivity(intent);
            }
        });

        dupatta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Dupatta");
                startActivity(intent);
            }
        });

        wNehruJackets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","WNehruJeckets");
                startActivity(intent);
            }
        });

        sarees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Sarees");
                startActivity(intent);
            }
        });
    }
}
