package com.example.stockmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.stockmanagement.constant.ItemType;
import com.example.stockmanagement.constant.OrderTypeEnum;
import com.example.stockmanagement.dao.DBHelper;
import com.example.stockmanagement.dao.ItemRepository;
import com.example.stockmanagement.dao.OrderRepository;
import com.example.stockmanagement.dto.ItemDto;
import com.example.stockmanagement.view.DeliveredOrderFragment;
import com.example.stockmanagement.view.PendingOrderFragment;
import com.example.stockmanagement.view.PurchaseOrderFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class OrderScreen extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    private static final String SALES_PENDING="Sales Pending";
    private static final String SALES_DELIVERED="Sales Delivered";
    private static final String PURCHASE="Purchase";

    private PendingOrderFragment pendingOrderFragment;
    private DeliveredOrderFragment deliveredOrderFragment;
    private PurchaseOrderFragment purchaseOrderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_screen);

        pendingOrderFragment = new PendingOrderFragment();
        deliveredOrderFragment = new DeliveredOrderFragment();
        purchaseOrderFragment = new PurchaseOrderFragment();

        tabLayout = findViewById(R.id.order_tabLayout);



        TabLayout.Tab firstTable = tabLayout.newTab().setText(SALES_PENDING);
        tabLayout.addTab(firstTable);
        tabLayout.addTab(tabLayout.newTab().setText(SALES_DELIVERED));
        tabLayout.addTab((tabLayout.newTab().setText(PURCHASE)));
        setTabContent(firstTable);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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


        FloatingActionButton addOrderButton = findViewById(R.id.add_order_button);
        addOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addOrderIntent = new Intent(OrderScreen.this, AddOrderScreen.class);
                startActivity(addOrderIntent);
            }
        });

        findViewById(R.id.stock_screen_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderScreen.this, MainActivity.class));
            }
        });

        findViewById(R.id.production_screen_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderScreen.this, ProductionScreen.class));
            }
        });
    }

    private void setTabContent(TabLayout.Tab tab) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (tab.getText().toString()) {
            case SALES_PENDING :
                fragmentTransaction.replace(R.id.fragmentContainer, pendingOrderFragment);
                break;
            case SALES_DELIVERED:
                fragmentTransaction.replace(R.id.fragmentContainer, deliveredOrderFragment);
                break;
            case PURCHASE:
                fragmentTransaction.replace(R.id.fragmentContainer, purchaseOrderFragment);
                break;
        }

        fragmentTransaction.commit();
    }
}