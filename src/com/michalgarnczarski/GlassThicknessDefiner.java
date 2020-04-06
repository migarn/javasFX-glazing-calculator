package com.michalgarnczarski;

public class GlassThicknessDefiner {
    private Glass glass;

    public GlassThicknessDefiner(Glass glass) {
        this.glass = glass;
    }

    public int defineThickness() {
        if (glass.getSpacerThickness() >= 16) {
            return defineThicknessFor16();
        } else if (glass.getSpacerThickness() >= 12) {
            return defineThicknessFor12();
        } else if (glass.getSpacerThickness() >= 9) {
            return defineThicknessFor9();
        } else if (glass.getSpacerThickness() >= 6) {
            return defineThicknessFor6();
        } else {
            return -1;
        }
    }

    public int defineThicknessFor16() {
        if (glass.getArea() <= 1.5 && glass.getLongerDimension() <= 1500 && glass.getDimensionsRatio() > 1 / 6) {
            return 3;
        } else if (glass.getArea() <= 3.35 && glass.getLongerDimension() <= 2500 && glass.getDimensionsRatio() > 1 / 6) {
            return 4;
        } else if (glass.getArea() <= 5 && glass.getLongerDimension() <= 3300) {
            return 5;
        } else if (glass.getArea() <= 7 && glass.getLongerDimension() <= 3500) {
            return 6;
        } else if (glass.getArea() <= 10 && glass.getLongerDimension() <= 5000) {
            return 8;
        } else if (glass.getArea() <= 13.5 && glass.getLongerDimension() <= 5000) {
            return 10;
        } else if (glass.getArea() <= 13.5 && glass.getLongerDimension() <= 6000) {
            return 12;
        } else {
            return -1;
        }
    }
}
