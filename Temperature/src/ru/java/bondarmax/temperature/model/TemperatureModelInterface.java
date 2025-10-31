package ru.java.bondarmax.temperature.model;

import ru.java.bondarmax.temperature.scales.TemperatureScaleInterface;

import java.util.List;

public interface TemperatureModelInterface {
    double convertTemperature(double value, TemperatureScaleInterface from, TemperatureScaleInterface to);

    List<TemperatureScaleInterface> getAvailableScales();
}
