/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MagicBoard;

import java.sql.SQLException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

/**
 * 魔板游戏类
 * @author 
 */
public class  MagicBoardFrame extends Application 
{
    
    //数字板块相关-----------
    private int [] boardNumberList = {0,1,2,3,4,5,6,7,8};
    private int[][] boardNumber = {{0,1,2},{3,4,5},{6,7,8}}; 
    private int[][] suspendStatu= {{0,0,0},{0,0,0},{0,0,0}};
    private Board[][] board = new Board[3][3];
    
    //显示时间
    private Text showTime = new Text("show the time");
    private Timer timer = new Timer();
    private TimerTask task = new TimerTasks();
    
    //各种时间属性
    private long startTime = 0;
    private long playTime = 0;
    private long suspendTime = 0;
    private long suspendAt = 0;
    private int isSuspend = 0;
    private int isEnd = 0;
    
    
    @Override
    public void start(Stage primaryStage) 
    {

        //开始计时
        this.startTime = System.currentTimeMillis();
        this.timer.schedule(task,1,100);
        
       //一堆button
        Button newGame = new Button("  NewGame  ");
        newGame.setOnAction(e->newGame());
        Button suspend = new Button(" Suspend  ");
        suspend.setOnAction(e->suspend());
        Button continues = new Button(" Continue ");
        continues.setOnAction(e->continues());
        Button top = new Button("   Top   ");
        top.setOnAction(e->top());
        Button reStart = new Button("  ReStart ");
        reStart.setOnAction(e->reStart());
        Button quit = new Button("   Quit   ");
        quit.setOnAction(e->quit());
        
        //把button放在一行
        HBox funcButtonLine = new HBox();
        funcButtonLine.getChildren().addAll(newGame,suspend,continues,top,reStart,quit);
        
        //生成数字板的数字       
        createNumberBoardNumber();
        
        //生成数字板
        createNumberBoard();
        GridPane gridPane = new GridPane();
        for(int i = 0;i<3;i++)
            for(int j = 0;j<3;j++)
                gridPane.add(this.board[i][j],j, i);       
        
        //把button，数字板块，显示时间的Text分别放在窗口的上，中，下位置
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(funcButtonLine);
        borderPane.setCenter(gridPane);
        borderPane.setBottom(showTime);
        
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Magic Board Game");
        primaryStage.show();
           
    }   
    
