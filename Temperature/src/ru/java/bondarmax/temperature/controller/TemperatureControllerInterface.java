package ru.java.bondarmax.temperature.controller;

import ru.java.bondarmax.temperature.scales.TemperatureScaleInterface;

import java.util.List;

public interface TemperatureControllerInterface {
    void handleConversionRequest(double value, TemperatureScaleInterface from, TemperatureScaleInterface to);

    List<TemperatureScaleInterface> getAvailableScales();
}