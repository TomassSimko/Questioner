package en.assignment.gui;

import dk.javahandson.Question;
import dk.javahandson.User;
import es.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class QuestController implements Initializable {
    @FXML
    private GridPane gridPane;
    @FXML
    private Button save;
    @FXML
    private ToggleGroup first_question,
            second_question,
            third_question,
            forth_question,
            fifth_question,
            sixth_question,
            seventh_question;
    @FXML
    private Button calculate;
    @FXML
    private Label score_final,
                  full_name;
    private int score = 0;

    private final List<String> resultFromQuestions = new ArrayList<>();
    private final List<Question> resultList = new ArrayList<>();
    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        calculate.setOnAction(this::setTextButton);
        save.setOnAction(event -> Utils.saveData(event, user));
    }

    @FXML
    public void setName(String name) {
        full_name.setText(name);
    }

    @FXML
    public void setTextButton(ActionEvent e) {
        restart();
        getScore();
        save.setDisable(false);
    }

    @FXML
    private void restart() {
        score = 0;
        resultFromQuestions.clear();
        resultList.clear();
    }

    @FXML
    private void getScore() {
        String[] resultGroup1 = first_question.getSelectedToggle().toString().split("'");
        String[] resultGroup2 = second_question.getSelectedToggle().toString().split("'");
        String[] resultGroup3 = third_question.getSelectedToggle().toString().split("'");
        String[] resultGroup4 = forth_question.getSelectedToggle().toString().split("'");
        String[] resultGroup5 = fifth_question.getSelectedToggle().toString().split("'");
        String[] resultGroup6 = sixth_question.getSelectedToggle().toString().split("'");
        String[] resultGroup7 = seventh_question.getSelectedToggle().toString().split("'");

        resultFromQuestions.add(resultGroup1[1]);
        resultFromQuestions.add(resultGroup2[1]);
        resultFromQuestions.add(resultGroup3[1]);
        resultFromQuestions.add(resultGroup4[1]);
        resultFromQuestions.add(resultGroup5[1]);
        resultFromQuestions.add(resultGroup6[1]);
        resultFromQuestions.add(resultGroup7[1]);

        List<String> questionList = new ArrayList<>();

        for (int i = 2; i < 9; i++) {
            Node nodeOut = gridPane.getChildren().get(i);
            if(nodeOut instanceof HBox){
                for(Node nodeIn:((HBox)nodeOut).getChildren()){
                    if(nodeIn instanceof Label){
                        questionList.add(((Label)nodeIn).getText());
                }
            }
        }
    }

        for (int i = 0; i < resultFromQuestions.size(); i++) {
            switch (resultFromQuestions.get(i)) {
                case "Disagree" -> {
                    score -= 1;
                    resultList.add(new Question(i + 1, questionList.get(i),"Disagree"));
                }
                case "Neutral" -> {
                    score += 0;
                    resultList.add(new Question(i + 1, questionList.get(i), "Neutral"));
                }
                case "Agree" -> {
                    score += 1;
                    resultList.add(new Question(i + 1, questionList.get(i),"Agree"));
                }
                default -> {
                }
            }
        }
            user = new User(0, full_name.getText(), score,resultList);
            score_final.setText(String.valueOf(score));

    }
}
