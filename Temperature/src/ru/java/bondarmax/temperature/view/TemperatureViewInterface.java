package ru.java.bondarmax.temperature.view;

import ru.java.bondarmax.temperature.controller.TemperatureControllerInterface;

public interface TemperatureViewInterface {
    void show();

    void setController(TemperatureControllerInterface controller);

    void displayResult(double result);

    void displayError(String error);

    void clearResult();
}