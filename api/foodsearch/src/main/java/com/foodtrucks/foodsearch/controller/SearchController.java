package com.foodtrucks.foodsearch.controller;

import au.com.bytecode.opencsv.CSVReader;
import com.foodtrucks.foodsearch.entity.FoodTruck;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname SearchController
 * @Description restful api controller
 * @Date 2024/1/26 6:02
 * @Created by jackzhou
 */
@RestController
@CrossOrigin(origins = "*")
public class SearchController {
    private List<FoodTruck> FoodTruckList = ReadCsv();

    @Value("classpath:data/Mobile_Food_Facility_Permit.csv:d:\\Mobile_Food_Facility_Permit.csv")
    private String csvFile;

    /**
     * @Description search api to accept "keyword" query string from client
     * @param keyword search keyword from client
     * @return search result with keyword
     */
    @GetMapping("/search")
    public List<FoodTruck> Search(@RequestParam(value = "keyword", defaultValue = "") String keyword) {
        System.out.println(String.format("searching keyword is: %s", keyword));
        var result = new ArrayList<FoodTruck>();
        for(var item : FoodTruckList){
            if(item.getFoodItems().toLowerCase().contains(keyword.toLowerCase())){
                result.add(item);
            }
        }
        System.out.println(String.format("result item count is: %d", result.size()));
        return result;
    }

    /**
     * @Description method to read csv file to get full data of food truck info
     * @return the food truck list from csv file
     */
    private List<FoodTruck> ReadCsv() {
        List<List<String>> records = new ArrayList<List<String>>();
        List<FoodTruck> foodList = new ArrayList<FoodTruck>();
        try (CSVReader reader = new CSVReader(new FileReader("/root/foodsearch/Mobile_Food_Facility_Permit.csv"))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                FoodTruck food = new FoodTruck();
                food.setLocation(nextLine[4]);
                food.setAddress(nextLine[5]);
                food.setStatus(nextLine[10]);
                food.setFoodItems(nextLine[11]);
                food.setLatitude(nextLine[14]);
                food.setLongitude(nextLine[15]);
                food.setLatitude_longitude(nextLine[23]);
                foodList.add(food);
            }
            if (foodList.size() > 1) {
                foodList.remove(0); // Remove the first row that's the header of csv
            }
            System.out.println(foodList.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return foodList;
    }
}
