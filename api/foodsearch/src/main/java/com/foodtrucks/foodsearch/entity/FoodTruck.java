package com.foodtrucks.foodsearch.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * FoodTruck Entity.
 *
 * @Classname FoodTruck
 * @Description Food truck entity, only keep the fields that's needed
 * @Date 2024/1/26 5:32
 * @Created by jackzhou
 */
@Data
public class FoodTruck implements Serializable {
  private String location;
  private String address;
  private String status;
  private String foodItems;
  private String latitude;
  private String longitude;
  private String latitude_longitude;
}