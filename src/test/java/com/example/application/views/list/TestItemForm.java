package com.example.application.views.list;

import com.example.application.data.entity.Item;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

public class TestItemForm {

    private Item apple;

    @Before
    public void setupData() {
        apple = new Item();
        apple.setItemName("Apple");
        apple.setItemPrice(1.5);
        apple.setItemQty(5);
    }

    @Test
    public void formFieldsPopulated() {
        ItemForm testForm = new ItemForm();
        testForm.setItem(apple);
        Assert.assertEquals("Apple", testForm.itemName.getValue());
        Assert.assertEquals(1.5, testForm.itemPrice.getValue(), 0.01);
        Assert.assertEquals(5, testForm.itemQty.getValue(), 0.1);
    }

    @Test
    public void saveEventHasCorrectValues() {
        ItemForm testForm = new ItemForm();
        Item item = new Item();
        testForm.setItem(item);
        testForm.itemName.setValue("Banana");
        testForm.itemPrice.setValue(2.2);
        testForm.itemQty.setValue(10.0);

        AtomicReference<Item> savedItemListRef = new AtomicReference<>(null);
        testForm.addListener(ItemForm.SaveEvent.class, e -> {
            savedItemListRef.set(e.getItem());
        });
        testForm.save.click();
        Item savedItemList = savedItemListRef.get();

        Assert.assertEquals("Banana", savedItemList.getItemName());
        Assert.assertEquals(2.2, savedItemList.getItemPrice(),0.01);
        Assert.assertEquals(10.0, savedItemList.getItemQty(), 0.1);
    }
}