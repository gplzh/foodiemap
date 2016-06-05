package com.btw.foodiemap.domain;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by gplzh on 2016/6/2.
 */

public interface FoodRepository extends MongoRepository<Food, String> {

    List<Food> findByLocationNear(Point point, Distance maxDistance);

    List<Food> findByLocationWithin(Polygon polygon);
}