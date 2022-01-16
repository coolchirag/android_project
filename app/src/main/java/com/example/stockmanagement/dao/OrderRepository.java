package com.example.stockmanagement.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.stockmanagement.constant.OrderStatusEnum;
import com.example.stockmanagement.constant.OrderTypeEnum;
import com.example.stockmanagement.dao.constant.OrderMstTableConstant;
import com.example.stockmanagement.dto.OrderDto;

import java.util.ArrayList;
import java.util.List;

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
        value.put(OrderMstTableConstant.ORDER_CODE, orderDto.getOrderCode());
        value.put(OrderMstTableConstant.ORDERED_QUANTITY, orderDto.getOrderedQuantity());
        value.put(OrderMstTableConstant.DELIVERED_QUANTITY, orderDto.getDeliveredQuantity());
        value.put(OrderMstTableConstant.STATUS, orderDto.getStatus().name());
        db.insert(OrderMstTableConstant.TABLE_NAME, null, value);
        return true;
    }

    public boolean updateOrder(OrderDto orderDto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("update '" + OrderMstTableConstant.TABLE_NAME + "' set "+OrderMstTableConstant.ORDER_ID+"=?"+OrderMstTableConstant.ORDERED_QUANTITY+"=?"+ OrderMstTableConstant.DELIVERED_QUANTITY + "=?  where id =?"+new String[]{String.valueOf(orderDto.getOrderId()), String.valueOf(orderDto.getOrderedQuantity()), String.valueOf(orderDto.getDeliveredQuantity()), String.valueOf(orderDto.getId())});
        db.close();
        return true;
    }

    public OrderDto getOrderById(Integer id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from '" + OrderMstTableConstant.TABLE_NAME + "' where " + OrderMstTableConstant.ID + "=?", new String[]{String.valueOf(id)});

        List<OrderDto> result = mapCursorToOrderDtos(cursor);
        db.close();
        OrderDto dto = null;
        if(result!= null && result.size()>0) {
            dto = result.get(0);
        }
        return dto;
    }

    public List<OrderDto> getOrderByType(OrderTypeEnum orderType) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from '" + OrderMstTableConstant.TABLE_NAME + "' where " + OrderMstTableConstant.ORDER_TYPE + "=?", new String[]{orderType.name()});

        List<OrderDto> result = mapCursorToOrderDtos(cursor);
        db.close();
        return result;
    }

    public List<OrderDto> getOrderByTypeAndStatus(OrderTypeEnum orderType, OrderStatusEnum status) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from '" + OrderMstTableConstant.TABLE_NAME + "' where " + OrderMstTableConstant.ORDER_TYPE + "=? and "+OrderMstTableConstant.STATUS+" =?", new String[]{orderType.name(), status.name()});
        List<OrderDto> result = mapCursorToOrderDtos(cursor);
        db.close();
        return result;
    }

    private List<OrderDto> mapCursorToOrderDtos(Cursor cursor) {
        final List<OrderDto> orderDtos = new ArrayList<>();
        cursor.moveToFirst();

        int idColumnIndex = cursor.getColumnIndex(OrderMstTableConstant.ID);
        int orderIdColumnIndex = cursor.getColumnIndex(OrderMstTableConstant.ORDER_ID);
        int typeColumnIndex = cursor.getColumnIndex(OrderMstTableConstant.ORDER_TYPE);
        int itemIdColumnIndex = cursor.getColumnIndex(OrderMstTableConstant.ITEM_ID);
        int orderCodeColumnIndex = cursor.getColumnIndex(OrderMstTableConstant.ORDER_CODE);
        int orderedQuantityColumnIndex = cursor.getColumnIndex(OrderMstTableConstant.ORDERED_QUANTITY);
        int deliveredQuantityColumnIndex = cursor.getColumnIndex(OrderMstTableConstant.DELIVERED_QUANTITY);
        int statusColumnIndex = cursor.getColumnIndex(OrderMstTableConstant.STATUS);

        while(!cursor.isAfterLast()) {
            OrderDto orderDto = new OrderDto();
            orderDto.setId(cursor.getInt(idColumnIndex));
            orderDto.setOrderId(cursor.getString(orderIdColumnIndex));
            orderDto.setType(OrderTypeEnum.valueOf(cursor.getString(typeColumnIndex)));
            orderDto.setItemId(cursor.getInt(itemIdColumnIndex));
            orderDto.setOrderCode(cursor.getString(orderCodeColumnIndex));
            orderDto.setOrderedQuantity(cursor.getInt(orderedQuantityColumnIndex));
            orderDto.setDeliveredQuantity(cursor.getInt(deliveredQuantityColumnIndex));
            orderDto.setStatus(OrderStatusEnum.valueOf(cursor.getString(statusColumnIndex)));

            orderDtos.add(orderDto);
            cursor.moveToNext();
        }
        return orderDtos;
    }


}
