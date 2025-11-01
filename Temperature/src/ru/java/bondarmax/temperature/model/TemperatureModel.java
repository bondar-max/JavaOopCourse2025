package ru.java.bondarmax.temperature.model;

import ru.java.bondarmax.temperature.scales.TemperatureScaleInterface;
import ru.java.bondarmax.temperature.scales.TemperatureScaleFactory;

import java.util.List;

// Модель
public class TemperatureModel implements TemperatureModelInterface {
    private final List<TemperatureScaleInterface> availableScales;

    public TemperatureModel() {
        availableScales = TemperatureScaleFactory.getAllScales();
    }

    public double convertTemperature(double value, TemperatureScaleInterface from, TemperatureScaleInterface to) {
        double celsius = from.convertToCelsius(value);
        return to.convertFromCelsius(celsius);
    }

    public List<TemperatureScaleInterface> getAvailableScales() {
        return availableScales;
    }
}