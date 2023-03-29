package com.example;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Data;

import java.io.Serializable;
import java.text.DecimalFormat;

import static java.lang.Math.log;
import static java.lang.Math.tan;

@Named
@Data
@SessionScoped
public class LogicBean implements Serializable {
    private double startValue;
    private double endValue;
    private double stepValue;
    private double a;
    private double b;
    private double c;
    private int numberOfSteps;
    private double biggestY;
    private double biggestX;
    private double smallestY;
    private double smallestX;
    private double sumOfElements;
    private double averageOfElements;

    public double tabulation(double x, double a, double b, double c) {
        double eps = 0.0001;
        if (x < 1.4 - eps)
            return a*x*x + b*x + c;
        else if (x > 1.4 + eps)
            return (a + b*x) / Math.sqrt(x*x + 1);
        else {
            return a/x + Math.sqrt(x*x + 1);
        }
    }

    public int calculateSteps(double x1, double x2, double step) {
        return (int) ((x2 - x1) / step) + 1;
    }

    public double[] xValuesArrayCreate(double x1, double x2, double step) {
        return new double[calculateSteps(x1, x2, step)];
    }

    public double[] yValuesArrayFill(double[] xValues, double a, double b, double c) {
        double[] yValues = new double[xValues.length];
        for (int i = 0; i < yValues.length; i++) {
            yValues[i] = tabulation(xValues[i], a, b, c);
        }
        return yValues;
    }

    public double[] xValuesArrayFill(double x1, double x2, double step) {
        double[] xValues = xValuesArrayCreate(x1, x2, step);
        for (int i = 0; i < xValues.length; i++) {

            xValues[i] = x1 + i * step;
        }
        return xValues;
    }

    public int getMinIndex(double[] yValues) {
        int minIndex = 0;
        for (int i = 0; i < yValues.length; i++) {
            if (yValues[i] < yValues[minIndex]) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    public double getMinElement(double[] yValues) {
        int minNumber = getMinIndex(yValues);
        return yValues[minNumber];
    }

    public double getMinElementArgument(double[] yValues, double[] xValues) {
        int minNumber = getMinIndex(yValues);
        return xValues[minNumber];
    }

    public int getMaxIndex(double[] yValues) {
        int maxIndex = 0;
        for (int i = 0; i < yValues.length; i++) {
            if (yValues[i] > yValues[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public double getMaxElement(double[] yValues) {
        int maxNumber = getMaxIndex(yValues);
        return yValues[maxNumber];
    }

    public double getMaxElementArgument(double[] yValues, double[] xValues) {
        int maxNumber = getMaxIndex(yValues);
        return xValues[maxNumber];
    }

    public double getSum(double[] yValues) {
        double sum = 0;
        for (int i = 0; i < yValues.length; i++) {
            sum = sum + yValues[i];
        }
        return sum;
    }

    public double getAverage(double[] yValues) {
        double sum = getSum(yValues);
        double average;
        average = sum / yValues.length;
        return average;
    }

    public String forRun(){
        double[] xValues = xValuesArrayFill(startValue, endValue, stepValue);
        double[] yValues = yValuesArrayFill(xValues, a, b, c);

        numberOfSteps=calculateSteps(startValue, endValue, stepValue);
        biggestY=getMaxElement(yValues);
        biggestX=getMaxElementArgument(yValues, xValues);
        smallestY=getMinElement(yValues);
        smallestX=getMinElementArgument(yValues, xValues);
        sumOfElements=getSum(yValues);
        averageOfElements=getAverage(yValues);
        return "calculate-Page";
    }

    public String returnToMainPage(){
        return "main-Page.xhtml";
    }
}
