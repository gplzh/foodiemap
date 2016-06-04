package com.btw.foodiemap.controller;

import com.btw.foodiemap.domain.Food;
import com.btw.foodiemap.domain.FoodService;
import com.btw.response.ResponseEntity;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by gplzh on 2016/6/2.
 */

@Controller
@RequestMapping(value = "/geo")
public class GeoController {

    @Autowired
    private FoodService foodService;

    @RequestMapping(value = "/addPosition", method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "测试", response = ResponseEntity.class)
    @ResponseBody
    public ResponseEntity addPosition(@RequestParam double x, @RequestParam double y) {
        Food food = new Food("好吃的", new Point(x, y));
        foodService.save(food);

        return new ResponseEntity();
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "测试", response = SearchResponse.class)
    @ResponseBody
    public SearchResponse search(@RequestParam double x, @RequestParam double y, @RequestParam double distance) {
        SearchResponse response = new SearchResponse();
        response.setResult(foodService.findByLocationNear(new Point(x, y), new Distance(distance, Metrics.MILES)));
        return response;
    }

    class SearchResponse extends ResponseEntity<List<Food>>{}

}
