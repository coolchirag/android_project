package com.example.stockmanagement.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.stockmanagement.constant.OrderStatusEnum;
import com.example.stockmanagement.dao.constant.OrderMstTableConstant;
import com.example.stockmanagement.dto.OrderDto;

public class OrderRepository {

    private final DBHelper dbHelper;

    public OrderRepository(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public boolean insertOrder(OrderDto orderDto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(OrderMstTableConstant.ORDER_ID, orderDto.getOrderId());
        value.put(OrderMstTableConstant.ORDER_TYPE, orderDto.getType().name());
        value.put(OrderMstTableConstant.ITEM_ID, orderDto.getItemId());
        value.put(OrderMstTableConstant.ORDERED_QUANTITY, orderDto.getOrderedQuantity());
        value.put(OrderMstTableConstant.DELIVERED_QUANTITY, orderDto.getDeliveredQuantity());
        value.put(OrderMstTableConstant.STATUS, orderDto.getStatus().name());
        db.insert(OrderMstTableConstant.TABLE_NAME, null, value);
        return true;
    }

    public boolean updateOrderDeliveredQuantity(Integer id, Integer deliveredQuantity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("update " + OrderMstTableConstant.TABLE_NAME + " set " + OrderMstTableConstant.DELIVERED_QUANTITY + " = " + OrderMstTableConstant.DELIVERED_QUANTITY + " + " + deliveredQuantity + " where id = " + id);
        return true;
    }


}
