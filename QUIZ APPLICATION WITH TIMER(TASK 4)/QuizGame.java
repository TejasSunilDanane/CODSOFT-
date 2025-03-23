import java.util.*;
import java.util.concurrent.*;

class QuizQuestion {
    String question;
    String[] options;
    int correctAnswer;

    public QuizQuestion(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
}

public class QuizGame {
    private static final int TIME_LIMIT = 10; // Time limit in seconds per question
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<QuizQuestion> questions = new ArrayList<>();
    private static int score = 0;

    public static void main(String[] args) {
        loadQuestions();
        playQuiz();
        displayResults();
    }

    private static void loadQuestions() {
        questions.add(new QuizQuestion("What is the capital of France?", new String[]{"1. Berlin", "2. Madrid", "3. Paris", "4. Rome"}, 3));
        questions.add(new QuizQuestion("Which planet is known as the Red Planet?", new String[]{"1. Earth", "2. Mars", "3. Jupiter", "4. Venus"}, 2));
        questions.add(new QuizQuestion("Who wrote 'Hamlet'?", new String[]{"1. Charles Dickens", "2. J.K. Rowling", "3. William Shakespeare", "4. Jane Austen"}, 3));
    }

    private static void playQuiz() {
        for (QuizQuestion q : questions) {
            System.out.println("\n" + q.question);
            for (String option : q.options) {
                System.out.println(option);
            }
            
            int answer = getUserAnswerWithTimer();
            if (answer == q.correctAnswer) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong! The correct answer was: " + q.correctAnswer);
            }
        }
    }

    private static int getUserAnswerWithTimer() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(() -> {
            System.out.print("Enter your answer (1-4) within " + TIME_LIMIT + " seconds: ");
            return scanner.nextInt();
        });
        
        try {
            return future.get(TIME_LIMIT, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("\nTime's up! Moving to the next question.");
            return -1;
        } finally {
            executor.shutdownNow();
        }
    }

    private static void displayResults() {
        System.out.println("\nQuiz Over!");
        System.out.println("Your final score: " + score + " / " + questions.size());
    }
}
