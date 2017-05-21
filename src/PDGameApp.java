/*********************************************************************************
    File Name      :  PDGameApp.java
    Program        :  Prisoner's dilemma
    Programmed by  :  Vishal Vallabh Panguru 
 ********************************************************************************/

/*********************************************************************************
    Class name:  PDGameApp
    Description: This class contains the main() method and uses the methods from
                 PDGame and GameStat to implement prisoner's dilemma problem.
 ********************************************************************************/

//importing necessary packages
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.io.FileNotFoundException;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.HashMap;
import java.util.Date;

public class PDGameApp extends JFrame implements ActionListener, ListSelectionListener 
{

	
	int rounds;                                                                     //number of rounds
	int uyears = 0, cyears = 0;                                                     //user and computer sentence
	String gameStartTime;                                                           //Game start time
        String file = "strategy.txt";                                                   //text file with computer decisions
        
	private final HashMap<String, GameStat> hashmap = new HashMap<>();              //Hashmap to store strategies
	private final DefaultListModel<String> listModel = new DefaultListModel<>();    //Listmodel
        private final JList<String> finishedGamesList =new JList<>();                   //Jlist to be put in Listmodel
        
        //Initialization of various labels, TextFields and Buttons
	private final JLabel LRounds = new JLabel("Rounds Played");
        private final JTextField TFRounds = new JTextField();
        private final JLabel LCStrat = new JLabel("Computer Strategy");
        private final JTextField TFCStrat = new JTextField();
        private final JLabel LPSentence = new JLabel("Player Sentence");
        private final JTextField TFPSentence = new JTextField();
        private final JLabel LCSentence = new JLabel("Computer Sentence");
        private final JTextField TFCSentence = new JTextField();
        private final JLabel LWinner = new JLabel("Winner");
        private final JTextField TFWinner = new JTextField();
        private final JButton Bstrt = new JButton("Start New Game");
        private final JButton Bsilent = new JButton("Remain Silent");
        private final JButton Btestify = new JButton("Testify");
        private final JLabel LDecision = new JLabel("Your decision this round?");
        
        //Initialization of various Panels
        private final JPanel panel1 = new JPanel();
        private final JPanel panel2 = new JPanel();
	private final JPanel panel1S = new JPanel();
	private final JPanel panel2N = new JPanel();
        private final JPanel panel2NN = new JPanel();
        private final JPanel panel2NS = new JPanel();
    
       
        private final JLabel LCombo = new JLabel("Computer Strategy:");
        private final JComboBox<Object> CStrategyCB;                                        //ComboBox for Strategies
        private final JTextArea gameResultsTA = new JTextArea();
        private PDGame pg;
        private GameStat gs;
        
   
        //main() method
        public static void main(String[] args) 
        {
            
                createAndShowGUI();           
			
	} //end main()
        
        
        //Method to create and show GUI
	public static void createAndShowGUI() 
        {
		//Create and set up the window
		PDGameApp gui = new PDGameApp();                        //call the constructor below to set the window to user 
		gui.addListeners();                                     //method will add listeners to buttons
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.pack();                                             //packs together all the panels
		gui.setVisible(true);                                   //window is visible
	}
        
