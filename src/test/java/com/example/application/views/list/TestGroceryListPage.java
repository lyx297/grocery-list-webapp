package com.example.application.views.list;

import com.example.application.data.entity.GroceryListInfo;
import com.example.application.data.repository.GroceryListInfoRepository;
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
public class TestGroceryListPage {

    @Autowired
    private GroceryListPage groceryListPage;

    @Test
    public void listCanBeRetrievedFromGrid() {
        Grid<GroceryListInfo> testGrid = groceryListPage.listGrid;

        GroceryListInfo tesco = new GroceryListInfo();
        tesco.setGroceryListName("tesco");

        groceryListPage.service.saveListInfo(tesco);
        groceryListPage.listGrid.setItems(groceryListPage.service.findAllListInfo());

        GroceryListInfo firstListInGrid = getFirstGroceryList(testGrid);

        Assert.assertEquals("tesco", firstListInGrid.getGroceryListName());
    }

    private GroceryListInfo getFirstGroceryList(Grid<GroceryListInfo> grid) {
        return((ListDataProvider<GroceryListInfo>) grid.getDataProvider()).getItems().iterator().next();
    }
}