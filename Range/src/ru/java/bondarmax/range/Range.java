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

    public Range getIntersection(Range otherRange) {
        // Проверяем отсутствие пересечения или пересечение по краю
        if (to <= otherRange.from || from >= otherRange.to) {
            return null;
        }

        // Вычисляем границы пересечения
        double newFrom = Math.max(from, otherRange.from);
        double newTo = Math.min(to, otherRange.to);

        return new Range(newFrom, newTo);
    }

    public Range[] getUnion(Range otherRange) {
        // Если интервалы не пересекаются и не соприкасаются
        if (to < otherRange.from || from > otherRange.to)
            return new Range[]{new Range(from, to), new Range(otherRange.from, otherRange.to)};

        // Если интервалы пересекаются или один содержит другой
        double newFrom = Math.min(from, otherRange.from);
        double newTo = Math.max(to, otherRange.to);

        return new Range[]{new Range(newFrom, newTo)};
    }

    public Range[] getDifference(Range otherRange) {
        // Если интервалы не пересекаются или пересекаются только по краю
        if (to <= otherRange.from || from >= otherRange.to) {
            return new Range[]{new Range(from, to)};
        }

        // Если один интервал полностью содержит другой
        if (from <= otherRange.from && to >= otherRange.to)
            return new Range[]{new Range(from, otherRange.from), new Range(otherRange.to, to)};

        // Если интервалы пересекаются частично
        if (from < otherRange.from && to > otherRange.from)
            return new Range[]{new Range(from, otherRange.from)};

        if (from >= otherRange.from && from < otherRange.to && to > otherRange.to)
            return new Range[]{new Range(otherRange.to, to)};

        return new Range[0]; // Пустой результат
    }

    public void printInformation() {
        System.out.printf("%nНачало диапазона: %.2f%n", this.getFrom());
        System.out.printf("Конец диапазона: %.2f%n", this.getTo());
        System.out.printf("Длина диапазона: %.2f%n%n", this.getLength());
    }
}