package com.example.application.data.service;

import com.example.application.data.entity.GroceryListInfo;
import com.example.application.data.entity.Item;
import com.example.application.data.repository.ItemRepository;
import com.example.application.data.repository.GroceryListInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpringService {

    final ItemRepository itemRepository;
    final GroceryListInfoRepository groceryListInfoRepository;

    public SpringService(ItemRepository itemRepository,
                        GroceryListInfoRepository groceryListInfoRepository
    ) {
        this.itemRepository = itemRepository;
        this.groceryListInfoRepository = groceryListInfoRepository;
    }

    public List<Item> findAllItems(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return itemRepository.findAll();
        } else {
            return itemRepository.searchBasedOnItemName(stringFilter);
        }
    }



    public List<GroceryListInfo> findAllListInfo(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return groceryListInfoRepository.findAll();
        } else {
            return groceryListInfoRepository.search(stringFilter);
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

    public List<GroceryListInfo> findAllListInfo() {
        return groceryListInfoRepository.findAll();
    }


    public long countListInfo() {
        return groceryListInfoRepository.count();
    }

    public void deleteListInfo(GroceryListInfo groceryListInfo) {
        groceryListInfoRepository.delete(groceryListInfo);
    }

    public void saveListInfo(GroceryListInfo groceryListInfo) {
        if(groceryListInfo == null) {
            System.err.println("Item is null.");
            return;
        }
        groceryListInfoRepository.save(groceryListInfo);
    }


}
