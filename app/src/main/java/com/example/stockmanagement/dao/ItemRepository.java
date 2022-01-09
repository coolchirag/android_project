package com.example.stockmanagement.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.stockmanagement.constant.ItemType;
import com.example.stockmanagement.dao.constant.ItemMstTableConstant;
import com.example.stockmanagement.dao.constant.OrderMstTableConstant;
import com.example.stockmanagement.dto.ItemDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemRepository {

    private final DBHelper dbHelper;

    public ItemRepository(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public boolean insertItem(ItemDto itemDto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ItemMstTableConstant.CODE, itemDto.getCode());
        contentValues.put(ItemMstTableConstant.DESCRIPTION, itemDto.getDescription());
        contentValues.put(ItemMstTableConstant.TYPE, itemDto.getType());
        contentValues.put(ItemMstTableConstant.QUANTITY, itemDto.getQuantity());
        contentValues.put(ItemMstTableConstant.UPDATED_DATE, (new Date()).toString());
        db.insert(ItemMstTableConstant.TABLE_NAME, null, contentValues);

        return true;
    }

    public List<ItemDto> getAllItems() {
        final List<ItemDto> items = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + ItemMstTableConstant.TABLE_NAME, null);
        cursor.moveToFirst();
        int idColumnIndex = cursor.getColumnIndex(ItemMstTableConstant.ID);
        int codeColumnIndex = cursor.getColumnIndex(ItemMstTableConstant.CODE);
        int descColumnIndex = cursor.getColumnIndex(ItemMstTableConstant.DESCRIPTION);
        int quantityColumnIndex = cursor.getColumnIndex(ItemMstTableConstant.QUANTITY);


        while (cursor.isAfterLast() == false) {
            ItemDto item = new ItemDto();
            item.setId(cursor.getInt(idColumnIndex));
            item.setCode(cursor.getString(codeColumnIndex));
            item.setDescription(cursor.getString(descColumnIndex));
            item.setQuantity(cursor.getInt(quantityColumnIndex));

            items.add(item);
            cursor.moveToNext();
        }
        return items;
    }

    public List<ItemDto> getAllItemsByType(ItemType itemType) {
        final List<ItemDto> items = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " + ItemMstTableConstant.TABLE_NAME + " where " + ItemMstTableConstant.TYPE + " = " + itemType.name(), null);
        cursor.moveToFirst();
        int idColumnIndex = cursor.getColumnIndex(ItemMstTableConstant.ID);
        int codeColumnIndex = cursor.getColumnIndex(ItemMstTableConstant.CODE);
        int descColumnIndex = cursor.getColumnIndex(ItemMstTableConstant.DESCRIPTION);
        int quantityColumnIndex = cursor.getColumnIndex(ItemMstTableConstant.QUANTITY);


        while (cursor.isAfterLast() == false) {
            ItemDto item = new ItemDto();
            item.setId(cursor.getInt(idColumnIndex));
            item.setCode(cursor.getString(codeColumnIndex));
            item.setDescription(cursor.getString(descColumnIndex));
            item.setQuantity(cursor.getInt(quantityColumnIndex));

            items.add(item);
            cursor.moveToNext();
        }
        return items;
    }

    public boolean updateItemQuantity(Integer id, Integer quantity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("update " + ItemMstTableConstant.TABLE_NAME + " set " + ItemMstTableConstant.QUANTITY + " = " + ItemMstTableConstant.QUANTITY + " + " + quantity + " where id = " + id);
        return true;
    }
}