package main.java.oop_fundamentals.class_problems;
import java.util.*;

abstract class Question {
    protected String questionText;
    protected String correctAnswer;

    public Question(String questionText, String correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    public abstract boolean evaluateAnswer(String answer);

    public String getQuestionText() {
        return questionText;
    }
}

class MultipleChoiceQuestion extends Question {
    public MultipleChoiceQuestion(String questionText, String correctAnswer) {
        super(questionText, correctAnswer);
    }

    @Override
    public boolean evaluateAnswer(String answer) {
        return this.correctAnswer.equalsIgnoreCase(answer.trim());
    }
}

class Examination {
    private String title;
    private List<Question> questions;

    public Examination(String title) {
        this.title = title;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    public String getTitle() {
        return title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getTotalQuestions() {
        return questions.size();
    }
}

class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Attempt {
    private Student student;
    private Examination examination;
    private Map<Integer, String> answers;
    private boolean isSubmitted;
    private int score;

    public Attempt(Student student, Examination examination) {
        this.student = student;
        this.examination = examination;
        this.answers = new HashMap<>();
        this.isSubmitted = false;
        this.score = 0;
        System.out.println("Examination '" + examination.getTitle() + "' started by " + student.getName() + ".");
    }

    public void answerQuestion(int questionIndex, String answer) {
        if (isSubmitted) {
            System.out.println("Cannot modify answers. Examination has already been submitted.");
            return;
        }
        answers.put(questionIndex, answer);
        System.out.println("Question " + (questionIndex + 1) + " answered with '" + answer + "'.");
    }

    public void submit() {
        if (isSubmitted) {
            System.out.println("Examination is already submitted.");
            return;
        }

        isSubmitted = true;
        evaluate();
        System.out.println("Examination '" + examination.getTitle() + "' submitted successfully.");
        System.out.println("Result for '" + examination.getTitle() + "' attempt: " + score + "/" 
                            + examination.getTotalQuestions() + " correct");
    }

    private void evaluate() {
        score = 0;
        List<Question> questions = examination.getQuestions();
        for (int i = 0; i < questions.size(); i++) {
            String studentAnswer = answers.get(i);
            if (studentAnswer != null && questions.get(i).evaluateAnswer(studentAnswer)) {
                score++;
            }
        }
    }
}

public class Examsystem {
    public static void main(String[] args) {
        Examination mathQuiz = new Examination("Math Quiz");
        mathQuiz.addQuestion(new MultipleChoiceQuestion("What is 2 + 2?", "A"));
        mathQuiz.addQuestion(new MultipleChoiceQuestion("What is 5 x 3?", "B"));

        Student student = new Student("Student");

        Attempt attempt = new Attempt(student, mathQuiz);
        attempt.answerQuestion(0, "A");
        attempt.answerQuestion(1, "C");
        attempt.submit();
    }
}
