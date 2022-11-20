package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    private int carId;
    private String mark;
    private String model;
    private Integer year;
    private String color;
    private Integer price;

}