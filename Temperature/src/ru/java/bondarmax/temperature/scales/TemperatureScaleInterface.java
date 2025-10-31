package ru.java.bondarmax.temperature.scales;

public interface TemperatureScaleInterface {
    double convertToCelsius(double value);

    double convertFromCelsius(double celsius);
}
