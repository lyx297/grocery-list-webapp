package com.example.application.views.list;

import com.example.application.data.entity.Item;
import com.example.application.data.entity.ListInfo;
import com.example.application.data.service.SpringService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
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
    ItemForm itemForm;
    ListInfoForm listInfoForm;
    TextField filterText = new TextField();
    ComboBox<ListInfo> listInfoDropDown = new ComboBox<>();

    public ListView(SpringService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureItemForm();
        configureListForm();

        add(
            getToolbar(),
            getContent()
        );

        updateList();
        closeEditor();
    }


    private void closeEditor() {
        itemForm.setItem(null);
        itemForm.setVisible(false);
        listInfoForm.setListInfo(null);
        listInfoForm.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        itemGrid.setItems(service.findAllItems(filterText.getValue()));
        listInfoDropDown.setItems(service.findAllListInfo());
        listInfoDropDown.setItemLabelGenerator(ListInfo::getListName);
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(itemGrid, itemForm, listInfoForm);
        content.setFlexGrow(2, itemGrid);
        content.setFlexGrow(1, listInfoForm);
        content.setFlexGrow(1, itemForm);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureItemForm() {
        itemForm = new ItemForm();
        itemForm.setWidth("25em");

        itemForm.addListener(ItemForm.SaveEvent.class, this::saveItem);
        itemForm.addListener(ItemForm.DeleteEvent.class, this::deleteItem);
        itemForm.addListener(ItemForm.CloseEvent.class, e -> closeEditor());
    }

    private void saveItem(ItemForm.SaveEvent event) {
        service.saveItem(event.getItem());
        updateList();
        closeEditor();
    }

    private void deleteItem(ItemForm.DeleteEvent event) {
        service.deleteItem(event.getItem());
        updateList();
        closeEditor();
    }

    private void configureListForm() {
        listInfoForm = new ListInfoForm();
        listInfoForm.setWidth("25em");

        listInfoForm.addListener(ListInfoForm.SaveEvent.class, this::saveListInfo);
        listInfoForm.addListener(ListInfoForm.DeleteEvent.class, this::deleteListInfo);
        listInfoForm.addListener(ListInfoForm.CloseEvent.class, e -> closeEditor());
    }

    private void saveListInfo(ListInfoForm.SaveEvent event) {
        service.saveListInfo(event.getListInfo());
        updateList();
        closeEditor();
    }

    private void deleteListInfo(ListInfoForm.DeleteEvent event) {
        service.deleteListInfo(event.getListInfo());
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

        Button addListButton = new Button("Add list");
        addListButton.addClickListener(e -> addListInfo());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton, listInfoDropDown, addListButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addItem() {
        itemGrid.asSingleSelect().clear();
        editItem(new Item());
    }

    private void addListInfo() {
        itemGrid.asSingleSelect().clear();
        editListInfo(new ListInfo());
    }

    private void editItem(Item item) {
        if(item == null) {
            closeEditor();
        } else {
            listInfoForm.setVisible(false);
            itemForm.setItem(item);
            itemForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void editListInfo(ListInfo listInfo) {
        if(listInfo == null) {
            closeEditor();
        } else {
            itemForm.setVisible(false);
            listInfoForm.setListInfo(listInfo);
            listInfoForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void configureGrid() {
        itemGrid.addClassName("item-grid");
        itemGrid.setSizeFull();
        itemGrid.setColumns("itemName", "itemPrice", "itemQty");
        itemGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        // to set up itemForm first before implementing
        itemGrid.asSingleSelect().addValueChangeListener(e -> editItem(e.getValue()));
    }

}
