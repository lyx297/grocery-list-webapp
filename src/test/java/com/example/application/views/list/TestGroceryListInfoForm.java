package com.example.application.views.list;

import com.example.application.data.entity.GroceryListInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

public class TestGroceryListInfoForm {

    private GroceryListInfo walmart;

    @Before
    public void setupData() {
        walmart = new GroceryListInfo();
        walmart.setGroceryListName("Walmart");
    }

    @Test
    public void testValidateAndSaveForValueSavedInFields() {
        GroceryListInfoForm testForm = new GroceryListInfoForm();
        testForm.setGroceryListInfo(walmart);
        Assert.assertEquals("Walmart", testForm.groceryListName.getValue());
    }

    @Test
    public void testSaveEventHasCorrectValues() {
        GroceryListInfoForm testForm = new GroceryListInfoForm();
        GroceryListInfo groceryListInfo = new GroceryListInfo();
        testForm.setGroceryListInfo(groceryListInfo);
        testForm.groceryListName.setValue("Tesco");

        AtomicReference<GroceryListInfo> savedGroceryListInfoRef = new AtomicReference<>(null);
        testForm.addListener(GroceryListInfoForm.SaveEvent.class, e -> {
            savedGroceryListInfoRef.set(e.getGroceryListInfo());
        });
        testForm.save.click();
        GroceryListInfo savedGroceryListInfo = savedGroceryListInfoRef.get();

        Assert.assertEquals("Tesco", savedGroceryListInfo.getGroceryListName());
    }
}