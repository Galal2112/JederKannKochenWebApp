package com.example.application.data.repository;

import com.example.application.data.entity.Rezept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface RezeptRepository extends JpaRepository<Rezept, Integer> {
    //get from offset(10), limit 20, it will get back from 10 to 30
    @Query(value = "select * from Rezept offset :offset rows fetch next :limit ROWS ONLY", nativeQuery = true)
    List<Rezept> getRezeptePage(int offset, int limit);
    //return list from rezept for creatorId
    @Query("select r from Rezept r where r.creator.id=:creatorId")
    List<Rezept> getUserRezepte(Integer creatorId);
    // return countOfRezepte for creatorId
    @Query("select count(r) from Rezept r where r.creator.id=:creatorId")
    Integer countOfRezepte(Integer creatorId);


    // to update the rezept information
    @Transactional
    @Modifying
    @Query("update Rezept r set r.rezeptName = :name, r.beschreibung = :inhalt where r.id = :rezeptId")
    void setRezeptInfoById(Integer rezeptId, String name, String inhalt);

    @Query("select c from Rezept c " +
            "where lower(c.rezeptName) like lower(concat('%', :searchTerm, '%')) ")
    List<Rezept> search(@Param("searchTerm") String searchTerm);
}
