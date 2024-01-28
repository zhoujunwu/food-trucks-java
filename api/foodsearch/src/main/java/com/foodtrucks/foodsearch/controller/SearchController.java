package com.foodtrucks.foodsearch.controller;

import au.com.bytecode.opencsv.CSVReader;
import com.foodtrucks.foodsearch.entity.FoodTruck;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Search food controller.
 *
 * @Classname SearchController
 * @Description restful api controller
 * @Date 2024/1/26 6:02
 * @Created by jackzhou
 */
@RestController
@CrossOrigin(origins = "*")
public class SearchController {
  private List<FoodTruck> foodTruckList = null;

  @Value("${csv.input}")
  private String csvFile;

  @Value("${classpath:data/Mobile_Food_Facility_Permit.csv}")
  private String csvDefault;

  /**
   * Search api.
   *
   * @param keyword search keyword from client
   * @return search result with keyword
   * @Description search api to accept "keyword" query string from client
   */
  @GetMapping("/search")
  public List<FoodTruck> search(@RequestParam(value = "keyword", defaultValue = "") String keyword) throws IOException {
    System.out.printf("searching keyword is: %s%n", keyword);
    iniData();
    var result = new ArrayList<FoodTruck>();
    for (var item : foodTruckList) {
      if (item.getFoodItems().toLowerCase().contains(keyword.toLowerCase())) {
        result.add(item);
      }
    }
    System.out.printf("result item count is: %d%n", result.size());
    return result;
  }

  /**
   * To initiate data according to file path
   */
  private void iniData() throws IOException {
    if (foodTruckList == null) {
      System.out.println("initiate csv data..");
      System.out.println("csvfile from input is: " + csvFile);
      System.out.println("csv default file is: " + csvDefault);
      InputStreamReader reader = null;
      if (csvFile == null || csvFile.isEmpty()) {
        ClassPathResource resource = new ClassPathResource(csvDefault);
        reader = new InputStreamReader(resource.getInputStream());
      } else {
        reader = new FileReader(csvFile);
      }
      foodTruckList = readCsv(reader);
    }
  }

  /**
   * Read CSV file content.
   *
   * @param inputReader the input stream of csv file
   * @return the food truck list from csv file
   * @Description method to read csv file to get full data of food truck info
   */
  private List<FoodTruck> readCsv(InputStreamReader inputReader) {
    List<FoodTruck> foodList = new ArrayList<FoodTruck>();
    try (CSVReader reader = new CSVReader(inputReader)) {
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
        // Remove the first row that's the header of csv
        foodList.remove(0);
      }
      System.out.printf("total csv item count is: %d%n", foodList.size());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return foodList;
  }
}
