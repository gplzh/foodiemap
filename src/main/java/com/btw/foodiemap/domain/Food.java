package com.btw.foodiemap.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

/**
 * Created by gplzh on 2016/6/2.
 */

@Entity
@Document(collection = "food")
public class Food {

    @Id
    @JsonIgnore
    private String id;

    private String name;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;

    public Food(String name, GeoJsonPoint location) {
        this.name = name;
        this.location = location;
    }
}
