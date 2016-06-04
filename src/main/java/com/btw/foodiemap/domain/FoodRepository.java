package com.btw.foodiemap.domain;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by gplzh on 2016/6/2.
 */

public interface FoodRepository extends MongoRepository<Food, String> {

    GeoResults<Food> findByLocationNear(Point point, Distance maxDistance);
}