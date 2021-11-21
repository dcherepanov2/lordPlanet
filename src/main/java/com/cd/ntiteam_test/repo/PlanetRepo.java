package com.cd.ntiteam_test.repo;

import com.cd.ntiteam_test.entity.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PlanetRepo extends JpaRepository<Planet,Long> {

    @Query(
            value = "SELECT * FROM planet WHERE id=:id",
            nativeQuery = true)
    Planet findByIdCustom(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(
            value = "DELETE FROM planet WHERE id = :id",
            nativeQuery = true)
    void deletePlanet(@Param("id")Long id);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE planet SET lord_id = null WHERE id =:id ",
            nativeQuery = true)
    void setLordNull(@Param("id")Long id);

    @Transactional
    @Modifying
    @Query(
            value = "DELETE FROM lord_planet WHERE planet_id =:id ",
            nativeQuery = true)
    void setLordNullFromLordPlanet(@Param("id")Long id);

    @Query(
            value = "SELECT * FROM planet WHERE name=:name limit 1",
            nativeQuery = true)
    Planet findByName(@Param("name")String name);
}
