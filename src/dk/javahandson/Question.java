package dk.javahandson;

public class Question {
    private int id;
    private String question;
    private String answer;

    public Question(int id,String answer,String question){
        this.id= id;
        this.question = question;
        this.answer=answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() { return answer; }

    public void setAnswer(String answer) { this.answer = answer; }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
