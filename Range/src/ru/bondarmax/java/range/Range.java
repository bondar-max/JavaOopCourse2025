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
        double resultRangeStartPoint = 0;
        double resultRangeEndPoint = 0;

        double rangeStartPoint = range.getFrom();
        double rangeEndPoint = range.getTo();

        double currentRangeStartPoint = this.getFrom();
        double currentRangeEndPoint = this.getTo();

        if (this.isInside(rangeStartPoint)) {
            resultRangeStartPoint = rangeStartPoint;
        } else if (range.isInside(currentRangeStartPoint)) {
            resultRangeStartPoint = currentRangeStartPoint;
        }

        if (this.isInside(rangeEndPoint)) {
            resultRangeEndPoint = rangeEndPoint;
        } else if (range.isInside(currentRangeEndPoint)) {
            resultRangeEndPoint = currentRangeEndPoint;
        }

        if ((resultRangeEndPoint - resultRangeStartPoint) < 1)
            return null;

        return new Range(resultRangeStartPoint, resultRangeEndPoint);
    }

    public Range getSetsUnion(Range range) {
        double resultRangeStartPoint = 0;
        double resultRangeEndPoint = 0;

        double rangeStartPoint = range.getFrom();
        double rangeEndPoint = range.getTo();

        double currentRangeStartPoint = this.getFrom();
        double currentRangeEndPoint = this.getTo();

        if(this.isInside(rangeStartPoint) && this.isInside(rangeEndPoint)) {
            resultRangeStartPoint = currentRangeStartPoint;
            resultRangeEndPoint = currentRangeEndPoint;
        } else if(range.isInside(currentRangeStartPoint) && range.isInside(currentRangeEndPoint)){
            resultRangeStartPoint = rangeStartPoint;
            resultRangeEndPoint = rangeEndPoint;
        }

        if(this.isInside(rangeStartPoint)){
            resultRangeStartPoint = currentRangeStartPoint;
            resultRangeEndPoint = rangeEndPoint;
        }

        if(this.isInside(rangeEndPoint)){
            resultRangeStartPoint = rangeStartPoint;
            resultRangeEndPoint = currentRangeEndPoint;
        }

        return null;
    }
}

