package ru.java.bondarmax.temperature_view;

import javax.swing.*;
import java.awt.*;

// Представление
public class TemperatureView {
    private JFrame frame;
    private JTextField temperatureField;
    private JTextField resultField;
    private JComboBox<String> sourceScaleCombo;
    private JComboBox<String> targetScaleCombo;
    private JButton convertButton;
    private JLabel errorLabel;

    public TemperatureView() {
        initializeGUI();
    }

    // Геттеры для доступа к компонентам
    public JTextField getTemperatureField() {
        return temperatureField;
    }

    public JTextField getResultField() {
        return resultField;
    }

    public JComboBox<String> getSourceScaleCombo() {
        return sourceScaleCombo;
    }

    public JComboBox<String> getTargetScaleCombo() {
        return targetScaleCombo;
    }

    public JButton getConvertButton() {
        return convertButton;
    }

    public JLabel getErrorLabel() {
        return errorLabel;
    }

    private void initializeGUI() {
        // 1. Создание основного окна
        frame = new JFrame("Конвертер температуры");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
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
        String[] temperatureScales = {"Цельсий", "Фаренгейт", "Кельвин"};
        sourceScaleCombo = new JComboBox<>(temperatureScales);

        JLabel targetScaleLabel = new JLabel("В шкалу:");
        targetScaleCombo = new JComboBox<>(temperatureScales);
        targetScaleCombo.setSelectedIndex(1); // По умолчанию Фаренгейт

        JLabel resultLabel = new JLabel("Результат:");
        resultField = new JTextField();
        resultField.setEditable(false); // Поле только для чтения

        convertButton = new JButton("Конвертировать");

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);

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
        frame.setVisible(true);
    }
}
