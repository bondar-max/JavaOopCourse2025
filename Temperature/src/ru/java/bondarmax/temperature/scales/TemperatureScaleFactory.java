package ru.java.bondarmax.temperature.scales;

import java.util.Arrays;
import java.util.List;

public class TemperatureScaleFactory {
    public static List<TemperatureScaleInterface> getAllScales() {
        return Arrays.asList(
                new CelsiusScale(),
                new FahrenheitScale(),
                new KelvinScale()
        );
    }
}