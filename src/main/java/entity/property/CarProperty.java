package entity.property;

import entity.Car;
import javafx.beans.property.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarProperty {


    private IntegerProperty carId;
    private StringProperty mark;
    private StringProperty model;
    private IntegerProperty year;
    private StringProperty color;
    private IntegerProperty price;


    public CarProperty(Car car) {
        this.carId = new SimpleIntegerProperty(car.getCarId());
        this.mark = new SimpleStringProperty(car.getMark());
        this.model = new SimpleStringProperty(car.getModel());
        this.year = new SimpleIntegerProperty(car.getYear());
        this.color = new SimpleStringProperty(car.getColor());
        this.price = new SimpleIntegerProperty(car.getPrice());
    }


}