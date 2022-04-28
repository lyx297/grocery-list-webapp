package com.example.application.views.list;

import com.example.application.data.entity.Item;
import com.example.application.data.service.SpringService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Main")
@Route(value = "", layout = MainLayout.class)
public class ListView extends VerticalLayout {

    private final SpringService service;
    Grid<Item> itemGrid = new Grid<>(Item.class);
    ItemForm form;
    TextField filterText = new TextField();

    public ListView(SpringService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureForm();

        add(
            getToolbar(),
            getContent()
        );

        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setItem(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        itemGrid.setItems(service.findAllItems(filterText.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(itemGrid, form);
        content.setFlexGrow(2, itemGrid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureForm() {
        form = new ItemForm();
        form.setWidth("25em");

        form.addListener(ItemForm.SaveEvent.class, this::saveItem);
        form.addListener(ItemForm.DeleteEvent.class, this::deleteItem);
        form.addListener(ItemForm.CloseEvent.class, e -> closeEditor());
    }

    private void saveItem(ItemForm.SaveEvent event) {
        service.saveItem(event.getItem());
        updateList();
        closeEditor();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add item");
        addContactButton.addClickListener(e -> addItem());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addItem() {
        itemGrid.asSingleSelect().clear();
        editItem(new Item());
    }


    private void configureGrid() {
        itemGrid.addClassName("item-grid");
        itemGrid.setSizeFull();
        itemGrid.setColumns("itemName", "itemPrice", "itemQty");
        itemGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        // to set up form first before implementing
        itemGrid.asSingleSelect().addValueChangeListener(e -> editItem(e.getValue()));
    }

    private void deleteItem(ItemForm.DeleteEvent event) {
        service.deleteItem(event.getItem());
        updateList();
        closeEditor();
    }

    private void editItem(Item item) {
        if(item == null) {
            closeEditor();
        } else {
            form.setItem(item);
            form.setVisible(true);
            addClassName("editing");
        }
    }

}
