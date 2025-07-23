package ru.java.bondarmax.range;

public class Range {
    private double from;
    private double to;

    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public double getLength() {
        return to - from;
    }

    public boolean isInside(double number) {
        return from <= number && number <= to;
    }

    public Range getIntersection(Range range) {
        // Вычисляем границы пересечения
        double newFrom = Math.max(from, range.from);
        double newTo = Math.min(to, range.to);

        // Проверяем отсутствие пересечения или пересечение только по границе
        if (newFrom >= newTo) {
            return null;
        }

        return new Range(newFrom, newTo);
    }

    public Range[] getUnion(Range range) {
        // Если интервалы не пересекаются и не соприкасаются
        if (to < range.from || from > range.to) {
            return new Range[]{new Range(from, to), new Range(range.from, range.to)};
        }

        // Если интервалы пересекаются или один содержит другой
        double newFrom = Math.min(from, range.from);
        double newTo = Math.max(to, range.to);

        return new Range[]{new Range(newFrom, newTo)};
    }

    public Range[] getDifference(Range range) {
        // Если интервалы не пересекаются или пересекаются только по краю
        if (to <= range.from || from >= range.to) {
            return new Range[]{new Range(from, to)};
        }

        // Проверяем полное включение
        if (from >= range.from && to <= range.to) {
            return new Range[0];
        }

        // Если один интервал содержит другой
        if (from < range.from && to > range.to) {
            return new Range[]{new Range(from, range.from), new Range(range.to, to)};
        }

        // Если интервалы пересекаются частично
        if (from < range.from) {
            return new Range[]{new Range(from, range.from)};
        }

        return new Range[]{new Range(range.to, to)}; // Пустой результат
    }

    public void print() {
        System.out.printf("%nНачало диапазона: %.2f%n", from);
        System.out.printf("Конец диапазона: %.2f%n", to);
        System.out.printf("Длина диапазона: %.2f%n%n", getLength());
    }
}