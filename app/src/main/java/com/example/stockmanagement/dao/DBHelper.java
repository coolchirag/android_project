package com.example.stockmanagement.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.stockmanagement.constant.ItemType;
import com.example.stockmanagement.dao.constant.ItemMstTableConstant;
import com.example.stockmanagement.dao.constant.OrderMstTableConstant;
import com.example.stockmanagement.dao.constant.ProductionExecutionHistoryTableConstant;
import com.example.stockmanagement.dao.constant.ProductionItemMapTableConstant;
import com.example.stockmanagement.dao.constant.ProductionMstTableConstant;
import com.example.stockmanagement.dto.ItemDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "STOCK_MANAGER";
    private Context context;

    private Map<Integer, List<String>> versionSqlMap = new HashMap<>();


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 2);
        this.context = context;
        versionSqlUpdate();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Toast.makeText(context, "Inside create", Toast.LENGTH_SHORT).show();
        for (List<String> sqls : versionSqlMap.values()) {
            if (sqls != null) {
                for (String sql : sqls) {
                    db.execSQL(sql);
                }
            }
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(context, "Inside upgrade", Toast.LENGTH_SHORT).show();
        boolean equality;
        if (oldVersion < newVersion) {
            for (int i = oldVersion+1; i <= newVersion; i++) {
                List<String> sqls = versionSqlMap.get(i);
                if (sqls != null) {
                    for (String sql : sqls) {
                        db.execSQL(sql);
                    }
                }
            }
        }
    }

    private void versionSqlUpdate() {
        String item_mst_sql = "Create table if not exists " + ItemMstTableConstant.TABLE_NAME + " ( " +
                ItemMstTableConstant.ID + " INTEGER PRIMARY KEY, " +
                ItemMstTableConstant.CODE + " TEXT, " +
                ItemMstTableConstant.DESCRIPTION + " TEXT, " +
                ItemMstTableConstant.TYPE + " TEXT, " +
                ItemMstTableConstant.QUANTITY + " INTEGER, " +
                ItemMstTableConstant.UPDATED_DATE + " TEXT" +
                ")";

        String order_table_sql = "CREATE TABLE if not exists '" + OrderMstTableConstant.TABLE_NAME + "' (" +
                OrderMstTableConstant.ID + "	INTEGER PRIMARY KEY AUTOINCREMENT, " +
                OrderMstTableConstant.ORDER_ID + " TEXT NOT NULL, " +
                OrderMstTableConstant.ORDER_TYPE + " TEXT NOT NULL, " +
                OrderMstTableConstant.ITEM_ID + "	INTEGER NOT NULL," +
                OrderMstTableConstant.ORDER_CODE + " TEXT NOT NULL, " +
                OrderMstTableConstant.ORDERED_QUANTITY + "	INTEGER NOT NULL," +
                OrderMstTableConstant.DELIVERED_QUANTITY + "	INTEGER," +
                OrderMstTableConstant.STATUS + "	TEXT," +
                "FOREIGN KEY(" + OrderMstTableConstant.ITEM_ID + ") REFERENCES " + ItemMstTableConstant.TABLE_NAME + "(" + ItemMstTableConstant.ID + ")" +
                ")";

        String production_table_sql = "CREATE TABLE if not exists " + ProductionMstTableConstant.TABLE_NAME + " ( " +
                ProductionMstTableConstant.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ProductionMstTableConstant.NAME + " TEXT NOT NULL " +
                ")";

        String production_execution_history_sql = "CREATE TABLE if not exists " + ProductionExecutionHistoryTableConstant.TABLE_NAME + " ( " +
                ProductionExecutionHistoryTableConstant.ID + "	INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ProductionExecutionHistoryTableConstant.PRODUCTION_ID + "	INTEGER NOT NULL," +
                ProductionExecutionHistoryTableConstant.QUANTITY + "	INTEGER NOT NULL," +
                ProductionExecutionHistoryTableConstant.UPDATED_DATE + "	TEXT NOT NULL," +
                "FOREIGN KEY(" + ProductionExecutionHistoryTableConstant.PRODUCTION_ID + ") REFERENCES " + ProductionMstTableConstant.TABLE_NAME + "(" + ProductionMstTableConstant.ID + ")" +
                ")";

        String production_item_map_sql = "CREATE TABLE if not exists " + ProductionItemMapTableConstant.TABLE_NAME + " ( " +
                ProductionItemMapTableConstant.ID + "	INTEGER PRIMARY KEY AUTOINCREMENT," +
                ProductionItemMapTableConstant.PRODUCTION_ID + "	INTEGER NOT NULL," +
                ProductionItemMapTableConstant.ITEM_ID + "	INTEGER NOT NULL," +
                ProductionItemMapTableConstant.ITEM_QUANTITY + "	INTEGER NOT NULL," +
                "FOREIGN KEY(" + ProductionItemMapTableConstant.PRODUCTION_ID + ") REFERENCES " + ProductionMstTableConstant.TABLE_NAME + "(" + ProductionMstTableConstant.ID + ")," +
                "FOREIGN KEY(" + ProductionItemMapTableConstant.ITEM_ID + ") REFERENCES " + ItemMstTableConstant.TABLE_NAME + "(" + ItemMstTableConstant.ID + ")" +
                ")";

        versionSqlMap.put(1, Arrays.asList(item_mst_sql, order_table_sql));
        versionSqlMap.put(2, Arrays.asList(production_table_sql, production_execution_history_sql, production_item_map_sql));


    }


}
