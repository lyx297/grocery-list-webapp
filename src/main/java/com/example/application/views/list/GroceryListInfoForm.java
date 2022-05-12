package com.example.application.views.list;

import com.example.application.data.entity.GroceryListInfo;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class GroceryListInfoForm extends FormLayout {
    Binder<GroceryListInfo> binder = new BeanValidationBinder<>(GroceryListInfo.class);

    public TextField groceryListName = new TextField("Grocery list name");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    private GroceryListInfo groceryListInfo;

    public GroceryListInfoForm() {
        addClassName("groceryListInfo-form");
        binder.bindInstanceFields(this);

        add(groceryListName,
            createButtonsLayout());
    }

    public void setGroceryListInfo(GroceryListInfo groceryListInfo) {
        this.groceryListInfo = groceryListInfo;
        binder.readBean(groceryListInfo);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, groceryListInfo)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(groceryListInfo);
            fireEvent(new SaveEvent(this, groceryListInfo));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class ListInfoFormEvent extends ComponentEvent<GroceryListInfoForm> {
        private GroceryListInfo groceryListInfo;

        protected ListInfoFormEvent(GroceryListInfoForm source, GroceryListInfo groceryListInfo) {
            super(source, false);
            this.groceryListInfo = groceryListInfo;
        }

        public GroceryListInfo getGroceryListInfo() {
            return groceryListInfo;
        }
    }

    public static class SaveEvent extends ListInfoFormEvent {
        SaveEvent(GroceryListInfoForm source, GroceryListInfo groceryListInfo) {
            super(source, groceryListInfo);
        }
    }

    public static class DeleteEvent extends ListInfoFormEvent {
        DeleteEvent(GroceryListInfoForm source, GroceryListInfo groceryListInfo) {
            super(source, groceryListInfo);
        }

    }

    public static class CloseEvent extends ListInfoFormEvent {
        CloseEvent(GroceryListInfoForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}