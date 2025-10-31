package ru.java.bondarmax.temperature.main;

import ru.java.bondarmax.temperature.model.TemperatureModel;
import ru.java.bondarmax.temperature.view.TemperatureView;
import ru.java.bondarmax.temperature.controller.TemperatureController;

public class Main {
    public static void main(String[] args) {
        // Создание и связывание компонентов MVC
        TemperatureModel model = new TemperatureModel();
        TemperatureView view = new TemperatureView();
        new TemperatureController(model, view);

        // Отображение интерфейса
        view.show();
    }
}