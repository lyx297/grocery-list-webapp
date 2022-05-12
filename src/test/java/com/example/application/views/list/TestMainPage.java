package com.example.application.views.list;

import com.example.application.data.entity.GroceryListInfo;
import com.example.application.data.entity.Item;
import com.example.application.data.repository.ItemRepository;
import com.example.application.data.service.SpringService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMainPage {

    @Autowired
    private MainPage mainPage;

    @Autowired
    private ItemRepository repository;

    @Autowired
    private SpringService service;

    Grid<Item> grid;

    @Test
    public void itemCanBeRetrievedFromGrid() {
        this.grid = mainPage.itemGrid;

        GroceryListInfo tesco = new GroceryListInfo();
        tesco.setGroceryListName("tesco");

        Item banana = new Item();
        banana.setGroceryListName(tesco.getGroceryListName());
        banana.setItemName("Banana");
        banana.setItemPrice(2.2);
        banana.setItemQty(10);

        mainPage.service.saveItem(banana);
        mainPage.itemGrid.setItems(mainPage.service.findAllItems(""));

        Item firstItemInGrid = getFirstItem(grid);

        Assert.assertEquals("Banana", firstItemInGrid.getItemName());
        Assert.assertEquals(2.2, firstItemInGrid.getItemPrice(), 0.01);
        Assert.assertEquals(10, firstItemInGrid.getItemQty(),0.1);
    }

    private Item getFirstItem(Grid<Item> grid) {
        return( (ListDataProvider<Item>) grid.getDataProvider()).getItems().iterator().next();
    }


}