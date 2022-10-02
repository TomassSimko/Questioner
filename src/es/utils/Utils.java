package es.utils;

import dk.javahandson.User;
import en.assignment.gui.QuestController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utils {

    public static void changeScene(ActionEvent event, String fxmlFile,String name){
        Parent root = null;

        if(name != null){
            try {
                FXMLLoader loader = new FXMLLoader(Utils.class.getResource(fxmlFile));
                root = loader.load();
                QuestController pc = loader.getController();
                pc.setName(name);
            }catch(IOException e){
                e.printStackTrace();
            }

        } else {
            try{
                root = FXMLLoader.load(Objects.requireNonNull(Utils.class.getResource(fxmlFile)));
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Welcome to questioner " + name);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }


    public static void saveData(ActionEvent event, User user) {
        try(Connection con = DbConnection.getConnection()){
            String sql = "INSERT INTO user (id,name,total) VALUES (?,?,?);";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2,user.getName());
            preparedStatement.setInt(3,user.getTotal());

            preparedStatement.executeUpdate();

            // Todo : check !
            changeScene(event,"/MainWindow.fxml",null);
        }  catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static List<User> fetchData(){
        List<User> userList = new ArrayList<>();
        try (Connection con = DbConnection.getConnection();){
            String sql = "SELECT * FROM user ";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int total = rs.getInt("total");
                double time = rs.getDouble("time");
                boolean isListed = rs.getBoolean("isListed");
                User u = new User(id, name, total,time,isListed);
                userList.add(u);
            }
            return userList;
        } catch (
                SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
