package ru.java.bondarmax.temperature.view;

import ru.java.bondarmax.temperature.scales.TemperatureScaleInterface;

import java.awt.event.ActionListener;
import java.util.List;

public interface TemperatureViewInterface {
    void setTemperatureScales(List<TemperatureScaleInterface> scales);

    void setConvertButtonListener(ActionListener listener);

    void setTemperatureFieldListener(ActionListener listener);

    Double getInputTemperatureValue();

    void setError(String error);

    TemperatureScaleInterface getSourceScale();

    TemperatureScaleInterface getTargetScale();

    void show();

    void setResult(double result);

    void clearResult();
}