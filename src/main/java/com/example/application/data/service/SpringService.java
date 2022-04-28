package com.example.application.data.service;

import com.example.application.data.entity.Item;
import com.example.application.data.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpringService {

    private final ItemRepository itemRepository;

    public SpringService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> findAllItems(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return itemRepository.findAll();
        } else {
            return itemRepository.search(stringFilter);
        }
    }

    public long countItems() {
        return itemRepository.count();
    }

    public void deleteItem(Item item) {
        itemRepository.delete(item);
    }

    public void saveItem(Item item) {
        if(item == null) {
            System.err.println("Item is null.");
            return;
        }
        itemRepository.save(item);
    }


}
