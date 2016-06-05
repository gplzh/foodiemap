package com.btw.foodiemap.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by gplzh on 2016/6/2.
 */

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    public Food save(Food food) {
        return foodRepository.save(food);
    }

    public List<Food> findByLocationNear(Point point, Distance maxDistance){
        return foodRepository.findByLocationNear(point, maxDistance);
    }

    public List<Food> findByLocationWithin(Polygon polygon){
        return foodRepository.findByLocationWithin(polygon);
    }

}
