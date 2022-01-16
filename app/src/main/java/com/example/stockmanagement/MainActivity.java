package com.example.stockmanagement;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.stockmanagement.R;
import com.example.stockmanagement.dao.DBHelper;
import com.example.stockmanagement.dao.ItemRepository;
import com.example.stockmanagement.dto.ItemDto;
import com.example.stockmanagement.view.ItemListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper = new DBHelper(this);
    private ItemRepository itemRepo = new ItemRepository(dbHelper);

    private ListView itemListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //dbHelper = new DBHelper(this);
        this.itemListView = findViewById(R.id.itemListView);

        FloatingActionButton addItemButton = findViewById(R.id.item_add_button);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddItemScreen.class));
            }
        });

        findViewById(R.id.order_screen_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OrderScreen.class));
            }
        });

        findViewById(R.id.production_screen_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProductionScreen.class));
            }
        });

        loadItemList();
        this.itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "You have clicked on position : " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void loadItemList() {
        List<ItemDto> allItems = itemRepo.getAllItems();
        this.itemListView.setAdapter(new ItemListAdapter(this, R.layout.item_list_layout, allItems));


    }
}