package ru.java.bondarmax.temperature.scales;

public class FahrenheitScale implements TemperatureScaleInterface {
    public double convertToCelsius(double value) {
        return (value - 32) * 5 / 9;
    }

    public double convertFromCelsius(double celsius) {
        return celsius * 9 / 5 + 32;
    }

    @Override
    public String toString() {
        return "Фаренгейт";
    }
}
