/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MagicBoard;

/**
 *
 * @author 
 */
import dao.PlayerDao;
import domain.Player;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class Save{
        
    public Save(String playTime) throws Exception
    {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(5.5);
        pane.setVgap(5.5);
            
        pane.add(new Label("大神不留个名字？"),0,0);
        pane.add(new Label("Name:"),0,1);
        TextField nameText=new TextField("8个字符以内");
        pane.add(nameText,1,1);
        pane.add(new Label("Time:"),0,2);
        pane.add(new Label(playTime), 1, 2);
        Button btSave=new Button("Save");
        pane.add(btSave, 1, 3);
        GridPane.setHalignment(btSave, HPos.RIGHT);
             
        Scene scene=new Scene(pane,300,200);  
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
        btSave.setOnAction(e-> 
        {
            try 
            {
                DataBaseConnection db=new DataBaseConnection();
                System.out.println(nameText.getText());
                db.dbWrite(nameText.getText(), playTime);
            } 
            catch (Exception ex) 
            {
                Logger.getLogger(MagicBoardFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            stage.close();
        });
    }
}