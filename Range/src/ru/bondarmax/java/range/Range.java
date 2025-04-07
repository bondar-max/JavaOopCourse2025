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
        double resultRangeStartPoint = this.getFrom();
        double resultRangeEndPoint = this.getTo();

        if(this.isInside(range.getFrom())){
            resultRangeStartPoint = range.getFrom();
        }

        if(this.isInside(range.getTo())){
            resultRangeEndPoint = range.getTo();
        }



       /* int resulRangeLength = 0;

        int rangeLength = (int) (range.getLength());
        int rangeNumber = (int) range.getFrom();
        int i = 0;

        for (; i <= rangeLength; rangeNumber++, i++) {
            if (isInside(rangeNumber)) {
                resultRangeStartPoint = rangeNumber;
                resulRangeLength++;
                break;
            }
        }

        for (; i <= rangeLength; rangeNumber++, i++) {
            if (isInside(rangeNumber)) {
                resultRangeEndPoint = rangeNumber;
                resulRangeLength++;
            }
        }

        if (resulRangeLength <= 1) {
            return null;
        }*/

        return new Range(resultRangeStartPoint, resultRangeEndPoint);
    }
}

