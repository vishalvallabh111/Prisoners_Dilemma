/*********************************************************************************
    File Name :     GameStat.java
    Program   :     Prisoner's dilemma
    Programmed by : Vishal Vallabh Panguru 
 ********************************************************************************/


/*********************************************************************************
    Class name: GameStat
    Description: This class contains statistics of each game.
 ********************************************************************************/

public class GameStat 
{
	
	private int uyears ;            //years a user is imprisoned
	private int compyears ; 	//years computer is imprisoned
	private String compStat;	// Computer Strategy 
	private final int rounds = 5;   //number of rounds;
	
        //This method returns number of rounds
        public int getRounds()
        {
            return rounds;
        }        
	//This method sets the computer strategy.
	public void setCompStat(String compStat) 
        {
            this.compStat = compStat;
	}
        
        //This method updates the sentences of user and computer 
	public void update(int uyears, int compyears)
	{
            this.uyears = this.uyears + uyears;
            this.compyears = this.compyears + compyears;	
	}
	
        //This method returns the computer Strategy being used
	public String getCompStat() 
        {
            return compStat.substring(3);
	}
        
	//This method returns the user sentence
	public int getUserYears() 
	{
            return uyears;
	}

	// This method returns the computer sentence
	public int getComputerYears() 
	{
            return compyears;
	}

	//This method returns the winner and the strategy used.
	public String getWinner()
	{
            String winner;
            if(uyears > compyears) 
            {	
		winner = "computer";
            } 
            else if(compyears > uyears) 
            {	
		winner = "player";
            } 
            else 
            {
		winner = "tie";
            }
            return winner;
	}	
}//end GameStat class
