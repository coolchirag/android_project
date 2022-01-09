package com.example.stockmanagement.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.stockmanagement.R;
import com.example.stockmanagement.dto.ItemDto;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends ArrayAdapter {
    private final List<ItemDto> dataList;
    private final Context context;
    private final int resource;//R.layout.item_list_layout

    public ItemListAdapter(@NonNull Context context, int resource, List<ItemDto> dataList) {
        super(context, resource, dataList);
        this.context=context;
        this.dataList = dataList;
        this.resource = resource;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(this.context);
        final View itemView = inflater.inflate(resource, null, true);
        final ItemDto itemDto = dataList.get(position);
        if(itemDto != null) {
            ((TextView)itemView.findViewById(R.id.item_code)).setText(itemDto.getCode());
            ((TextView)itemView.findViewById(R.id.item_desc)).setText(itemDto.getDescription());
            ((TextView)itemView.findViewById(R.id.item_quantity)).setText(itemDto.getQuantity()+"");
        }
        return itemView;
    }
}
