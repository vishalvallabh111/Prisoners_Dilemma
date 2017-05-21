/*********************************************************************************
    File Name      :  PDGame.java
    Program        :  Prisoner's dilemma
    Programmed by  :  Vishal Vallabh Panguru
 ********************************************************************************/


/*************************************************************************************
    Class name  : PDGame
    Description : This class implements how to play the game based of various computer
                  strategies using the methods in PDGame.
 ************************************************************************************/

//importing packages

import java.util.*;
import java.io.*;
import java.util.ArrayList;


public class PDGame 
{
	
    private static  ArrayList<String> strategies;   //ArrayList of Computer Strategies
    private final ArrayList<Integer> history;       //ArrayList to store history of users decisions
    GameStat gstat = null;                          //GameStat object
    private int compstrat;                          //Computer strategy
    Scanner s;                                      //Scanner variable
        
    //constructor used for initialization
    public PDGame(String file) throws FileNotFoundException
    {       
            
            history = new ArrayList<>();                  
            gstat = new GameStat();                                                
            strategies = new ArrayList<>();
            strategies.add("1. Input File");
            strategies.add("2. Tit-For-Tat");
            strategies.add("3. Tit-For-Two-Tats");
            strategies.add("4. Random Choice");
            File file1 = new File(file);
            s = new Scanner(file1); 
    }   //end constructor    
	
    
    //To set computer's strategy
    public void setStrategy(int compstrat) throws InputMismatchException
    {
            this.compstrat = compstrat;
            gstat.setCompStat(strategies.get(compstrat-1));
    }

    //To return computer Strategies
    public ArrayList<String> getStrategies() 
    {
            return strategies;
    }
    
    //To return statistics for each game
    public GameStat getStats() 
    {
            return gstat;
    }
	
    //To implement various computer strategies and take a decision
    public int compDesc()
    {
            int decision; //computers decision
	
            // 1. To read from a file
            if(compstrat == 1)
            {
    
		if(s.hasNextInt())
                {
                   decision = s.nextInt();
                }
                else
                {
                    decision = -1;
                }
            }
		
            // 2. Tit-For-Tat 
            else if(compstrat == 2)	
            {
                if(history.isEmpty()) 
                {
                    decision = 1;
                } 
                else 
                {
                    int lastMove = history.get(history.size()-1);
                    decision = lastMove;
                }
            }		
				
            //3. Tit-For-Two-Tats
            else if(compstrat == 3)
            {
		if(history.size() < 2) 
                {
                    decision = 1;
		} 
                else 
                {
                    if(history.get(history.size() - 1) == 2 && history.get(history.size() - 2) == 2) 
                    {
			decision = 2;
                    } 
                    else 
                    {
			decision = 1;
                    }
		}
            }		
				
            //4. Random Choice
            else if( compstrat == 4)
            {
                decision = (((int)(Math.random()*10))%2 + 1);
            } 
            
            else
            {
               decision = -1;   
            }    
            return decision;   
    }
	
	
    //Method for playing each round and returns the prison imprisonment stats
    
    public String playRound(int dec)
    {
            int compDesc;           //computer decision
            int uyears;             //user years in prison
            int comyears;           //computer years in prison
            String res;             //variable containing imprisonment stats
		
            compDesc = compDesc();
            history.add(dec);       //adding to users history
		
            if(compDesc == -1)
            {
                res = "Error in making computer's decision";
                uyears = 0;
                comyears = 0;
            }
            else if(dec == compDesc) 
            {
                //Both user and computer cooperate and remain silent
                if(dec == 1) 
                {
                    res = " You and your partner remain silent.\n You both get 2 years in prison.\n";
                    uyears = 2;
                    comyears = 2;
                }
                
                //Both user and computer betray and testify
                else 
                {
                    res = " You and your partner testify against each other.\n You both get 3 years in prison.\n";
                    uyears = 3;
                    comyears = 3;
                }
		
            } 
                
            // User testifies and computer remains silent
            else if(dec > compDesc) 
            {
                res = " You tesify against your partner and they remain silent.\n You get 1 year in prison and they get 5 years.\n";
                uyears = 1;
                comyears = 5;
            }
                
            // Computer testifies and user remains silent
		 
            else 
            {
                res = " You remain silent and your partner tesifies against you.\n You get 5 years in prison and your partner gets 1 year.\n";
                uyears = 5;
                comyears = 1;
            }
		
            gstat.update(uyears, comyears);     //updating stats
            return res;
    }
	
    //This method returns the final scores of the game
    
    public String getScores()
    {
            int uyears = gstat.getUserYears();
            int comyears = gstat.getComputerYears();
            System.out.println("END OF ROUNDS, GAME OVER --GAME STATS--");
            String score = "Your total prison sentence is " + uyears + " years.\n" + "Your partner's total prison sentence is "+ comyears +" years."; 
            return score;
    }	
} //end class PDGame