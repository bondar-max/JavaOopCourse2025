package ru.bondarmax.java.range;

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

    public Range getSetsIntersection(Range range) {
        double range1Start = this.getFrom();
        double range1End = this.getTo();

        double range2Start = range.getFrom();
        double range2End = range.getTo();

        if (range1End <= range2Start) { // первый диапазон полностью слева
            return null;
        }

        if (range2End <= range1Start) { // второй диапазон полностью слева
            return null;
        }

        // определяем границы пересечения
        double newStart = Math.max(range1Start, range2Start);
        double newEnd = Math.min(range1End, range2End);

        if (newStart >= newEnd) { // проверяем, что длина диапазона больше нуля
            return null;
        }

        return new Range(newStart, newEnd);
    }

    public Range[] getSetsUnion(Range range) {
        double range1Start = this.getFrom();
        double range1End = this.getTo();

        double range2Start = range.getFrom();
        double range2End = range.getTo();

        boolean intersects = this.isInside(range2Start) || this.isInside(range2End) ||
                range.isInside(range1Start) || range.isInside(range1End); // проверяем пересечения диапазонов

        if (intersects) { // диапазоны пересекаются
            double minStart = Math.min(range1Start, range2Start);
            double maxEnd = Math.max(range1End, range2End);

            return new Range[]{new Range(minStart, maxEnd)};
        } else { // диапазоны не пересекаются
            return new Range[]{this, range};
        }
    }

    public Range[] getSetsDifference(Range range) {
        double range1Start = this.getFrom();
        double range1End = this.getTo();

        double range2Start = range.getFrom();
        double range2End = range.getTo();

        Range[] result = new Range[2]; // массив для хранения до двух диапазонов результата
        int rangeQuantity = 0;

        if (range1Start < range2Start && range1End > range2Start) { // проверяем левый участок
            result[rangeQuantity] = new Range(range1Start, range2Start);
            rangeQuantity++;
        }

        if (range1End > range2End && range1Start < range2End) { // проверяем правый участок
            result[rangeQuantity] = new Range(range2End, range1End);
            rangeQuantity++;
        }

        if (range1End <= range2Start) { // если первый диапазон полностью слева
            result[rangeQuantity] = new Range(range1Start, range1End);
            rangeQuantity++;
        }

        if (range1Start >= range2End) { // если первый диапазон полностью справа
            result[rangeQuantity] = new Range(range1Start, range1End);
            rangeQuantity++;
        }

        Range[] finalResult = new Range[rangeQuantity]; // создаём итоговый массив нужного размера

        System.arraycopy(result, 0, finalResult, 0, rangeQuantity);

        return finalResult;
    }

    public void printInformation(){
        System.out.printf("%nНачало диапазона: %.2f%n", this.getFrom());
        System.out.printf("Конец диапазона: %.2f%n", this.getTo());
        System.out.printf("Длина диапазона: %.2f%n%n", this.getLength());
    }
}