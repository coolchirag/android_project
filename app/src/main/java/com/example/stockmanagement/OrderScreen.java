package com.example.stockmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.stockmanagement.constant.ItemType;
import com.example.stockmanagement.constant.OrderTypeEnum;
import com.example.stockmanagement.dao.DBHelper;
import com.example.stockmanagement.dao.ItemRepository;
import com.example.stockmanagement.dao.OrderRepository;
import com.example.stockmanagement.dto.ItemDto;

import java.util.ArrayList;
import java.util.List;

public class OrderScreen extends AppCompatActivity {

    private DBHelper dbHelper = new DBHelper(this);
    private ItemRepository itemRepo = new ItemRepository(dbHelper);
    private OrderRepository orderRepo = new OrderRepository(dbHelper);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_screen);
        initOrderType();



    }

    private void initOrderType() {
        Spinner orderTypeSpinner = findViewById(R.id.add_order_type);
        final List<String> itemTypes = new ArrayList<>();
        for(OrderTypeEnum itemType : OrderTypeEnum.values()) {
            itemTypes.add(itemType.name());
        }
        orderTypeSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemTypes));
        orderTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = parent.getItemAtPosition(position).toString();
                initItemList(OrderTypeEnum.valueOf(selectedValue));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initItemList(OrderTypeEnum orderType) {
        Spinner itemSpinner = findViewById(R.id.add_order_item_spinner);
        List<ItemDto> allItems = itemRepo.getAllItemsByType(orderType.getItemType());
        List<String> itemCodes = new ArrayList<>();
        for(ItemDto item : allItems) {
            itemCodes.add(item.getCode());
        }
        itemSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemCodes));
    }
}