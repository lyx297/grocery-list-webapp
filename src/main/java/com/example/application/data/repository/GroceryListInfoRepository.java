package com.example.application.data.repository;

import com.example.application.data.entity.GroceryListInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroceryListInfoRepository extends JpaRepository<GroceryListInfo, Long> {

    @Query("select c from GroceryListInfo c " +
            "where lower(c.groceryListName) like lower(concat('%', :searchTerm, '%')) ")
    List<GroceryListInfo> search(@Param("searchTerm") String searchTerm);
}
