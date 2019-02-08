package model;

import javafx.scene.paint.Color;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.util.Map;
import java.util.TreeMap;

public class FrameAnalyzer {

    private ColorsRange colorsRange = new ColorsRange();

    public Map<String, Object> analyzeFrame(Mat mat) {

        Map<String, Object> result = new TreeMap<>();
        Map<String, Integer> colors = new TreeMap<>();

        Map<String, Mat> rgbMats = colorsRange.getRanges(mat);


        double area = (mat.width() * mat.height());
        int red = (int) Math.round(((double) Core.countNonZero(rgbMats.get("red")) / area) * 100);
        int green = (int) Math.round((Core.countNonZero(rgbMats.get("green")) / area) * 100);
        int blue = (int) Math.round((Core.countNonZero(rgbMats.get("blue")) / area) * 100);
//        int yellow = (int) Math.round((Core.countNonZero(rgbMats.get("yellow")) / area) * 100);
//        int white= (int) Math.round((Core.countNonZero(rgbMats.get("white")) / area) * 100);
//        int black = (int) Math.round((Core.countNonZero(rgbMats.get("black")) / area) * 100);

        colors.put("red", red);
        colors.put("green", green);
        colors.put("blue", blue);
//        colors.put("yellow", yellow);
//        colors.put("white", white);
//        colors.put("black", black);

        if (red > 20 && isDominant(red, colors)) {
            result.put("colorName", "Red");
            result.put("colorLabelColor", Color.RED);
        }
        if (green > 20 && isDominant(green, colors)) {
            result.put("colorName", "Green");
            result.put("colorLabelColor", Color.GREEN);
        }
        if (blue > 20  && isDominant(blue, colors)) {
            result.put("colorName", "Blue");
            result.put("colorLabelColor", Color.BLUE);
        }
//        if (yellow > 20  && isDominant(yellow, colors)) {
//            result.put("colorName", "Yellow");
//            result.put("colorLabelColor", Color.YELLOW);
//        }
//        if (white > 20  && isDominant(white, colors)) {
//            result.put("colorName", "White");
//            result.put("colorLabelColor", Color.GRAY);
//        }
//        if (black > 20  && isDominant(black, colors)) {
//            result.put("colorName", "Black");
//            result.put("colorLabelColor", Color.BLACK);
//        }

        result.putAll(colors);
        return result;
    }

    private boolean isDominant(int color, Map<String, Integer> colors){
        boolean isDominant = true;

        for (String key : colors.keySet()){
            if(color < colors.get(key)){
                isDominant = false;
            }
        }

        return isDominant;
    }
}