    private void newGame()
    {
        createNumberBoardNumber();
        
        for(int i = 0;i<3;i++)
            for(int j = 0;j<3;j++)
                this.board[i][j].setNumber(this.boardNumber[i][j]);
        
        this.startTime = System.currentTimeMillis();
        this.playTime = 0;
        this.suspendTime = 0;
        this.suspendAt = 0;
        this.isSuspend = 0;
        this.isEnd = 0;
    }
    private void suspend()
    {
        if(this.isSuspend==0&&this.isEnd==0)
        {
            this.playTime = System.currentTimeMillis()-this.startTime-this.suspendTime;
            this.suspendAt = System.currentTimeMillis();
            this.isSuspend = 1;        
        
            for(int i = 0;i<3;i++)
                 for(int j = 0;j<3;j++)
                 {
                      this.suspendStatu[i][j] = this.board[i][j].getNumber();
                      this.board[i][j].setNumber(-1);    
                  }
        }
    }
    private void continues()
    {
        if(this.isSuspend==1)
        {
            this.suspendTime += System.currentTimeMillis()-this.suspendAt;       
            this.isSuspend = 0;   
       
            for(int i = 0;i<3;i++)
                for(int j = 0;j<3;j++)
                    this.board[i][j].setNumber(this.suspendStatu[i][j]);
        }
    }
    private void top()
    {
        try {
                TopScore sg = new TopScore();
            } catch (SQLException ex) {
                Logger.getLogger(MagicBoardFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(MagicBoardFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    private void reStart()
    {        
        for(int i = 0;i<3;i++)
            for(int j = 0;j<3;j++)
                this.board[i][j].setNumber(this.boardNumber[i][j]);
        
        this.startTime = System.currentTimeMillis();
        this.playTime = 0;
        this.suspendTime = 0;
        this.suspendAt = 0;
        this.isSuspend = 0;
        this.isEnd = 0;
    }
    private void quit()
    {
        System.exit(0);
    }
    private void save()
    {
        this.isEnd = 1;
        this.playTime = System.currentTimeMillis()-this.startTime-this.suspendTime;
        try {
               Save svvePlayer = new Save(String.format("%1$tM:%1$tS",this.playTime));
            } catch (SQLException ex) {
                Logger.getLogger(MagicBoardFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(MagicBoardFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    public void setNumberBoardNewNumber(int row,int col,int number)
    {
        this.board[row][col].setNumber(number);
    }    
    public int getNumberBoardNumber(int row,int col)
    {
        return this.board[row][col].getNumber();
    }
    public boolean isWin()
    {
        int f = 0;
        for(int i = 0;i<3;i++)
            for(int j = 0;j<3;j++)
            {
                if(i==2&&j==2)
                    return true;
                if(f>this.board[i][j].getNumber())
                    return false;
                f = this.board[i][j].getNumber();
            }        
        return true; 
    }
    private void createNumberBoardNumber()
    {
            int t,q,k,z,w,row=0,col=0,total = 0;
            Random random = new Random();
            //将预先设定的二位数组打乱
            for(int i = 0;i<9;i++)
            {
                    q = random.nextInt()%3;
                    k = random.nextInt()%3;
                    z = random.nextInt()%3;
                    w = random.nextInt()%3;
                    if(q<0)
                        q *= -1;
                    if(k<0)
                        k *= -1;
                    if(z<0)
                        z *= -1;
                    if(w<0)
                        w *= -1;
                    t = this.boardNumber[q][k];
                    this.boardNumber[q][k] = this.boardNumber[z][w];
                    this.boardNumber[z][w] = t;    
            }
            //生成判断数组
            for(int i = 0;i<3;i++)
                for(int j = 0;j<3;j++)
                {
                    this.boardNumberList[i*3+j] = this.boardNumber[i][j];
                    if(this.boardNumber[i][j]==0)
                    {
                        row = i;
                        col = j;
                    }
                }
            //判断随机生成的数列的逆序对奇偶性
            for(int i = 0;i<8;i++)
                for(int j = i+1;j<9;j++)
                    if(this.boardNumberList[i]>this.boardNumberList[j])
                        total++;   
            //如果随机生成的数字序列不存在完成性，则交换任意两个非空格快
            if(total%2!=(row+col)%2)
            {
                if(this.boardNumber[0][0]!=0&&this.boardNumber[0][1]!=0)
                {
                    int  temp = this.boardNumber[0][0];
                    this.boardNumber[0][0] = this.boardNumber[0][1];
                    this.boardNumber[0][1] = temp;
                }
                else
                {
                    int  temp = this.boardNumber[1][0];
                    this.boardNumber[1][0] = this.boardNumber[1][1];
                    this.boardNumber[1][1] = temp;
                }     
            }
    }
    public void createNumberBoard()
    {
        for(int i = 0;i<3;i++)
            for(int j = 0;j<3;j++)
                this.board[i][j] = new Board(this.boardNumber[i][j],i,j);
    }
    /**
    * 时间任务类
    * @author 
    */
    public class TimerTasks extends TimerTask
    {
        @Override
        public void run()
        { 
                if(isSuspend==1||isEnd==1)
                    showTime.setText(String.format("%1$tM:%1$tS",playTime));
                else
                {
                    String str = String.format("%1$tM:%1$tS",System.currentTimeMillis() - startTime - suspendTime);  
                    showTime.setText(str.substring(0, 5));
                }
        }     
    }   
    /**
    * 数字魔板类
    * @author 
    */
    public class Board extends Pane
    {
        private int x;
        private int y;
        private int number;
        private Text showNumber = new Text(this.getWidth()+73,this.getHeight()+60,"");
        
        public Board(int number,int x,int y)
        {
            setNumber(number);
            setXY(x, y);
            this.getChildren().add(showNumber);
            this.setStyle("-fx-border-color:black");
            this.setOnMouseClicked(e->clickMe());
            this.setPrefSize(146,120);
        }
        private void clickMe()
        {
            //只有点击非空白块才进行变动
            if(getNumber()!=0)
            {
                boolean notSet = true;
                int[] xy = getXY();
                int x = xy[0];
                int y = xy[1];
                int[][] flags = {{x,y+1},{x,y-1},{x+1,y},{x-1,y}};
                
                //判断相邻的方块是否有空白块，有则和空白块互换，否则不变
                if(flags[0][1]<3&&notSet)
                    if(getNumberBoardNumber(flags[0][0],flags[0][1])==0)
                    {
                        notSet = false;
                        setNumberBoardNewNumber(flags[0][0],flags[0][1],this.number);
                        setNumber(0);
                    }
                if(flags[1][1]>-1&&notSet)
                    if(getNumberBoardNumber(flags[1][0],flags[1][1])==0)
                    {
                        notSet = false;
                        setNumberBoardNewNumber(flags[1][0],flags[1][1],this.number);
                        setNumber(0);
                    }
                if(flags[2][0]<3&&notSet)
                    if(getNumberBoardNumber(flags[2][0],flags[2][1])==0)
                    {
                        notSet = false;
                        setNumberBoardNewNumber(flags[2][0],flags[2][1],this.number);
                        setNumber(0);
                    }
                if(flags[3][0]>-1&&notSet)
                    if(getNumberBoardNumber(flags[3][0],flags[3][1])==0)
                    {
                        notSet = false;
                        setNumberBoardNewNumber(flags[3][0],flags[3][1],this.number);
                        setNumber(0);
                    }
            }
            if(isWin())             
                save();
        }
        public int[] getXY()
        {
            int[] xy = new int[2];
            xy[0] = this.x;
            xy[1] = this.y;
            return xy;
        }
        public void setXY(int x,int y)
        {
            this.x = x;
            this.y = y;
        }
        public int getNumber()
        {
            return this.number;
        }
        public void setNumber(int number)
        {
            this.number = number;
            if(this.number ==0)
                this.showNumber.setText("");
            else if(this.number == -1)
                this.showNumber.setText("哈");
            else
                this.showNumber.setText(""+this.number);      
        }       
    }
 
    public static void main(String[] args) 
    {
        launch(args);
    }    
}
