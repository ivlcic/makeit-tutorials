package com.redhat.developers;


public class FruityVice {

  private String name;

  private Nutritions nutritions;

  public FruityVice(String name, Nutritions nutritions) {
    this.name = name;
    this.nutritions = nutritions;
  }

  public String getName() {
    return name;
  }

  public Nutritions getNutritions() {
    return nutritions;
  }

  public static class Nutritions {

    private double carbohydrates;

    private double calories;

    public Nutritions(double carbohydrates, double calories) {
      this.carbohydrates = carbohydrates;
      this.calories = calories;
    }

    public double getCarbohydrates() {
      return carbohydrates;
    }

    public double getCalories() {
      return calories;
    }

  }
}
