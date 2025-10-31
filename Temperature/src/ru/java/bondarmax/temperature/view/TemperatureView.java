package ru.java.bondarmax.temperature.view;

import ru.java.bondarmax.temperature.scales.TemperatureScaleInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

// Представление
public class TemperatureView implements TemperatureViewInterface {
    private JFrame frame;
    private JTextField temperatureField;
    private JTextField resultField;
    private JComboBox<TemperatureScaleInterface> sourceScaleCombo;
    private JComboBox<TemperatureScaleInterface> targetScaleCombo;
    private JButton convertButton;
    private JLabel errorLabel;

    public TemperatureView() {
        initializeGui();
    }

    private void initializeGui() {
        // 1. Создание основного окна
        frame = new JFrame("Конвертер температуры");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(310, 240);
        frame.setLocationRelativeTo(null); // Центрирование окна

        // 2. Установка системного look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Не удалось установить системный look and feel: " + e.getMessage());
        }

        // 3. Создание главной панели
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 4. Создание внутренних панелей
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        JPanel buttonPanel = new JPanel(new FlowLayout());

        // 5. Создание компонентов
        JLabel temperatureLabel = new JLabel("Введите температуру:");
        temperatureField = new JTextField();

        JLabel sourceScaleLabel = new JLabel("Из шкалы:");
        sourceScaleCombo = new JComboBox<>();

        JLabel targetScaleLabel = new JLabel("В шкалу:");
        targetScaleCombo = new JComboBox<>();

        JLabel resultLabel = new JLabel("Результат:");
        resultField = new JTextField();
        resultField.setEditable(false); // Поле только для чтения

        convertButton = new JButton("Конвертировать");

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setPreferredSize(new Dimension(0, 20));

        // 6. Сборка inputPanel (компоненты → панель)
        inputPanel.add(temperatureLabel);
        inputPanel.add(temperatureField);
        inputPanel.add(sourceScaleLabel);
        inputPanel.add(sourceScaleCombo);
        inputPanel.add(targetScaleLabel);
        inputPanel.add(targetScaleCombo);
        inputPanel.add(resultLabel);
        inputPanel.add(resultField);

        // 7. Сборка buttonPanel (компоненты → панель)
        buttonPanel.add(convertButton);

        // 8. Сборка mainPanel (панели → главная панель)
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(errorLabel, BorderLayout.NORTH);

        // 9. Финальная сборка (главная панель → окно)
        frame.add(mainPanel);
    }

    public void show() {
        // Запуск в потоке диспетчера событий
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    public void setTemperatureScales(List<TemperatureScaleInterface> scales) {
        // Создаем разные модели для каждого комбо-бокса
        DefaultComboBoxModel<TemperatureScaleInterface> sourceModel = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<TemperatureScaleInterface> targetModel = new DefaultComboBoxModel<>();

        for (TemperatureScaleInterface scale : scales) {
            sourceModel.addElement(scale);
            targetModel.addElement(scale);
        }

        sourceScaleCombo.setModel(sourceModel);
        sourceScaleCombo.setSelectedIndex(0); // Цельсий

        targetScaleCombo.setModel(targetModel);
        targetScaleCombo.setSelectedIndex(1); // Фаренгейт
    }

    public void setConvertButtonListener(ActionListener listener) {
        convertButton.addActionListener(listener);
    }

    public void setTemperatureFieldListener(ActionListener listener) {
        temperatureField.addActionListener(listener);
    }

    public void clearResult() {
        resultField.setText(""); // очищаем поле
    }

    public void setError(String error) {
        errorLabel.setText(error);
    }

    public TemperatureScaleInterface getSourceScale() {
        return (TemperatureScaleInterface) sourceScaleCombo.getSelectedItem();
    }

    public TemperatureScaleInterface getTargetScale() {
        return (TemperatureScaleInterface) targetScaleCombo.getSelectedItem();
    }

    public Double getInputTemperatureValue() {
        String text = temperatureField.getText().trim();

        if (text.isEmpty()) {
            return null;
        }

        return Double.parseDouble(text);
    }

    public void setResult(double result) {
        resultField.setText(String.format("%.2f", result));
    }
}
