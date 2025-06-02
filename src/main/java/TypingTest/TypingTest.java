package TypingTest;

import java.time.format.ResolverStyle;
import java.util.Collections;
import java.util.List;

public class TypingTest {

    private List<String> wordList;
    private Result result;
    private TypingListener listener;

    private int index = 0;
    private String currentWord;
    private long wordStartTime;

    private Timer timer;
    private boolean running = false; // ✅ Added flag

    public boolean isRunning() {
        return running;
    }

    public interface TypingListener {
        void onWordTimeout(String word);
        void onNewWord(String word);
        void onTestFinished(int correct, int incorrect, double avgTime , long elapsedTime);
    }

    public TypingTest(List<String> words, TypingListener listener) {
        this.wordList = words;
        Collections.shuffle(this.wordList);
        this.listener = listener;
        this.result = new Result(this.wordList.size());
    }

    public void start() {
        index = 0;
        running = true;
        startNextWord();
    }

    private void startNextWord() {
        if (timer != null) timer.stop();

        if (index >= wordList.size()) {
            running = false;
            listener.onTestFinished(result.correctCount, result.incorrectCount, result.getAvgTimeSeconds() , timer.getElapsedTime());
            return;
        }

        currentWord = wordList.get(index++);
        wordStartTime = System.currentTimeMillis();

        long timeout = getTimeoutFor(currentWord);
        timer = new Timer(timeout, () -> {
            result.addIncorrect(timeout);
            listener.onWordTimeout(currentWord);
            startNextWord();
        });
        timer.start();
        listener.onNewWord(currentWord);
    }

    private long getTimeoutFor(String word) {
        return 1000L * Math.max(2, word.length());
    }

    public void submitWord(String typed) {
        if (!running) return;
        if (timer != null) timer.stop();

        long timeTaken = System.currentTimeMillis() - wordStartTime;
        if (typed.trim().equals(currentWord)) {
            result.addCorrect(timeTaken);
        } else {
            result.addIncorrect(timeTaken);
        }

        startNextWord();
    }

    public Result getResult() {
        return result;
    }
}
