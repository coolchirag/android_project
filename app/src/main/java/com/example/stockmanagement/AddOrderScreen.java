package com.example.stockmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stockmanagement.constant.OrderStatusEnum;
import com.example.stockmanagement.constant.OrderTypeEnum;
import com.example.stockmanagement.dao.DBHelper;
import com.example.stockmanagement.dao.ItemRepository;
import com.example.stockmanagement.dao.OrderRepository;
import com.example.stockmanagement.dto.ItemDto;
import com.example.stockmanagement.dto.OrderDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddOrderScreen extends AppCompatActivity {

    private DBHelper dbHelper = new DBHelper(this);
    private ItemRepository itemRepo = new ItemRepository(dbHelper);
    private OrderRepository orderRepo = new OrderRepository(dbHelper);
    private int id;

    private Spinner orderTypeSpinner;
    private Spinner itemSpinner;
    private EditText orderIdEditText;
    private EditText orderQuantityEditText;
    private EditText deliverQuantityEditText;

    private Map<String, Integer> orderTypePositionMap = new HashMap<>();
    private Map<String, Integer> itemPositionMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order_screen);

        this.orderTypeSpinner = findViewById(R.id.add_order_type);
        this.itemSpinner = findViewById(R.id.add_order_item_spinner);
        this.orderIdEditText = findViewById(R.id.add_order_id);
        this.orderQuantityEditText = findViewById(R.id.add_order_quantity);
        this.deliverQuantityEditText = findViewById(R.id.add_deliver_quantity);

        initOrderType();

        id = getIntent().getIntExtra("orderId", 0);
        if(id>0) {
            loadOrderData();
        }
        Button orderSaveButton = findViewById(R.id.order_add_save_button);
        orderSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOrder();
            }
        });

        Button orderCancelButton = findViewById(R.id.order_add_cancel_button);
        orderCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToOrderScreen();
            }
        });

    }

    private void loadOrderData() {
        final OrderDto orderDto = orderRepo.getOrderById(this.id);
        if(orderDto==null) {
            id = 0;
            Toast.makeText(this, "Selected order is not present : "+this.id, Toast.LENGTH_SHORT).show();
            return;
        }
        final Integer orderTypePosition = this.orderTypePositionMap.get(orderDto.getType().name());
        this.orderTypeSpinner.setSelection(orderTypePosition);
        this.orderTypeSpinner.setClickable(false);
        this.orderTypeSpinner.setEnabled(false);

        ItemDto itemDto = itemRepo.getItemsById(orderDto.getItemId());
        final Integer itemPosition = this.itemPositionMap.get(itemDto.getCode());
        this.itemSpinner.setSelection(itemPosition);
        this.itemSpinner.setClickable(false);
        this.itemSpinner.setEnabled(false);

        this.orderIdEditText.setText(orderDto.getOrderId());
        this.orderQuantityEditText.setText(orderDto.getOrderedQuantity());
        this.deliverQuantityEditText.setText(orderDto.getDeliveredQuantity());

    }

    private void returnToOrderScreen() {
        this.id=0;
        startActivity(new Intent(this, OrderScreen.class));
    }

    private void initOrderType() {

        final List<String> itemTypes = new ArrayList<>();
        int index=0;
        for(OrderTypeEnum itemType : OrderTypeEnum.values()) {
            itemTypes.add(itemType.name());
            this.orderTypePositionMap.put(itemType.name(), index);
            index++;
        }
        orderTypeSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemTypes));
        orderTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = parent.getItemAtPosition(position).toString();
                OrderTypeEnum orderType = OrderTypeEnum.valueOf(selectedValue);
                initItemList(orderType);
                if(orderType.equals(OrderTypeEnum.PURCHASE)) {
                    AddOrderScreen.this.deliverQuantityEditText.setEnabled(false);
                } else {
                    AddOrderScreen.this.deliverQuantityEditText.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initItemList(OrderTypeEnum orderType) {

        List<ItemDto> allItems = itemRepo.getAllItemsByType(orderType.getItemType());
        List<String> itemCodes = new ArrayList<>();
        int index = 0;
        for(ItemDto item : allItems) {
            itemCodes.add(item.getCode());
            this.itemPositionMap.put(item.getCode(), index);
            index++;
        }
        itemSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemCodes));
    }

    private void saveOrder() {
        final OrderDto orderDto;
        if(this.id>0) {
            orderDto = orderRepo.getOrderById(this.id);
        } else {
            orderDto = new OrderDto();
        }
        String orderId = (this.orderIdEditText).getText().toString();
        orderDto.setOrderId(orderId);
        OrderTypeEnum orderType = OrderTypeEnum.valueOf(this.orderTypeSpinner.getSelectedItem().toString());
        orderDto.setType(orderType);
        String itemCode = this.itemSpinner.getSelectedItem().toString();
        ItemDto itemDto = itemRepo.getItemsByCode(itemCode);
        orderDto.setItemId(itemDto.getId());
        orderDto.setOrderCode(orderId+"-"+itemCode);
        final Integer orderedQuantity = Integer.parseInt(this.orderQuantityEditText.getText().toString());
        orderDto.setOrderedQuantity(orderedQuantity);

        final Integer deliveredQuantity;
        if(orderType.equals(OrderTypeEnum.PURCHASE)) {
            deliveredQuantity = orderedQuantity;
        } else {
            deliveredQuantity = Integer.parseInt(this.deliverQuantityEditText.getText().toString());
        }

        Integer newlyDeliveredQuantity = deliveredQuantity - orderDto.getDeliveredQuantity();
        orderDto.setDeliveredQuantity(deliveredQuantity);
        if(orderedQuantity<deliveredQuantity) {
            Toast.makeText(this, "OrderQuantity should be greater or equal DeliverQuantity", Toast.LENGTH_SHORT).show();
            return;
        } else if(orderedQuantity>deliveredQuantity){
            orderDto.setStatus(OrderStatusEnum.PENDING);
        } else {
            orderDto.setStatus(OrderStatusEnum.DELIVERED);
        }
        if(this.id>0) {
            orderRepo.updateOrder(orderDto);
        } else {
            orderRepo.insertOrder(orderDto);
        }

        itemRepo.updateItemQuantity(itemDto.getId(), itemDto.getQuantity()-newlyDeliveredQuantity);
        Toast.makeText(this, "Order is Saved", Toast.LENGTH_SHORT).show();
        returnToOrderScreen();
    }


}