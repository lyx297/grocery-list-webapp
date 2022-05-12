package com.example.application.data.repository;

import com.example.application.data.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select c from Item c " +
            "where lower(c.itemName) like lower(concat('%', :searchTerm, '%')) ")
    List<Item> searchBasedOnItemName(@Param("searchTerm") String searchTerm);


    @Query("select c from Item c " +
            "where lower(c.itemName) like lower(concat('%', :searchTerm, '%')) " +
            "and lower(c.groceryListName) like lower(concat('%', :selectedGroceryListInfo, '%')) ")
    List<Item> searchBothFilters(@Param("searchTerm") String searchTerm,
                                @Param("selectedGroceryListInfo") String selectedGroceryListInfo);

    @Query("select c from Item c " +
            "where lower(c.groceryListName) like lower(concat('%', :selectedGroceryListInfo, '%')) ")
    List<Item> searchBasedOnListInfo(@Param("selectedGroceryListInfo") String selectedGroceryListInfo);
}
