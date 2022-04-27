package com.example.application.views.list;

import com.example.application.data.entity.Item;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Main")
@Route(value = "", layout = MainLayout.class)
public class ListView extends VerticalLayout {

    Grid<Item> itemGrid = new Grid<>(Item.class);

    public ListView() {
        addClassName("list-view");
        setSizeFull();

        configureGrid();

        add(itemGrid);
    }

    private void configureGrid() {
        itemGrid.addClassName("item-grid");
        itemGrid.setSizeFull();
        itemGrid.setColumns("listID", "itemID", "itemName", "itemPrice", "itemQuantity");
        itemGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        // to set up form first before implementing
        // itemGrid.asSingleSelect().addValueChangeListener(e -> editContact(e.getValue()));
    }



}
