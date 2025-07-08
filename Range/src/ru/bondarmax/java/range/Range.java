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

    public Range[] getSetsUnion(Range range) {
        double rangeStartPoint = range.getFrom();
        double rangeEndPoint = range.getTo();

        double thisRangeStartPoint = this.getFrom();
        double thisRangeEndPoint = this.getTo();

        if (this.isInside(rangeStartPoint) && this.isInside(rangeEndPoint)) {
            return getNewRange(thisRangeStartPoint, thisRangeEndPoint);
        } else if (range.isInside(thisRangeStartPoint) && range.isInside(thisRangeEndPoint)) {
            return getNewRange(rangeStartPoint, rangeEndPoint);
        } else if (this.isInside(rangeStartPoint) || range.isInside(thisRangeEndPoint)) {
            return getNewRange(thisRangeStartPoint, rangeEndPoint);
        } else if (this.isInside(rangeEndPoint) || range.isInside(thisRangeStartPoint)) {
            return getNewRange(rangeStartPoint, thisRangeEndPoint);
        } else {
            return new Range[]{range, this};
        }
    }

    private Range[] getNewRange(double startPoint, double endPoint) {
        return new Range[]{new Range(startPoint, endPoint)};
    }

    /*public Range[] getSetsDifference(Range range) {
        double rangeStartPoint = range.getFrom();
        double rangeEndPoint = range.getTo();

        double thisRangeStartPoint = this.getFrom();
        double thisRangeEndPoint = this.getTo();


        if ((thisRangeStartPoint == rangeStartPoint) && (thisRangeEndPoint == rangeEndPoint) {
            return null;
        }
        else if (!this.isInside(rangeStartPoint) && !this.isInside(rangeEndPoint)) {
            return 
        }
    }*/
}


