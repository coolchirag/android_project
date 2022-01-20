package com.example.stockmanagement;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

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
import com.example.stockmanagement.view.ProductItemFragment;
import com.example.stockmanagement.view.RawItemFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProductItemFragment productItemFragment;
    private RawItemFragment rawItemFragment;

    private static final String PRODUCT_TAB = "Product";
    private static final String RAW_MATERIAL_TAB = "Raw Material";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productItemFragment = new ProductItemFragment();
        rawItemFragment = new RawItemFragment();

        TabLayout itemTabLayout = findViewById(R.id.item_tab_layout);
        TabLayout.Tab firstTab = itemTabLayout.newTab().setText(PRODUCT_TAB);
        itemTabLayout.addTab(firstTab);
        itemTabLayout.addTab(itemTabLayout.newTab().setText(RAW_MATERIAL_TAB));
        //itemTabLayout.selectTab(firstTab);
        setTabContent(firstTab);

        itemTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setTabContent(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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

    }




    private void setTabContent(TabLayout.Tab tab) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (tab.getText().toString()) {
            case PRODUCT_TAB:
                fragmentTransaction.replace(R.id.item_fragment_container, productItemFragment);
                break;
            case RAW_MATERIAL_TAB:
                fragmentTransaction.replace(R.id.item_fragment_container, rawItemFragment);
                break;
        }

        fragmentTransaction.commit();
    }
}