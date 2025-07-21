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
        double range1Start = range.getFrom();
        double range1End = range.getTo();

        double range2Start = this.getFrom();
        double range2End = this.getTo();

        if(range1End <= range2Start){ // первый диапазон полностью слева
            return null;
        }

        if(range2End <= range1Start){ // второй диапазон полностью слева
            return null;
        }

        // определяем границы пересечения
        double newStart;
        double newEnd;

        if(range1Start < range2Start){
            newStart = range2Start;
        } else{
            newStart = range1Start;
        }

        if(range1End <= range2End){
            newEnd = range1End;
        } else{
            newEnd = range2End;
        }

        if(newStart >= newEnd){ // проверяем, что длина диапазона больше нуля
            return null;
        }

        return new Range(newStart, newEnd);
    }

    public Range[] getSetsUnion(Range range) {
        double range1Start = range.getFrom();
        double range1End = range.getTo();

        double range2Start = this.getFrom();
        double range2End = this.getTo();

        boolean intersects = this.isInside(range2Start) || this.isInside(range2End) ||
                range.isInside(range1Start ) || range.isInside(range1End); // проверяем пересечения диапазонов

        if(intersects){ // диапазоны пересекаются
            double minStart = Math.min(range1Start, range2Start);
            double maxEnd = Math.max(range1End,range2End);

            return new Range[]{new Range(minStart, maxEnd)};
        } else { // диапазоны не пересекаются
            return new Range[]{this, range};
        }
    }

    private Range[] getNewRange(double startPoint, double endPoint) {
        return new Range[]{new Range(startPoint, endPoint)};
    }

    public Range[] getSetsDifference(Range range) {
        double range2Start = range.getFrom();
        double range2End = range.getTo();

        double range1Start = this.getFrom();
        double range1End = this.getTo();

        Range[] result = new Range[2]; // массив для хранения до двух диапазонов результата
        int count = 0;

        if(range1Start < range2Start && range1End > range2Start){ // проверяем левый участок
            result[count] = new Range(range1Start, range2Start);
            count++;
        }

        if(range1End > range2End && range1Start < range2End){ // проверяем правый участок
            result[count] = new Range(range2End, range1End);
            count++;
        }

        if(range1End <= range2Start) { // если первый диапазон полностью слева
            result[count] = new Range(range1Start, range1End);
            count++;
        }

        if(range1Start >= range2End){ // если первый диапазон полностью справа
            result[count] = new Range(range1Start , range1End);
            count++;
        }

        Range[] finalResult = new Range[count]; // создаём итоговый массив нужного размера

        System.arraycopy(result, 0, finalResult, 0, count);

        return finalResult;
    }
}