        //Constructor where SWING interface is built
	public PDGameApp() 
        {
                super("Prisoner's Dilemma");                            //call made to base class constructor
                gs = new GameStat();                                
                
                TitledBorder title;                                     //title for the border
		title = BorderFactory.createTitledBorder("List of Games");
		panel1.setBorder(title);
                
                //setting up left panel
		panel1.setLayout(new BorderLayout());
                Color cl = new Color(110,115,170);
                panel1.setBackground(cl);
                add(panel1,BorderLayout.WEST);
		
                //setting up top left list
                finishedGamesList.setModel(listModel);
		finishedGamesList.setFont(new Font("SansSerif", Font.BOLD,14));
		finishedGamesList.setVisibleRowCount(10);
		finishedGamesList.setFixedCellWidth(350);
                finishedGamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel1.add(new JScrollPane(finishedGamesList), BorderLayout.NORTH);
		
                //setting up left bottom panel
                panel1.add(panel1S,BorderLayout.SOUTH);
		GridLayout grid = new GridLayout(5,2);
		grid.setVgap(5);
                //filling the grid
		panel1S.setLayout(grid);
		panel1S.setBackground(cl);     
		panel1S.add(LRounds);
		panel1S.add(TFRounds);
		panel1S.add(LCStrat);
		panel1S.add(TFCStrat);
		panel1S.add(LPSentence);
		panel1S.add(TFPSentence);
		panel1S.add(LCSentence);
		panel1S.add(TFCSentence);
		panel1S.add(LWinner);
		panel1S.add(TFWinner);
		
		//setting the editable
		TFRounds.setEditable(false);
		TFCStrat.setEditable(false);
		TFPSentence.setEditable(false);
		TFCSentence.setEditable(false);
		TFWinner.setEditable(false);
		
		//setting up right panel
                panel2.setLayout(new BorderLayout());
                add(panel2,BorderLayout.EAST);
		
                //setting up top right panel
                panel2.add(panel2N,BorderLayout.NORTH);
                
                //try-catch block if file is not found
                try
                {
                    pg = new PDGame(file);   
                }    
                catch (FileNotFoundException e1)
                {
                    gameResultsTA.setText("\nRequired File not found...Exiting program.");
                    System.exit(1);
                }
		
                //creating a combo-box for storing strategies
                Object[] strategies = pg.getStrategies().toArray();
		CStrategyCB = new JComboBox<>(strategies);
                CStrategyCB.setEditable(false);
		CStrategyCB.setSelectedIndex(0);
                
                //setting up panel2NN and panel2NS so that top right panel is set
                //setting up panel2NN
                Color o = new Color(220,210,155);
		panel2NN.setLayout(new FlowLayout());
		panel2NN.setBackground(o);
		panel2NN.add(LCombo);
		panel2NN.add(CStrategyCB);
		panel2NN.add(Bstrt);
		
		//setting up panel2NS
		panel2NS.setLayout(new FlowLayout());
		panel2NS.setBackground(o);
		panel2NS.add(LDecision);
		panel2NS.add(Bsilent);
		panel2NS.add(Btestify);
	
		panel2N.setLayout(new GridLayout(2,1));
		panel2N.add(panel2NN);
		panel2N.add(panel2NS);
		
                //setting up right bottom panel with Text Area
		gameResultsTA.setRows(15);
		gameResultsTA.setColumns(35);
		gameResultsTA.setEditable(false);
		JScrollPane scrollTxt = new JScrollPane(gameResultsTA);
		panel2.add(scrollTxt,BorderLayout.SOUTH);		
		
	}   //end constructor
   
   
        //method to hook up listeners to buttons
	public void addListeners() 
        {
		Bstrt.addActionListener(this);
		Bsilent.addActionListener(this);
		Btestify.addActionListener(this);
		finishedGamesList.addListSelectionListener(this);
	}
   
	
        
   
	//method to show results for the game when user clicks on a finished game in upper left list box
         @Override
	public void valueChanged(ListSelectionEvent list) 
        {
            
		//if user clicks the list
		if(list.getSource() == finishedGamesList) 
                {   
                    //if the list is not empty
                    if(!finishedGamesList.isSelectionEmpty())
                    {
			String searchKey = (String)finishedGamesList.getSelectedValue();
			gs = hashmap.get(searchKey);
			//Rounds played is displayed
			TFRounds.setText(Integer.toString(gs.getRounds()));
                        TFRounds.setFont(new Font("SansSerif", Font.BOLD, 14));
			//Computer Strategy is displayed
                        TFCStrat.setText(gs.getCompStat());
                        TFCStrat.setFont(new Font("SansSerif", Font.BOLD, 14));
                        
                        cyears = gs.getComputerYears();
                        uyears = gs.getUserYears();
			//User sentence is displayed
                        TFCSentence.setText(String.format("%d %s", cyears, ((cyears >1)?"years":"year")));
                        TFCSentence.setFont(new Font("SansSerif", Font.BOLD, 14));
			//Computer sentence is displayed
                        TFPSentence.setText(String.format("%d %s", uyears, ((uyears >1)?"years":"year")));
                        TFPSentence.setFont(new Font("SansSerif", Font.BOLD, 14));
			//Winner is displayed
                        TFWinner.setText(gs.getWinner());
                        TFWinner.setFont(new Font("SansSerif", Font.BOLD, 14));
                    }
                }
	}
   
	
        //Method handels what button was clicked and calls appropriate method
        @Override
	public void actionPerformed ( ActionEvent e ) 
        {       
                //on clicking start button
		if (e.getSource() == Bstrt) 
                {
                    startGame();
		} 
                //on clicking silent button
                else if (e.getSource() == Bsilent) 
                { 
                    cooperate();
		}
                //on clicking testify button
		else if(e.getSource() == Btestify) 
                { 
                    betray();
		}
	}
   
	
        //Method starts the game
        public void startGame()
        {
                //try-catch block if file is not found
                try
                {
                    pg = new PDGame(file);   
                }    
                catch (FileNotFoundException e1)
                {
                    gameResultsTA.setText("\nRequired File not found...Exiting program.");
                    System.exit(1);
                }
		gameStartTime = (new Date()).toString();
                //setting Buttons to be enable or not
		Bsilent.setEnabled(true);
		Btestify.setEnabled(true);
                LDecision.setEnabled(true);
		Bstrt.setEnabled(false);
		LCombo.setEnabled(false);
		CStrategyCB.setEnabled(false);
                //displaying the text when the game starts
		gameResultsTA.setText("***Prisoner's Dilemma***\n");
		gameResultsTA.append("1. Cooperate with your partner and remain silent.\n 2. Betray and testify against your partner. \n\n What is your decision this round?\n"); 
		rounds= 1;
		pg.setStrategy(CStrategyCB.getSelectedIndex()+1);
        }
        
