package com.example.application.data.repository;

import com.example.application.data.entity.Rezept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface RezeptRepository extends JpaRepository<Rezept, Integer> {

    @Query(value = "select * from Rezept offset :offset rows fetch next :limit ROWS ONLY", nativeQuery = true)
    List<Rezept> getRezeptePage(int offset, int limit);

    @Query("select r from Rezept r where r.creator.id=:creatorId")
    List<Rezept> getUserRezepte(Integer creatorId);

    @Query("select count(r) from Rezept r where r.creator.id=:creatorId")
    Integer countOfRezepte(Integer creatorId);

    @Transactional
    @Modifying
    @Query("update Rezept r set r.rezeptName = :name, r.beschreibung = :inhalt where r.id = :rezeptId")
    void setRezeptInfoById(Integer rezeptId, String name, String inhalt);
}
