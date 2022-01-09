package com.example.stockmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stockmanagement.constant.ItemType;
import com.example.stockmanagement.dao.DBHelper;
import com.example.stockmanagement.dao.ItemRepository;
import com.example.stockmanagement.dto.ItemDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddItemScreen extends AppCompatActivity {

    private DBHelper dbHelper = new DBHelper(this);
    private ItemRepository itemRepo = new ItemRepository(dbHelper);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_screen);
        initItemType();

        Button itemSaveButton = findViewById(R.id.item_add_save_button);
        itemSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
            }
        });
        Button itemCancelButton = findViewById(R.id.item_add_cancel_button);
        itemCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToItemListScreen();
            }
        });
    }

    private void saveItem() {
        ItemDto itemDto = new ItemDto();
        itemDto.setCode(((TextView) findViewById(R.id.add_item_code)).getText().toString());
        itemDto.setDescription(((TextView) findViewById(R.id.add_item_desc)).getText().toString());
        itemDto.setType(((Spinner) findViewById(R.id.add_item_type)).getSelectedItem().toString());
        TextView num = (TextView) findViewById(R.id.add_item_quantity);
        CharSequence text = num.getText();
        Integer.parseInt(text.toString());
        itemDto.setQuantity(Integer.parseInt(((TextView) findViewById(R.id.add_item_quantity)).getText().toString()));
        itemRepo.insertItem(itemDto);
        Toast.makeText(this, "Item is Saved", Toast.LENGTH_SHORT).show();
        returnToItemListScreen();
    }

    private void returnToItemListScreen() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void initItemType() {
        Spinner itemTypeSpinner = findViewById(R.id.add_item_type);
        final List<String> itemTypes = new ArrayList<>();
        for (ItemType itemType : ItemType.values()) {
            itemTypes.add(itemType.name());
        }
        itemTypeSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemTypes));
    }
}