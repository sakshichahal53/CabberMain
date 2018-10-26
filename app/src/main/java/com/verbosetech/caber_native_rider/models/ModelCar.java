package com.verbosetech.caber_native_rider.models;

/**
 * Created by sakshi on 3/6/18.
 */

public class ModelCar {

    private String name, lower_value, uppervalue;
    private int image;

    public ModelCar(String name, String lower_value, String uppervalue, int image) {
        this.name = name;
        this.lower_value = lower_value;
        this.uppervalue = uppervalue;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLower_value() {
        return lower_value;
    }

    public void setLower_value(String lower_value) {
        this.lower_value = lower_value;
    }

    public String getUppervalue() {
        return uppervalue;
    }

    public void setUppervalue(String uppervalue) {
        this.uppervalue = uppervalue;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
