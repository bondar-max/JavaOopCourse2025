package ru.java.bondarmax.temperature.scales;

public class KelvinScale implements TemperatureScaleInterface {
    public double convertToCelsius(double value) {
        return value - 273.15;
    }

    public double convertFromCelsius(double celsius) {
        return celsius + 273.15;
    }

    @Override
    public String toString() {
        return "Кельвин";
    }
}
