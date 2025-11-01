package ru.java.bondarmax.temperature.controller;

import ru.java.bondarmax.temperature.model.TemperatureModelInterface;
import ru.java.bondarmax.temperature.scales.TemperatureScaleInterface;
import ru.java.bondarmax.temperature.view.TemperatureViewInterface;

import java.util.List;

// Контроллер
public class TemperatureController implements TemperatureControllerInterface {
    private final TemperatureModelInterface model;
    private final TemperatureViewInterface view;

    public TemperatureController(TemperatureModelInterface model, TemperatureViewInterface view) {
        this.model = model;
        this.view = view;

        view.setController(this); // связываем
    }
    
    public void handleConversionRequest(double value, TemperatureScaleInterface from, TemperatureScaleInterface to) {
        try {
            double result = model.convertTemperature(value, from, to);

            view.displayResult(result);
            view.displayError("");
        } catch (Exception e) {
            view.displayError("Ошибка конвертации");
            view.clearResult();
        }
    }

    public List<TemperatureScaleInterface> getAvailableScales() {
        return model.getAvailableScales();
    }
}