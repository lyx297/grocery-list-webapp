package com.example.application.data.repository;

import com.example.application.data.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select c from Item c " +
            "where lower(c.itemName) like lower(concat('%', :searchTerm, '%')) ")
    List<Item> search(@Param("searchTerm") String searchTerm);
}
