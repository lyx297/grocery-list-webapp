package com.example.application.data.repository;

import com.example.application.data.entity.ListInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ListInfoRepository extends JpaRepository<ListInfo, Long> {

    @Query("select c from ListInfo c " +
            "where lower(c.listName) like lower(concat('%', :searchTerm, '%')) ")
    List<ListInfo> search(@Param("searchTerm") String searchTerm);
}
