package com.example.application.views.list;

import com.example.application.data.entity.GroceryListInfo;
import com.example.application.data.entity.Item;
import com.example.application.data.service.SpringService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;

import java.util.Collection;


@org.springframework.stereotype.Component
@Scope("prototype")
@PageTitle("Main Page")
@Route(value = "", layout = MainLayout.class)
public class MainPage extends VerticalLayout {

    final SpringService service;
    Grid<Item> itemGrid = new Grid<>(Item.class);
    ItemForm itemForm;
    TextField filterText = new TextField();

    GroceryListInfo selectedGroceryListInfo;
    GroceryListInfoForm groceryListInfoForm;

    ComboBox<GroceryListInfo> groceryListInfoDropDown = new ComboBox<>();

    public MainPage(SpringService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();

        groceryListInfoDropDown.setItemLabelGenerator(GroceryListInfo::getGroceryListName);

        configureGrid();
        configureItemForm();
        configureListForm();

        add(
            getToolbar(),
            getContent()
        );

        updateList(null);
        closeEditor();
    }


    private void closeEditor() {
        itemForm.setItem(null);
        itemForm.setVisible(false);
        groceryListInfoForm.setVisible(false);
        removeClassName("editing");
    }

    private void updateList(String value) {
        itemGrid.setItems(service.findAllItems(filterText.getValue()));

        groceryListInfoDropDown.setItems(service.findAllListInfo());

        if (groceryListInfoDropDown.getDataProvider() != null && value != null) {
            ListDataProvider dp = (ListDataProvider) groceryListInfoDropDown.getDataProvider();
            Collection<GroceryListInfo> listInfoGroceryListInfo = dp.getItems();
            listInfoGroceryListInfo.stream()
                    .forEach(groceryListInfo -> {
                if (groceryListInfo.getGroceryListName().equals(value)) {
                    this.selectedGroceryListInfo = groceryListInfo;
                    groceryListInfoDropDown.setLabel("Selected list:" + selectedGroceryListInfo.getGroceryListName());
                }
            });

        } else {
            groceryListInfoDropDown.setLabel("Selected list:");
        }
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(itemGrid, itemForm, groceryListInfoForm);
        content.setFlexGrow(2, itemGrid);
        content.setFlexGrow(1, groceryListInfoForm);
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

    protected void saveItem(ItemForm.SaveEvent event) {
        service.saveItem(event.getItem());
        updateList(null);
        closeEditor();
    }

    private void deleteItem(ItemForm.DeleteEvent event) {
        service.deleteItem(event.getItem());
        updateList(null);
        closeEditor();
    }

    private void configureListForm() {
        groceryListInfoForm = new GroceryListInfoForm();
        groceryListInfoForm.setWidth("25em");

        groceryListInfoForm.addListener(GroceryListInfoForm.SaveEvent.class, this::saveListInfo);
        groceryListInfoForm.addListener(GroceryListInfoForm.DeleteEvent.class, this::deleteListInfo);
        groceryListInfoForm.addListener(GroceryListInfoForm.CloseEvent.class, e -> closeEditor());
    }

    private void saveListInfo(GroceryListInfoForm.SaveEvent event) {
        service.saveListInfo(event.getGroceryListInfo());
        updateList(null);
        closeEditor();
    }

    private void deleteListInfo(GroceryListInfoForm.DeleteEvent event) {
        service.deleteListInfo(event.getGroceryListInfo());
        updateList(null);
        closeEditor();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList(null));

        groceryListInfoDropDown.addValueChangeListener(e-> updateList(e.getValue() != null ? e.getValue().getGroceryListName() : null));

        Button addContactButton = new Button("Add item");
        addContactButton.addClickListener(e -> addItem());

        Button clearSelectedListButton = new Button("Clear list");
        clearSelectedListButton.addClickListener(e -> {
            selectedGroceryListInfo = null;
            updateList(null);
        });

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton, groceryListInfoDropDown, clearSelectedListButton);
        toolbar.addClassName("toolbar");
        toolbar.setAlignItems(Alignment.END);
        return toolbar;
    }

    private void addItem() {
        itemGrid.asSingleSelect().clear();
        editItem(new Item(selectedGroceryListInfo));
    }

    private void addListInfo() {
        itemGrid.asSingleSelect().clear();
        editListInfo(new GroceryListInfo());
    }

    private void editItem(Item item) {
        if(item == null) {
            closeEditor();
        } else {
            groceryListInfoForm.setVisible(false);
            itemForm.setItem(item);
            itemForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void editListInfo(GroceryListInfo groceryListInfo) {
        if(groceryListInfo == null) {
            closeEditor();
        } else {
            itemForm.setVisible(false);
            groceryListInfoForm.setGroceryListInfo(groceryListInfo);
            groceryListInfoForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void configureGrid() {
        itemGrid.addClassName("item-grid");
        itemGrid.setSizeFull();
        itemGrid.setColumns("groceryListName", "itemName", "itemPrice", "itemQty");
        itemGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        // to set up itemForm first before implementing
        itemGrid.asSingleSelect().addValueChangeListener(e -> editItem(e.getValue()));
    }

}
