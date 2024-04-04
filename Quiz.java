import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Question {
    private String question;
    private List<String> options;
    private int correctOption;

    public Question(String question, List<String> options, int correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectOption() {
        return correctOption;
    }
}

public class Quiz {
    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;
    private Timer timer;
    private final int QUESTION_TIME_LIMIT = 10; // Time limit for each question in seconds
    private Scanner scanner;

    public Quiz(List<Question> questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
        this.score = 0;
        this.timer = new Timer();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        askQuestion();
    }

    private void askQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            System.out.println(currentQuestion.getQuestion());
            List<String> options = currentQuestion.getOptions();
            for (int i = 0; i < options.size(); i++) {
                System.out.println(options.get(i));
            }

            // Start timer for the question
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Time's up!");
                    currentQuestionIndex++;
                    askQuestion();
                }
            }, QUESTION_TIME_LIMIT * 1000);

            System.out.print("Enter your choice (1-" + options.size() + "): ");
            int userChoice = -1;
            if (scanner.hasNextInt()) {
                userChoice = scanner.nextInt();
            } else {
                System.out.println("Invalid input! Please enter an integer.");
            }
            timer.cancel(); // Cancel the timer if the user provides an answer

            if (userChoice == currentQuestion.getCorrectOption()) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Incorrect!");
            }

            currentQuestionIndex++;
            askQuestion();
        } else {
            endQuiz();
        }
    }

    private void endQuiz() {
        System.out.println("Quiz ended. Your score: " + score);
        timer.cancel(); // Cancel active timers
        scanner.close(); // Close scanner after the quiz ends
    }

    public static void main(String[] args) {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What does HTML stand for?", List.of("A. Hyper Text Markup Language", "B. High Tech Markup Language", "C. Hyperlink and Text Markup Language", "D. Hyper Transfer Markup Language"), 1));
        questions.add(new Question("What is the largest planet in our solar system?", List.of("A. Mars", "B. Jupiter", "C. Saturn", "D. Earth"), 2));
        questions.add(new Question("What is the abbreviation of AI?", List.of("A. Artificial Internet", "B. Artificial Intranet", "C. Artificial Intelligence", "D. None of the above"), 3));
        questions.add(new Question("Which data structure follows Last In, First Out (LIFO) approach?", List.of("A. Queue", "B. Stack", "C. Linked List", "D. Tree"), 2));
        questions.add(new Question("What keyword is used to define a subclass in Java?", List.of("A. class", "B. subclass", "C. superclass", "D. extends"), 4));
        questions.add(new Question("What is the time complexity of finding the median of an unsorted array using the QuickSelect algorithm?", List.of("A. O(n)", "B. O(n log n)", "C. O(n^2)", "D. O(log n)"), 1));

        Quiz quiz = new Quiz(questions);
        quiz.start();
    }
}
