package ru.java.bondarmax.temperature_main;

import ru.java.bondarmax.temperature_model.TemperatureModel;
import ru.java.bondarmax.temperature_view.TemperatureView;
import ru.java.bondarmax.temperature_controller.TemperatureController;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Запуск в потоке диспетчера событий
        SwingUtilities.invokeLater(() -> {
            // Создание и связывание компонентов MVC
            TemperatureModel model = new TemperatureModel();
            TemperatureView view = new TemperatureView();
            new TemperatureController(model, view);

            // Отображение интерфейса
            view.show();
        });
    }
}