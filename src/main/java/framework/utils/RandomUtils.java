package framework.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static framework.loging.Log.logInfoMessage;

public class RandomUtils {

    public static List<Integer> getRandomNumberList(int listBound) {
        List<Integer> randomList = new ArrayList<>();
        for (int number = 0; number < listBound; number++) {
            randomList.add(number);
        }
        Collections.shuffle(randomList);
        logInfoMessage("List with random numbers created");
        return randomList;
    }

    public static LocalDate getRandomDate(int startYear, int endYear) {
        Random random = new Random();
        int minDay = (int) LocalDate.of(startYear, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(endYear, 1, 1).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    public static int getRandomNumber(int numberMaxValue) {
        return (int) (Math.random() * numberMaxValue);
    }

    public static int getRandomNumberInInterval(int numberMinValue, int numberMaxValue) {
        return (int)((Math.random() * (numberMaxValue - numberMinValue + 1)) + numberMinValue);
    }

    public static <T extends Enum<?>> T getRandomEnumValue(Class<T> enumClass) {
        T[] enumArray = enumClass.getEnumConstants();
        int randomIndex = new Random().nextInt(enumArray.length);
        return enumArray[randomIndex];
    }

    public static double getRandomDoubleMarkInInterval(int min, int max) {
        max -= min;
        return Math.random() * ++max + min;
    }
}
