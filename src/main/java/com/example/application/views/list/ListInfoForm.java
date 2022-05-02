package com.example.application.views.list;

import com.example.application.data.entity.ListInfo;
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

public class ListInfoForm extends FormLayout {
    Binder<ListInfo> binder = new BeanValidationBinder<>(ListInfo.class);

    TextField listName = new TextField("List name");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    private ListInfo listInfo;

    public ListInfoForm() {
        addClassName("listInfo-form");
        binder.bindInstanceFields(this);

        add(listName,
            createButtonsLayout());
    }

    public void setListInfo(ListInfo listInfo) {
        this.listInfo = listInfo;
        binder.readBean(listInfo);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, listInfo)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(listInfo);
            fireEvent(new SaveEvent(this,listInfo));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class ListInfoFormEvent extends ComponentEvent<ListInfoForm> {
        private ListInfo listInfo;

        protected ListInfoFormEvent(ListInfoForm source, ListInfo listInfo) {
            super(source, false);
            this.listInfo = listInfo;
        }

        public ListInfo getListInfo() {
            return listInfo;
        }
    }

    public static class SaveEvent extends ListInfoFormEvent {
        SaveEvent(ListInfoForm source, ListInfo listInfo) {
            super(source, listInfo);
        }
    }

    public static class DeleteEvent extends ListInfoFormEvent {
        DeleteEvent(ListInfoForm source, ListInfo listInfo) {
            super(source, listInfo);
        }

    }

    public static class CloseEvent extends ListInfoFormEvent {
        CloseEvent(ListInfoForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}