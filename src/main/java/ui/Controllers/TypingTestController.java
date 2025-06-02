package ui.Controllers;

import TypingTest.Words;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import TypingTest.*;

public class TypingTestController {

    @FXML private Label wordLabel;
    @FXML private TextField inputField;
    @FXML private Label feedbackLabel;
    @FXML private Label timeLabel;
    @FXML private Button startButton;
    @FXML private Button restartButton;
    @FXML private Label correctLabel;
    @FXML private Label incorrectLabel;
    @FXML private Label statsLabel;

    Words words;
    TypingTest test;

    @FXML
    public void initialize() {
        wordLabel.setText("Ready?");
        inputField.setDisable(true);
        feedbackLabel.setText("");
        timeLabel.setText("");
        correctLabel.setText("Correct: 0");
        incorrectLabel.setText("Incorrect: 0");
        statsLabel.setText("WPM: 0.0 | Avg Time: 0.0s");
        startButton.setDisable(false);
        restartButton.setDisable(true);

        startButton.setOnAction(this::start);
        restartButton.setOnAction(this::restart);

        this.words = new Words("src/main/resources/Words.txt");
        inputField.setOnAction(e -> inputFieldHandler());
    }

    @FXML
    public void start(ActionEvent event) {
        inputField.setDisable(false);
        startButton.setDisable(true);
        restartButton.setDisable(false);

        test = new TypingTest(words.getWordList(), new TypingTest.TypingListener() {
            @Override
            public void onWordTimeout(String word) {

                Platform.runLater(() -> {
                    feedbackLabel.setText("Timeout!");
                });

            }

            @Override
            public void onNewWord(String word) {

                Platform.runLater(() -> {

                    correctLabel.setText("Correct: " + test.getResult().correctCount);
                    incorrectLabel.setText("Incorrect: " + test.getResult().incorrectCount);
                    statsLabel.setText("Avg Time: " + String.format("%.2f", test.getResult().getAvgTimeSeconds()) + "s");

                    wordLabel.setText(word);
                    inputField.clear();
                    feedbackLabel.setText("");
                });
            }

            @Override
            public void onTestFinished(int correct, int incorrect, double avgTime , long elapsedTime) {


                Platform.runLater(() -> {
                    feedbackLabel.setText("Test finished!");
                    correctLabel.setText("Correct: " + correct);
                    incorrectLabel.setText("Incorrect: " + incorrect);
                    statsLabel.setText("Total time : " + elapsedTime/1000 + " Avg Time: " + String.format("%.2f", avgTime) + "s");

                    inputField.setDisable(true);
                    startButton.setDisable(true);
                    restartButton.setDisable(false);
                });
            }
        });

        test.start();
    }

    @FXML
    public void restart(ActionEvent event) {
        // todo : is this optimal? or it should stop and then start on user click
        start(event);
    }

    @FXML
    public void inputFieldHandler() {
        if (test != null && test.isRunning()) {
            String userInput = inputField.getText().trim();
            test.submitWord(userInput);
        }
    }
}
