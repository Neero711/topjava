package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    Meal mealSave(Meal meal);
    boolean mealDelete(int id);
    Meal getMealById(int id);
    List<Meal> getAllMeals();
}