package com.example.application.views.list;

import com.example.application.data.entity.Item;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class ItemForm extends FormLayout {
    Binder<Item> binder = new BeanValidationBinder<>(Item.class);

    TextField itemName = new TextField("Item name");
    NumberField itemPrice = new NumberField("Item price");
    NumberField itemQty = new NumberField("Item quantity");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    private Item item;

    public ItemForm() {
        addClassName("item-form");
        binder.bindInstanceFields(this);

        add(itemName,
            itemPrice,
            itemQty,
            createButtonsLayout());
    }

    public void setItem(Item item) {
        this.item = item;
        binder.readBean(item);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, item)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(item);
            fireEvent(new SaveEvent(this,item));
        } catch (ValidationException e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class ItemFormEvent extends ComponentEvent<ItemForm> {
        private Item item;

        protected ItemFormEvent(ItemForm source, Item item) {
            super(source, false);
            this.item = item;
        }

        public Item getItem() {
            return item;
        }
    }

    public static class SaveEvent extends ItemFormEvent {
        SaveEvent(ItemForm source, Item item) {
            super(source, item);
        }
    }

    public static class DeleteEvent extends ItemFormEvent {
        DeleteEvent(ItemForm source, Item item) {
            super(source, item);
        }

    }

    public static class CloseEvent extends ItemFormEvent {
        CloseEvent(ItemForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}