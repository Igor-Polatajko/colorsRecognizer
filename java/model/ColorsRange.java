package model;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import java.util.Map;
import java.util.TreeMap;

public class ColorsRange {
    private Scalar redLow = new Scalar(0, 0, 130); // bgr
    private Scalar redHigh = new Scalar(150, 150, 255);

    private Scalar greenLow = new Scalar(0, 120, 0); // bgr
    private Scalar greenHigh = new Scalar(165, 255, 165);

    private Scalar blueLow = new Scalar(140, 0, 0); // bgr
    private Scalar blueHigh = new Scalar(255, 160, 160);

//    private Scalar yellowLow = new Scalar(0, 220, 240); // bgr
//    private Scalar yellowHigh = new Scalar(50, 255, 255);
//
//    private Scalar whiteLow = new Scalar(240, 240, 240); // bgr
//    private Scalar whiteHigh = new Scalar(255, 255, 255);
//
//    private Scalar blackLow = new Scalar(0, 0, 0); // bgr
//    private Scalar blackHigh = new Scalar(15, 15, 15);


    public Map<String, Mat> getRanges(Mat mat){
        Map<String, Mat> rgbMats = new TreeMap<>();

        rgbMats.put("red", new Mat());
        rgbMats.put("green", new Mat());
        rgbMats.put("blue", new Mat());
//        rgbMats.put("yellow", new Mat());
//        rgbMats.put("white", new Mat());
//        rgbMats.put("black", new Mat());

        Core.inRange(mat, redLow, redHigh, rgbMats.get("red"));
        Core.inRange(mat, greenLow, greenHigh, rgbMats.get("green"));
        Core.inRange(mat, blueLow, blueHigh, rgbMats.get("blue"));
//        Core.inRange(mat, yellowLow, yellowHigh, rgbMats.get("yellow"));
//        Core.inRange(mat, whiteLow, whiteHigh, rgbMats.get("white"));
//        Core.inRange(mat, blackLow, blackHigh, rgbMats.get("black"));

        return rgbMats;
    }
}
