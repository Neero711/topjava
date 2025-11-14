package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;

public class MealsUtil {

    public static final int CALORIES_PER_DAY = 2000;
    private static final MealRepository repository = new InMemoryMealRepository();

    public static MealRepository getRepository() {
        return repository;
    }
    private final AtomicInteger counter = new AtomicInteger(0);


     static  {
        Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение",
                100),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        ).forEach(repository::mealSave);
    }

    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime,
        LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
            .collect(
                Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
            );

        return meals.stream()
            .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
            .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
            .collect(Collectors.toList());
    }

    public static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

//    private static final List<Meal> MEALS = Arrays.asList(
//        new Meal(LocalDateTime.of(2024, 1, 15, 8, 0), "Овсянка с фруктами", 400),
//        new Meal(LocalDateTime.of(2024, 1, 15, 13, 0), "Суп и салат", 600),
//        new Meal(LocalDateTime.of(2024, 1, 15, 18, 30), "Курица с гречкой", 700),
//        new Meal(LocalDateTime.of(2024, 1, 15, 21, 0), "Йогурт", 400),
//
//        new Meal(LocalDateTime.of(2024, 1, 16, 7, 30), "Омлет", 350),
//        new Meal(LocalDateTime.of(2024, 1, 16, 12, 45), "Рыба с овощами", 500),
//        new Meal(LocalDateTime.of(2024, 1, 16, 19, 0), "Творог", 300),
//        new Meal(LocalDateTime.of(2024, 1, 16, 22, 0), "Фру   кты", 650),
//
//        new Meal(LocalDateTime.of(2024, 1, 17, 9, 0), "Блины", 800),
//        new Meal(LocalDateTime.of(2024, 1, 17, 14, 0), "Пицца", 1200),
//        new Meal(LocalDateTime.of(2024, 1, 17, 20, 0), "Салат Цезарь", 500)
//    );

    public static List<MealTo> getAllWithExcess() {
        List<Meal> meals = repository.getAllMeals();
        return filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
    }


}
