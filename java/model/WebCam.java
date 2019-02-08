package model;

import javafx.scene.image.Image;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class WebCam {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private VideoCapture capture = new VideoCapture(0);

    private ColorsRange colorsRange = new ColorsRange();
    private WebCamCallback callback;
    private Filter currentFilter = Filter.NONE;
    private Timer timer;
    private TimerTask mainLoop;


    public interface WebCamCallback {
        void setFrame(Image frame, Mat mat);
    }

    public enum Filter {
        NONE, RED, GREEN, BLUE
    }

    public void startWebCam(WebCamCallback callback) {
        this.callback = callback;
        startMainLoop();
    }

    public void setFilter(Filter currentFilter) {
        this.currentFilter = currentFilter;
    }

    private Mat getFrameMat() {
        Mat mat = new Mat();
        capture.read(mat);

        if (currentFilter != Filter.NONE) {
            mat = matFilter(mat);
        }
        return mat;
    }

    private Mat matFilter(Mat mat) {
        Map<String, Mat> rgbMats = colorsRange.getRanges(mat);

        switch (currentFilter) {
            case RED:
                return rgbMats.get("red");
            case GREEN:
                return rgbMats.get("green");
            case BLUE:
                return rgbMats.get("blue");
        }
        return mat;
    }


    private Image getFrameImageFromMat(Mat mat) {
        Image frame;
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".bmp", mat, buffer);
        frame = new Image(new ByteArrayInputStream(buffer.toArray()));
        return frame;
    }

    private void startMainLoop() {
        mainLoop = new TimerTask() {
            @Override
            public void run() {
                Mat mat = getFrameMat();
                callback.setFrame(getFrameImageFromMat(mat), mat);
            }
        };

        timer = new Timer();
        timer.scheduleAtFixedRate(mainLoop, 0, 50);
    }

}

