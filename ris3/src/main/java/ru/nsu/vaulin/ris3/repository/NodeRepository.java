package ru.nsu.vaulin.ris3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nsu.vaulin.ris3.model.entity.NodeEntity;

import java.util.List;

@Repository
public interface NodeRepository extends JpaRepository<NodeEntity, Long> {
    @Query(value =
            "SELECT * " +
            "FROM NODE N " +
            "WHERE earth_box(ll_to_earth(:LON, :LAT), :RADIUS) @> ll_to_earth(N.LONGITUDE, N.LATITUDE) " +
            "AND earth_distance(ll_to_earth(:LON, :LAT), ll_to_earth(N.LONGITUDE, N.LATITUDE)) < :RADIUS " +
            "ORDER BY earth_distance(ll_to_earth(:LAT, :LON), ll_to_earth(N.LATITUDE, N.LONGITUDE))",
            nativeQuery = true)
    List<NodeEntity> findInArea(@Param("RADIUS") double radius, @Param("LON") double lon, @Param("LAT") double lat);

    /*
    "SELECT * FROM NODE " +
            "WHERE earth_distance(ll_to_earth(:LAT, :LON), ll_to_earth(NODE.LATITUDE, NODE.LONGITUDE)) < :RADIUS " +
            "ORDER BY earth_distance(ll_to_earth(:LAT, :LON), ll_to_earth(NODE.LATITUDE, NODE.LONGITUDE))"
     */
}
