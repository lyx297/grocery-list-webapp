package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class ListInfo extends AbstractEntity {

    @NotEmpty
    private String listName = "";

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}
