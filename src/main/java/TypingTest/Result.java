package TypingTest;

public class  Result {
    public int totalCount = 0;
    public int correctCount = 0;
    public int incorrectCount = 0;
    public long totalTimeTaken = 0; // in milliseconds

    public Result(int totalCount) {
        this.totalCount = totalCount;
    }

    public void addCorrect(long timeTaken) {
        correctCount++;
        totalTimeTaken += timeTaken;
    }

    public void addIncorrect(long timeTaken) {
        incorrectCount++;
        totalTimeTaken += timeTaken;
    }

    public double getAvgTimeSeconds() {
        int totalAnswered = correctCount + incorrectCount;
        return totalAnswered == 0 ? 0 : (totalTimeTaken / (double) totalAnswered) / 1000.0;
    }
}
