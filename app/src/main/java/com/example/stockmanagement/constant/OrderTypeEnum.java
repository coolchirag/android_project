package com.example.stockmanagement.constant;

public enum OrderTypeEnum {
    PURCHASE(ItemType.RAW_MATERIAL),
    SALES(ItemType.PRODUCT);

    private ItemType itemType;

    OrderTypeEnum(ItemType itemType) {
        this.itemType = itemType;
    }

    public ItemType getItemType() {
        return itemType;
    }
}