        //Method which enables computer to remain silent
        public void cooperate()
        {
                int tot_rounds = gs.getRounds();
                String result = pg.playRound(1);
		gameResultsTA.append("\n"+result+"\n");
		//if 5 rounds are played	
		if(rounds== tot_rounds) 
                {
                    getFinalResult();
		}
                //for each round
                else 
                {
                    ++rounds;
                    gameResultsTA.append("1. Cooperate with your partner and remain silent.\n 2. Betray and testify against your partner. \n\n What is your decision this round?\n"); 
                }
        }
        
        
        //Method which enables computer to testify against the user
        public void betray()
        {
                int tot_rounds = gs.getRounds();
                String result = pg.playRound(2);
		gameResultsTA.append("\n"+result+"\n");
		//if 5 rounds are played	
		if(rounds== tot_rounds) 
                {
                    getFinalResult();
		} 
                //for each round
                else 
                {
                    ++rounds;
                    gameResultsTA.append("1. Cooperate with your partner and remain silent.\n 2. Betray and testify against your partner. \n\n What is your decision this round?\n");
		}
        }
	
        
        //Method to display final result after the game
        public void getFinalResult() 
        {
		gameResultsTA.append("\n"+pg.getScores()+"\n");
		gs = pg.getStats();
		hashmap.put(gameStartTime,gs);
		listModel.addElement(gameStartTime);
		//setting Buttons to be enable or not after each game
                Bsilent.setEnabled(false);
		Btestify.setEnabled(false);
                LDecision.setEnabled(false);
		Bstrt.setEnabled(true);
		LCombo.setEnabled(true);
		CStrategyCB.setEnabled(true);   
	}
} //end class PDGameApp