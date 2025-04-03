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

    public Range getSetsIntersection(Range range2) {
        double range2StartPoint = range2.getFrom();
        double range2EndPoint = range2.getTo();

        double resultRangeStartPoint = this.from;
        double resultEndPoint = this.from;

        boolean isStartInside =  isInside(range2StartPoint);
        boolean isEndInside =  isInside(range2EndPoint);

        if(!isStartInside || !isEndInside){
            return null;
        }

        if(this.from > resultRangeStartPoint){

        }

        return new Range(resultRangeStartPoint, resultEndPoint);
    }
}

