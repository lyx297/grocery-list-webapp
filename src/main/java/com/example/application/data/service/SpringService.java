package com.example.application.data.service;

import com.example.application.data.entity.Item;
import com.example.application.data.entity.ListInfo;
import com.example.application.data.repository.ItemRepository;
import com.example.application.data.repository.ListInfoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpringService {

    private final ItemRepository itemRepository;
    private final ListInfoRepository listInfoRepository;

    public SpringService(ItemRepository itemRepository,
                        ListInfoRepository listInfoRepository
    ) {
        this.itemRepository = itemRepository;
        this.listInfoRepository = listInfoRepository;
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

    public List<ListInfo> findAllListInfo() {
        return listInfoRepository.findAll();
    }

    public long countListInfo() {
        return listInfoRepository.count();
    }

    public void deleteListInfo(ListInfo listInfo) {
        listInfoRepository.delete(listInfo);
    }

    public void saveListInfo(ListInfo listInfo) {
        if(listInfo == null) {
            System.err.println("Item is null.");
            return;
        }
        listInfoRepository.save(listInfo);
    }


}
