package com.btw.foodiemap.controller;

import com.btw.foodiemap.domain.Food;
import com.btw.foodiemap.domain.FoodService;
import com.btw.response.ResponseEntity;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by gplzh on 2016/6/2.
 */

@Controller
@RequestMapping(value = "/geo")
public class GeoController {

    @Autowired
    private FoodService foodServiceImpl;

    @RequestMapping(value = "/addPosition", method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "测试", response = ResponseEntity.class)
    @ResponseBody
    public ResponseEntity addPosition(@RequestParam double x, @RequestParam double y) {
        Food food = new Food("好吃的", new GeoJsonPoint(x, y));
        foodServiceImpl.save(food);

        return new ResponseEntity();
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "测试", response = ResponseEntity.class)
    @ResponseBody
    public ResponseEntity search(@RequestParam double x, @RequestParam double y, @RequestParam double distance) {
        ResponseEntity response = new ResponseEntity();
        response.setResult(foodServiceImpl.findByLocationNear(new Point(x, y), new Distance(distance)));
        return response;
    }

}
