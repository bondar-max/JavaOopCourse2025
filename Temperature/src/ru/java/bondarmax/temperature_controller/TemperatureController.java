package ru.java.bondarmax.temperature_controller;

import ru.java.bondarmax.temperature_model.TemperatureModel;
import ru.java.bondarmax.temperature_view.TemperatureView;

// Контроллер
public class TemperatureController {
    private final TemperatureModel model;
    private final TemperatureView view;

    public TemperatureController(TemperatureModel model, TemperatureView view) {
        this.model = model;
        this.view = view;

        // Регистрация обработчиков событий
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        // Обработчик для кнопки конвертации
        view.getConvertButton().addActionListener(_ -> convertTemperature());

        // Обработка нажатия Enter в поле ввода
        view.getTemperatureField().addActionListener(_ -> convertTemperature());
    }

    private void convertTemperature() {
        try {
            // Получение введенных данных
            String inputText = view.getTemperatureField().getText().trim();

            if (inputText.isEmpty()) {
                view.getErrorLabel().setText("Введите значение температуры");
                return;
            }

            double inputValue = Double.parseDouble(inputText);
            String fromScale = (String) view.getSourceScaleCombo().getSelectedItem();
            String toScale = (String) view.getTargetScaleCombo().getSelectedItem();

            // Выполнение конвертации
            @SuppressWarnings("DataFlowIssue")
            double result = model.convertTemperature(inputValue, fromScale, toScale);

            // Отображение результата
            view.getResultField().setText(String.format("%.2f", result));
            view.getErrorLabel().setText(""); // Очистка сообщения об ошибки

        } catch (NumberFormatException ex) {
            view.getErrorLabel().setText("Ошибка: введите корректное число");
            view.getResultField().setText("");
        }
    }
}
