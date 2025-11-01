package ru.java.bondarmax.temperature.view;

import ru.java.bondarmax.temperature.controller.TemperatureControllerInterface;
import ru.java.bondarmax.temperature.scales.TemperatureScaleInterface;

import javax.swing.*;
import java.awt.*;
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

    /**
     * Внутренний метод, не должен использоваться контроллером
     * Получение введенной температуры как числа
     */
    public Double getInputTemperatureValue() {
        String text = temperatureField.getText().trim();

        if (text.isEmpty()) {
            return null;
        }

        text = text.replace(',', '.');
        return Double.parseDouble(text);
    }

    /**
     * Внутренний метод, не должен использоваться контроллером
     * Получение выбранной исходной шкалы
     */
    public TemperatureScaleInterface getSourceScale() {
        return (TemperatureScaleInterface) sourceScaleCombo.getSelectedItem();
    }

    /**
     * Внутренний метод, не должен использоваться контроллером
     * Получение выбранной целевой шкалы
     */
    public TemperatureScaleInterface getTargetScale() {
        return (TemperatureScaleInterface) targetScaleCombo.getSelectedItem();
    }

    /**
     * Установка доступных шкал температуры в комбо-боксы
     */
    public void setTemperatureScales(List<TemperatureScaleInterface> scales) {
        TemperatureScaleInterface[] scalesArray = scales.toArray(new TemperatureScaleInterface[0]);

        sourceScaleCombo.setModel(new DefaultComboBoxModel<>(scalesArray));
        targetScaleCombo.setModel(new DefaultComboBoxModel<>(scalesArray));

        sourceScaleCombo.setSelectedIndex(0);
        targetScaleCombo.setSelectedIndex(1);
    }

    /**
     * Устанавливает контроллер для обработки событий.
     */
    @Override
    public void setController(TemperatureControllerInterface controller) {
        // Обработчик для кнопки конвертации
        convertButton.addActionListener(e -> handleConversionRequest(controller));

        // Обработчик для поля ввода (нажатие Enter)
        temperatureField.addActionListener(e -> handleConversionRequest(controller));

        // Загружаем шкалы температур
        setTemperatureScales(controller.getAvailableScales());
    }

    /**
     * Отображает результат конвертации.
     * Форматирование числа.
     */
    @Override
    public void displayResult(double result) {
        resultField.setText(String.format("%.2f", result));
    }

    /**
     * Отображает сообщение об ошибке
     */
    @Override
    public void displayError(String error) {
        errorLabel.setText(error);
    }

    /**
     * Очищает поле результата
     */
    @Override
    public void clearResult() {
        resultField.setText("");
    }

    /**
     * Показывает интерфейс пользователя.
     * Запускается в потоке диспетчера событий Swing
     */
    @Override
    public void show() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    /**
     * Обрабатывает запрос на конвертацию и делегирует контроллеру
     */
    private void handleConversionRequest(TemperatureControllerInterface controller) {
        try {
            Double inputValue = getInputTemperatureValue();

            if (inputValue != null) {
                TemperatureScaleInterface fromScale = getSourceScale();
                TemperatureScaleInterface toScale = getTargetScale();
                controller.handleConversionRequest(inputValue, fromScale, toScale);
            } else {
                displayError("Введите значение температуры");
                clearResult();
            }
        } catch (NumberFormatException e) {
            displayError("Ошибка: введите корректное число");
            clearResult();
        }
    }

    private void initializeGui() {
        // 1. Создание основного окна
        frame = new JFrame("Конвертер температуры");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(310, 240);
        frame.setLocationRelativeTo(null); // Центрирование окна
        frame.setResizable(false); // Запрет изменения размера

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

        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        errorLabel.setPreferredSize(new Dimension(0, 20)); // Фиксированная высота

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
}