package com.cd.ntiteam_test.repo;

import com.cd.ntiteam_test.entity.Lord;
import com.cd.ntiteam_test.entity.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LordRepo extends JpaRepository<Lord, Long> {

    @Query(
            value = "SELECT * FROM lord WHERE id=:id",
            nativeQuery = true)
    Lord findByIdCustom(@Param("id") Long id);

    @Query(
            value = "SELECT id,age,name FROM lord LEFT JOIN lord_planet ON lord.id = lord_planet.lord_id WHERE lord_planet.* IS null",
            nativeQuery = true)
    List<Lord> findAllWherePlanetsNull();

    @Query(
            value = "SELECT * FROM lord ORDER BY age limit 10",
            nativeQuery = true)
    List<Lord> topYoungLord();

    @Query(
            value = "SELECT * FROM lord WHERE name=:name AND age=:age limit 1",
            nativeQuery = true)
    Lord isExists(@Param("name") String name, @Param("age") Integer age);

    @Query(
            value = "SELECT * FROM lord_planet WHERE lord_id=:id ",
            nativeQuery = true)
    List<Planet> getPlanets(@Param("id") Long id);
}

