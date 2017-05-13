/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MagicBoard;

import dao.PlayerDao;
import domain.Player;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

/**
 * 排行榜类
 * @author 
 */
public class TopScore{
        
    public TopScore() throws SQLException, Exception
    {
        Pane pane = new VBox();
        PlayerDao playerDao = new PlayerDao();
        List<Player> players = playerDao.query();
        Collections.sort(players, new Comparator<Player>() 
        {
            @Override
                       
            public int compare(Player o1, Player o2)
            {            
                String t1 =o1.getTime();
                String t2 =o2.getTime();
                return t1.compareTo(t2)<0?-1:1;
            }

        });
        
        Image image = new Image("image/2.jpg");
        pane.getChildren().add(new ImageView(image));
        
        for (int i=0;i<players.size()&&i<10;i++) 
        {
            Player player=players.get(i);
            String playerScore = String.format("%-8s\t\t\t   %s",player.getName(),player.getTime());
            Text show = new Text(playerScore);
            show.setUnderline(true);
            show.setFont(Font.font("Times New Roman",15.0));
            pane.getChildren().add(show);
        }
        
        Scene scene=new Scene(pane,175,200);  
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
    }
}