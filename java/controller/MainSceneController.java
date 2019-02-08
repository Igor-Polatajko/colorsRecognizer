package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.FrameAnalyzer;
import model.WebCam;
import org.opencv.core.Mat;

import java.util.Map;


public class MainSceneController {
    @FXML
    private ImageView currentFrame;

    @FXML
    private Label redLabel;

    @FXML
    private Label greenLabel;

    @FXML
    private Label blueLabel;

    @FXML
    private Label colorLabel;

    @FXML
    private ToggleGroup filters;


    private WebCam webCam;
    private FrameAnalyzer frameAnalyzer;

    @FXML
    protected void initialize() {
        webCam = new WebCam();
        frameAnalyzer = new FrameAnalyzer();

        webCam.startWebCam((frame, mat) -> {
            currentFrame.setImage(frame);
            Platform.runLater(() -> updateAnalyzeLabels(mat));
        });
    }

    public void radioButtonAction() {
        switch (((RadioButton) filters.getSelectedToggle()).getText()) {
            case "None":
                webCam.setFilter(WebCam.Filter.NONE);
                break;
            case "Red":
                webCam.setFilter(WebCam.Filter.RED);
                break;
            case "Green":
                webCam.setFilter(WebCam.Filter.GREEN);
                break;
            case "Blue":
                webCam.setFilter(WebCam.Filter.BLUE);
                break;
        }
    }

    private void updateAnalyzeLabels(Mat mat) {
        Map<String, Object> analyzeResults = frameAnalyzer.analyzeFrame(mat);

        redLabel.setText("Red - " + analyzeResults.get("red") + "%");
        greenLabel.setText("Green - " + analyzeResults.get("green") + "%");
        blueLabel.setText("Blue - " + analyzeResults.get("blue") + "%");

        colorLabel.setText((String) analyzeResults.get("colorName"));
        colorLabel.setTextFill((Color) analyzeResults.get("colorLabelColor"));
    }
}
