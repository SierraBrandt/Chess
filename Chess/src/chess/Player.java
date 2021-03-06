package chess;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BorderLayout;


public class Player {
    //Players
    private static Player currentTurn;
    public static Player players[] = new Player[4];
    public static int turnTracker = 0;
    public static boolean PlayerInCheck = false;
    
    //Player Info  
    private int playerNum;
    public boolean inCheck = false;
    private int points;
    private String name;
    
    
    Player( int num)
    {
        playerNum = num;
        points=0;
        name=PickName(playerNum);
    }
    public static void Reset()
    {
        players[0] = new Player(0);//player 1 white / red 
        players[1] = new Player(1);//player 2 black /blue
        players[2] = new Player(2);//player 1 green
        players[3] = new Player(3);//player 2 yell
        currentTurn = players[0];
        turnTracker = 0;
        PlayerInCheck = false;
    }
    public static void NewGameReset(){
        currentTurn = players[0];
        turnTracker = 0;
        PlayerInCheck = false;
    }
    public static Player GetCurrentPlayer()
    {
        return(currentTurn);
    }
    public static void SwitchTurn()
    {
        if(Chess.normalMode){
            if (currentTurn == players[0])
                currentTurn = players[1];
            else
                currentTurn = players[0];
        }
        else if(Chess.P4Mode){
            turnTracker++;
            if(turnTracker == 4)
                turnTracker = 0;
            currentTurn = players[turnTracker];
        }
        PlayerInCheck = false;
        for(int i = 0; i < players.length; i++){
            if(players[i].inCheck)
                PlayerInCheck = true;
        }
    }
    public Integer GetPlayerNumber()
    {
        return (playerNum);
    }
    public Integer GetPlayerPoints(){
        return(points);
    }
    public static void AddPoint(int x){
        players[x].points++;
    }
    public String PickName(int _num){
       if(_num==0)
           return("Player1");
       else if(_num==1)
           return("Player2");
       else if(_num==2)
           return("Player3");
       else 
           return("Player4");
    }
    public static String getName(int x){
        return(players[x].name);
    }
    public static void Draw(Graphics2D g){
            g.setColor(Color.blue);
            g.drawString(Player.getName(0)+":"+players[0].points,30,640);
            g.setColor(Color.red);            
            g.drawString(Player.getName(1)+":"+players[1].points,200,640);
            g.setColor(Color.green);
            g.drawString(Player.getName(2)+":"+players[2].points,370,640);
            g.setColor(Color.yellow);
            g.drawString(Player.getName(3)+":"+players[3].points,550,640);
    }
//    public static void JFrame(){
//        JFrame frame = new JFrame("Player Names");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
//        frame.pack();
//        frame.setVisible(true);
//    }

}