package com.btw.foodiemap.domain;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

/**
 * Created by gplzh on 2016/6/2.
 */

@Entity
@Document(collection = "food")
public class Food {

    @Id
    private String id;

    private String name;

    private Point location;

    public Food(String name, Point location) {
        this.name = name;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}
