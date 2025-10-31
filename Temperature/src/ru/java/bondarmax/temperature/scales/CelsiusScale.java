package ru.java.bondarmax.temperature.scales;

public class CelsiusScale implements TemperatureScaleInterface {
    public double convertToCelsius(double value) {
        return value;
    }

    public double convertFromCelsius(double celsius) {
        return celsius;
    }

    @Override
    public String toString() {
        return "Цельсий";
    }
}
