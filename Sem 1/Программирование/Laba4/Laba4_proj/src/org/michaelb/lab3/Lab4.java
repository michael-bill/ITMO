package org.michaelb.lab3;

import org.michaelb.lab3.story.exceptions.*;
import org.michaelb.lab3.story.interfaces.Laughable;
import org.michaelb.lab3.story.objects.*;

public class Lab4 {
    public static void main(String[] args) {
        // Создание нужных классов
        They they = new They(10);
        ConfusionGuy confusionGuy = new ConfusionGuy();
        Grumpy grumpy = new Grumpy();
        AirBalloon airBalloon = new AirBalloon();
        Homes homes = new Homes();
        Residents residents = new Residents();
        Wind wind = new Wind();

        // Они машут шляпами
        try {
            they.wavingHats();
        } catch (NoHatOnException e) {
            System.out.println(e.getMessage());
        }
        System.out.print(". ");
        // Растеряйка снимает шапку, которой нет
        try {
            confusionGuy.HAND.holdOutToTakeOffHat();
        } catch (NoHatOnException e) {
            System.out.print(", и " + e.getMessage());
        }
        System.out.print(" ");
        // Все засмеялись
        // Старый вариант: Every every = new Every(); every.laugh();
        // Вариант с анонимным классом:
        Laughable every = new Laughable() {
            @Override
            public void laugh() {
                System.out.print("Все засмеялись");
            }
        };
        every.laugh();
        System.out.print(", a ");
        // Ворчун сказал
        grumpy.goTell();
        System.out.print(": ");
        // Шар взлетел
        try {
            airBalloon.fly();
        } catch (NoMoreFuelException e) {
            System.out.print("Шар не полетит сегодня.");
            e.printStackTrace();
        }
        System.out.print(", и ");
        // Создаем дома для Цветочного города
        for (int i = 0; i < 10; i++) {
            Home home = new Home(i + 1, "Цветочный город, Улица Пушкина, " + (i + 1));
            homes.add(home);
        }
        // Создаем жителей для цветочного города
        for (int i = 0; i < 20; i++) {
            String sex = getRandom(0, 2) == 1 ? "Муж." : "Жен.";
            Shorty shorty = new Shorty("Карлик " + (i + 1), getRandom(100, 151), sex);
            residents.add(shorty);
        }
        // Создаем цветочный город
        FlowerCity flowerCity = new FlowerCity("Цветочный город", homes, residents);
        // Смотрим на город с воздушного шара
        flowerCity.seeing(airBalloon);
        System.out.print(". ");
        // Смотрим на дома
        homes.seeing(airBalloon);
        System.out.print(", a ");
        // Смотрим на жителей (коротышек)
        residents.seeing(airBalloon);
        System.out.print(". ");
        // Дуем ветром на шар
        wind.blownAway(airBalloon);
        System.out.print(", и ");
        // Опять смотрим на город
        flowerCity.seeing(airBalloon);
        System.out.println(".");
    }

    private static int getRandom(int start, int end) {
        return (int) (start + Math.random() * (end - start));
    }
}
