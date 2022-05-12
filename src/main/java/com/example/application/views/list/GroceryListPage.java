package com.example.application.views.list;

import com.example.application.data.entity.GroceryListInfo;
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
import org.springframework.context.annotation.Scope;


@org.springframework.stereotype.Component
@Scope("prototype")
@Route(value = "edit-grocery-lists", layout = MainLayout.class)
@PageTitle("Edit grocery lists | Grocery List Web App")
public class GroceryListPage extends VerticalLayout {
    final SpringService service;

    Grid<GroceryListInfo> listGrid = new Grid<>(GroceryListInfo.class);
    GroceryListInfoForm groceryListInfoForm;

    TextField filterText = new TextField();

    public GroceryListPage(SpringService service) {
        this.service = service;
        addClassName("grocery-list-view");
        setSizeFull();

        configureGrid();
        configureListForm();

        add(
            getToolbar(),
            getContent()
        );

        updateList();
        closeEditor();
    }

    private void configureGrid() {
        listGrid.addClassName("item-grid");
        listGrid.setSizeFull();
        listGrid.setColumns("groceryListName");
        listGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        listGrid.asSingleSelect().addValueChangeListener(e -> editGroceryListInfo(e.getValue()));
    }

    private void editGroceryListInfo(GroceryListInfo groceryListInfo) {
        if(groceryListInfo == null) {
            closeEditor();
        } else {
            groceryListInfoForm.setGroceryListInfo(groceryListInfo);
            groceryListInfoForm.setVisible(true);
            addClassName("editing");
        }
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
        updateList();
        closeEditor();
    }

    private void deleteListInfo(GroceryListInfoForm.DeleteEvent event) {
        service.deleteListInfo(event.getGroceryListInfo());
        updateList();
        closeEditor();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());

        Button addListButton = new Button("Add list");
        addListButton.addClickListener(e -> addListInfo());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addListButton);
        toolbar.addClassName("toolbar");
        toolbar.setAlignItems(Alignment.END);
        return toolbar;
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(listGrid, groceryListInfoForm);
        content.setFlexGrow(2, listGrid);
        content.setFlexGrow(1, groceryListInfoForm);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void addListInfo() {
        listGrid.asSingleSelect().clear();
        editListInfo(new GroceryListInfo());
    }

    private void editListInfo(GroceryListInfo groceryListInfo) {
        if(groceryListInfo == null) {
            closeEditor();
        } else {
            groceryListInfoForm.setGroceryListInfo(groceryListInfo);
            groceryListInfoForm.setVisible(true);
            addClassName("editing");
        }
    }

    public void updateList() {
        listGrid.setItems(service.findAllListInfo(filterText.getValue()));
    }

    private void closeEditor() {
        groceryListInfoForm.setGroceryListInfo(null);
        groceryListInfoForm.setVisible(false);
        removeClassName("editing");
    }
}
