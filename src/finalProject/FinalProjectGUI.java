/* 

 * Project: main

 * Package: finalProject

 * Class: FinalProjectGUI

 * Programmers:  Harrison Hu, Zain Babar

 * Date Created:  5/20/24

 * Description:  Creates all the graphical designs for the games, including buttons,
 *  labels and search fields. Chooses the mystery player for the guessing game and the 
 *  categories for the grid game. Gets all user input and does the comparison operations 
 *  for the grid game. At the end of each game, there is an option to return to the menu. 
 */

package finalProject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class FinalProjectGUI extends JPanel implements ActionListener, KeyListener {

	GuessOperations G = new GuessOperations();
	PlayerGuessCollection P = new PlayerGuessCollection();
	PlayerGridCollection PG = new PlayerGridCollection();

	// Used for constantly updating search bar
	ArrayList<JButton> dropDownMenu = new ArrayList<JButton>();
	final char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
			's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	// Variables for the guessing game
	JButton easy, hard, guessPlayer, playAgain, seeRules, back;
	JLabel home, guess, photo, name, team, conference, division, position, height, age, number, heightArrow,
			numberArrow, ageArrow;
	JLabel reveal, showName, win, lose, endBackground, rulesImage;
	JTextField searchField;
	JPanel homePanel, gamePanel, endPanel, rules;

	// Pictures used as backgrounds
	ImageIcon homeScreen = new ImageIcon("Pictures/home screen.jpg");
	ImageIcon guessLayout = new ImageIcon("Pictures/guess player.jpg");
	ImageIcon easyRules = new ImageIcon("Pictures/nba easy mode.jpg");
	ImageIcon hardRules = new ImageIcon("Pictures/nba hard mode.jpg");

	Color vanilla = new Color(242, 239, 229);
	Color tan = new Color(240, 228, 203);
	Color red = new Color(237, 28, 36);
	Color brown = new Color(117, 85, 64);
	Color pink = new Color(248, 193, 225);

	Font titleFont = new Font("OCR A Extended", Font.BOLD, 40);
	Font plainFont = new Font("OCR A Extended", Font.BOLD, 26);
	Font smallFont = new Font("OCR A Extended", Font.BOLD, 14);

	// Get the list of players for the guessing game
	final ArrayList<PlayerGuess> playerList = P.readNBAFile();
	final int SIZE = playerList.size();
	final int FRAMESIZE = 800;

	// Keep track of already guessed players
	ArrayList<PlayerGuess> guessed = new ArrayList<>();
	String userInput = "";
	String searchedPlayer = "";
	int guesses = 0;
	// Player gets 6 guesses to find the mystery player
	final int MAXGUESSES = 6;
	PlayerGuess answer;
	PlayerGuess searched;
	boolean playGuess = true;
	boolean winGuess = false;
	boolean easyMode = true;

	// HOOP GRIDS VARIABLES
	JButton hoopGridsGame, seeHoopRules, hoopBack;
	JTextField guessField;
	PlayerGrid searchedG;
	Color darkGreen = new Color(34, 139, 34);

	ImageIcon hoopHomePage = new ImageIcon("Pictures/hoop grids.jpg");
	ImageIcon gridRules = new ImageIcon("Pictures/hoop grid rules.jpg");
	ImageIcon endGrid = new ImageIcon("Pictures/hoop grid end game.jpg");
	// Drop down menu containing buttons to search players
	ArrayList<JButton> dropDownMenuG = new ArrayList<JButton>();

	// button for each square
	JButton r1c1, r1c2, r1c3, r2c1, r2c2, r2c3, r3c1, r3c2, r3c3;
	String r1, r2, r3, c1, c2, c3;
	String searchedPlayerG = "";

	// Get players and categories
	final ArrayList<PlayerGrid> playerGridList = PG.readGridFile();
	final ArrayList<String> teamCategories = PG.readTeamCategories();
	final ArrayList<String> statCategories = PG.readStatCategories();
	final ArrayList<String> yearCategories = PG.readYearCategories();

	final int LABELSIZE = 60;

	// A full grid will have 9 correct guesses
	final int FULLGRID = 9;

	// keep track of total guesses and correct guesses
	int gridGuesses = 0;
	int correctGuesses = 0;

	boolean playGrid = false;
	boolean winGrid = false;

	// create the home panel
	public FinalProjectGUI() throws InterruptedException {
		setLayout(new BorderLayout());
		homePanel = new JPanel();
		homePanel.setLayout(null);
		add(homePanel, BorderLayout.CENTER);

		start();
	}

	// method to clear a panel
	private void clearPanel(JPanel p) {
		p.removeAll();
		p.revalidate();
		p.repaint();
	}

	// method to update a panel
	private void updatePanel(JPanel p) {
		p.revalidate();
		p.repaint();
	}

	// start the program by adding the home screen background and buttons to choose
	// games
	private void start() throws InterruptedException {
		home = new JLabel(homeScreen);
		home.setBounds(0, 0, FRAMESIZE, FRAMESIZE);
		homePanel.add(home);
		homePanel.setVisible(true);

		// User must choose easy or hard mode before playing guessing game

		easy = new JButton("EASY");
		easy.setFont(smallFont);
		easy.setBackground(brown);
		easy.setForeground(tan);
		easy.setVisible(true);
		easy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// enable the guessing game button when difficulty chosen
				easyMode = true;
				guessPlayer.setEnabled(true);
				hoopGridsGame.setEnabled(false);
				easy.setEnabled(false);
				hard.setEnabled(false);
			}
		});
		easy.setBorderPainted(false);
		easy.setBounds(202, 200, 80, 20);
		home.add(easy);

		hard = new JButton("HARD");
		hard.setFont(smallFont);
		hard.setBackground(brown);
		hard.setForeground(tan);
		hard.setVisible(true);
		hard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				easyMode = false;
				guessPlayer.setEnabled(true);
				hoopGridsGame.setEnabled(false);
				easy.setEnabled(false);
				hard.setEnabled(false);
			}
		});
		hard.setBorderPainted(false);
		hard.setBounds(521, 200, 80, 20);
		home.add(hard);

		// Button to play guessing game
		guessPlayer = new JButton("Guess the NBA Player!");
		guessPlayer.setEnabled(false);
		guessPlayer.setFont(plainFont);
		guessPlayer.setForeground(brown);
		guessPlayer.setBackground(pink);
		guessPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// clear the home panel and start guessing game
				clearPanel(homePanel);
				homePanel.setVisible(false);

				try {
					guessPlayer();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});

		guessPlayer.setBorderPainted(false);
		guessPlayer.setBounds(202, 243, 399, 102);
		home.add(guessPlayer);

		// Button to play hoop grid game
		hoopGridsGame = new JButton("NBA Hoop Grids Game!");
		hoopGridsGame.setEnabled(true);
		hoopGridsGame.setFont(plainFont);
		hoopGridsGame.setForeground(brown);
		hoopGridsGame.setBackground(pink);
		hoopGridsGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// clear the panel and start hoop grid game
				clearPanel(homePanel);
				homePanel.setVisible(false);

				try {
					// start hoop grids
					hoopGridsMain();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});

		hoopGridsGame.setBorderPainted(false);
		hoopGridsGame.setBounds(202, 389, 399, 102);
		home.add(hoopGridsGame);

		updatePanel(homePanel);
	}

	/*
	 * 
	 * 
	 * Code For Guessing Game
	 * 
	 * 
	 * 
	 */

	// Choose the mystery player for the guessing game
	private PlayerGuess choosePlayer() {
		int answerIndex = (int) (Math.random() * SIZE);
		return playerList.get(answerIndex);
	}

	// create the game panel and guessing game background
	private void guessPlayer() throws InterruptedException {

		gamePanel = new JPanel();
		gamePanel.setLayout(null);
		gamePanel.setVisible(true);
		add(gamePanel, BorderLayout.CENTER);

		// background image for guessing game
		guess = new JLabel(guessLayout);
		guess.setBounds(0, 0, FRAMESIZE, FRAMESIZE);
		guess.setVisible(true);
		gamePanel.add(guess);
		updatePanel(gamePanel);

		// start the game
		guessStart();
	}

	// start the guessing game
	private void guessStart() {
		// set the answer player
		answer = choosePlayer();

		// create a textfield to get player searches from user
		searchField = new JTextField(25);
		searchField.addActionListener(this);
		searchField.setBounds(195, 668, 399, 30);
		guess.add(searchField);

		gamePanel.addKeyListener(this);

		searchField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				repaint();
				// every time a key is typed in the searchfield, search for a player
				searchGuess(answer);

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// see the rules
		seeRules = new JButton("Rules");
		seeRules.setFont(smallFont);
		seeRules.setForeground(Color.BLACK);
		seeRules.setBackground(pink);
		seeRules.setBounds(700, 0, 100, 20);
		seeRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent seeRules) {
				rules();
			}
		});
		guess.add(seeRules);

		updatePanel(gamePanel);

	}

	// show the appropriate rules for the game
	private void rules() {

		// make a new panel for rules
		rules = new JPanel();
		rules.setLayout(null);
		rules.setVisible(true);
		add(rules, BorderLayout.CENTER);

		gamePanel.setVisible(false);

		// show the correct rules image according to what game/difficulty
		if (playGrid) {
			rulesImage = new JLabel(gridRules);
		}

		else if (easyMode) {
			rulesImage = new JLabel(easyRules);
		}

		else {
			rulesImage = new JLabel(hardRules);
		}

		rulesImage.setBounds(0, 0, 800, 800);

		// make a back button to return to the game
		back = new JButton("Back");
		back.setFont(smallFont);
		back.setForeground(Color.BLACK);
		back.setBackground(pink);
		back.setBounds(0, 0, 100, 20);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent seeRules) {
				rules.setVisible(false);
				gamePanel.setVisible(true);
			}
		});
		rules.add(rulesImage);
		rulesImage.add(back);

		updatePanel(gamePanel);
		updatePanel(rules);

	}

	// get the input from the user and create buttons with matching players
	private void searchGuess(PlayerGuess answer) {
		String searchInput = searchField.getText();

		// remove all components
		for (int i = 0; i < dropDownMenu.size(); i++) {
			guess.remove(dropDownMenu.get(i));
		}

		// update the buttons
		updateDropDownMenu(searchInput, answer);

	}

	// show buttons with players that match search
	public ArrayList<JButton> returnDropDownMenu(String s, final PlayerGuess answer) {
		// if i type the letter "L", 6 players might show up as suggestions, as
		// JButtons. Like the drop down menu that shows from Google searches
		final ArrayList<JButton> dropDownMenu = new ArrayList<>(10);

		int count = 0;

		// loop through all players to check if String s is a substring
		for (int i = 0; i < SIZE; i++) {

			// if it is a substring, make a new JButton
			if (playerList.get(i).getName().toLowerCase().contains(s.toLowerCase())) {

				// create new temp button
				final JButton tempButton = new JButton(playerList.get(i).getName());
				tempButton.setFont(smallFont);
				tempButton.setBackground(pink);
				tempButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent b) {
						// get the player from the button
						searchedPlayer = tempButton.getText();
						for (int i = 0; i < SIZE; i++) {
							if (searchedPlayer.equalsIgnoreCase(playerList.get(i).getName())) {
								searched = playerList.get(i);
							}
						}

						// make sure the player has not already been guessed
						if (!guessed.contains(searched)) {
							try {
								// add the guessed player to the guessed list and increase guesses by 1
								guessed.add(searched);
								guesses++;
								// show the guessed player
								showSearch(searched, answer);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						// just update panel if the player has already been guessed
						// this gives user the chance to guess again
						else {
							updatePanel(gamePanel);
						}

						// remove buttons when guess is made
						for (int i = 0; i < dropDownMenu.size(); i++) {
							guess.remove(dropDownMenu.get(i));
						}

						// clear the text in the search bar
						searchField.setText("");

					}
				});
				// add JButton to List
				dropDownMenu.add(tempButton);

				count++;

				// have a maximum of 10 buttons
				if (count >= 10) {

					// break loop
					i = SIZE;

				}

			}

		}

		// bounds of the search bar
		int x = 195;
		int y = 600;

		for (int i = 0; i < count; i++) {
			// set bounds for each button
			dropDownMenu.get(i).setBounds(x, y, 400, 30);

			// put it up
			y -= 30;
		}

		return dropDownMenu;

	}

	// mereley removing all components, then adding back, we need to call this
	// constantly
	public void updateDropDownMenu(String searchInput, PlayerGuess answer) {

		// update dropdm
		dropDownMenu = returnDropDownMenu(searchInput, answer);

		for (int i = 0; i < dropDownMenu.size(); i++) {
			guess.add(dropDownMenu.get(i));
		}

		updatePanel(gamePanel);

	}

	public void actionPerformed(ActionEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	// Show the searched player and all the data
	// They will be highlighted a certain colour depending on the comparison to
	// mystery player
	private void showSearch(PlayerGuess searched, PlayerGuess answer) throws InterruptedException {

		// show photo of the searched player
		photo = new JLabel(searched.getPlayerIcon());
		photo.setBounds(10, 73 + (100 * (guesses - 1)), LABELSIZE, 85);
		photo.setBackground(red);
		guess.add(photo);

		// show name
		name = new JLabel(" " + searched.getName());
		name.setBounds(80, 85 + (100 * (guesses - 1)), 220, LABELSIZE);
		name.setBackground(G.checkName(searched, answer));
		name.setForeground(Color.BLACK);
		name.setFont(smallFont);
		name.setOpaque(true);
		guess.add(name);

		// Show team with green border if correct, grey border if not
		team = new JLabel(searched.getTeamLogo());
		team.setBounds(305, 80 + (100 * (guesses - 1)), 70, 70);
		if (searched.getTeam().equals(answer.getTeam())) {
			team.setBorder(BorderFactory.createLineBorder(G.green, 3));
		} else {
			team.setBorder(BorderFactory.createLineBorder(G.grey, 3));
		}
		guess.add(team);

		// show conference
		conference = new JLabel(searched.getConference(), SwingConstants.CENTER);
		conference.setBounds(380, 85 + (100 * (guesses - 1)), 80, LABELSIZE);
		conference.setBackground(G.checkConference(searched, answer));
		conference.setForeground(Color.BLACK);
		conference.setOpaque(true);
		conference.setFont(smallFont);
		guess.add(conference);

		// show division
		division = new JLabel(searched.getDivision(), SwingConstants.CENTER);
		division.setBounds(470, 85 + (100 * (guesses - 1)), 95, LABELSIZE);
		division.setBackground(G.checkDivision(searched, answer));
		division.setForeground(Color.BLACK);
		division.setFont(smallFont);
		division.setOpaque(true);
		guess.add(division);

		// show position
		position = new JLabel(searched.getPosition(), SwingConstants.CENTER);
		position.setBounds(575, 85 + (100 * (guesses - 1)), 40, LABELSIZE);
		position.setBackground(G.checkPosition(searched, answer, easyMode));
		position.setForeground(Color.BLACK);
		position.setFont(smallFont);
		position.setOpaque(true);
		guess.add(position);

		// show formatted height
		height = new JLabel(searched.getHeight(), SwingConstants.CENTER);
		height.setBounds(625, 85 + (100 * (guesses - 1)), 50, 40);
		height.setBackground(G.checkHeight(searched, answer, easyMode));
		height.setForeground(Color.BLACK);
		height.setFont(smallFont);
		height.setOpaque(true);
		guess.add(height);

		// show arrows that indicate whether mystery player is taller, shorter or same
		// height (if easy mode)
		if (easyMode) {
			if (searched.getHeightInches() > answer.getHeightInches()) {
				heightArrow = new JLabel("↓", SwingConstants.CENTER);
			} else if (searched.getHeightInches() < answer.getHeightInches()) {
				heightArrow = new JLabel("↑", SwingConstants.CENTER);
			} else if (searched.getHeightInches() == answer.getHeightInches()) {
				heightArrow = new JLabel(" ", SwingConstants.CENTER);
			}
			heightArrow.setBounds(625, 115 + (100 * (guesses - 1)), 50, 35);
			guess.add(heightArrow);
		}

		// show age with arrows if easy mode
		age = new JLabel(String.valueOf(searched.getAge()), SwingConstants.CENTER);
		age.setBounds(685, 85 + (100 * (guesses - 1)), 40, 40);
		age.setBackground(G.checkAge(searched, answer, easyMode));
		age.setForeground(Color.BLACK);
		age.setFont(smallFont);
		age.setOpaque(true);
		guess.add(age);

		if (easyMode) {
			if (searched.getAge() > answer.getAge()) {
				ageArrow = new JLabel("↓", SwingConstants.CENTER);
			} else if (searched.getAge() < answer.getAge()) {
				ageArrow = new JLabel("↑", SwingConstants.CENTER);
			} else if (searched.getAge() == answer.getAge()) {
				ageArrow = new JLabel(" ", SwingConstants.CENTER);
			}
			ageArrow.setBounds(680, 115 + (100 * (guesses - 1)), 50, 35);
			guess.add(ageArrow);
		}

		// show jersey number with arrows if easy mode
		number = new JLabel(String.valueOf(searched.getJerseyNum()), SwingConstants.CENTER);
		number.setBounds(735, 85 + (100 * (guesses - 1)), 40, 40);
		number.setBackground(G.checkJerseyNumber(searched, answer, easyMode));
		number.setForeground(Color.BLACK);
		number.setFont(smallFont);
		number.setOpaque(true);
		guess.add(number);

		if (easyMode) {
			if (searched.getJerseyNum() > answer.getJerseyNum()) {
				numberArrow = new JLabel("↓", SwingConstants.CENTER);
			} else if (searched.getJerseyNum() < answer.getJerseyNum()) {
				numberArrow = new JLabel("↑", SwingConstants.CENTER);
			} else if (searched.getJerseyNum() == answer.getJerseyNum()) {
				numberArrow = new JLabel(" ", SwingConstants.CENTER);
			}
			numberArrow.setBounds(730, 115 + (100 * (guesses - 1)), 50, 35);
			guess.add(numberArrow);
		}

		// if the player is guessed, set win boolean to true
		if (searched.getName().equals(answer.getName())) {
			winGuess = true;
		}

		// if maximum amount of guesses is reached and player not guessed, set to false
		if (guesses == MAXGUESSES && !searched.getName().equals(answer.getName())) {
			winGuess = false;

		}

		updatePanel(gamePanel);

		// end the game if maximum guesses or player guessed
		if (winGuess || guesses == MAXGUESSES) {
			gameOver(answer, winGuess);
		}
	}

	// end of game page
	private void gameOver(PlayerGuess answer, boolean winGuess) throws InterruptedException {
		// slight pause for dramatic effect
		Thread.sleep(200);
		clearPanel(gamePanel);
		gamePanel.setVisible(false);

		// create the ending panel
		endPanel = new JPanel();
		endPanel.setLayout(null);
		endPanel.setBackground(vanilla);
		add(endPanel, BorderLayout.CENTER);

		// add the end background (pink if win, tan if lose)
		endBackground = new JLabel();
		endBackground.setBounds(95, LABELSIZE, 600, 600);
		endBackground.setOpaque(true);
		if (winGuess) {
			endBackground.setBackground(pink);
		}
		if (!winGuess) {
			endBackground.setBackground(tan);
		}
		endPanel.add(endBackground);

		// add the photo of the mystery player
		photo = new JLabel(answer.getPlayerIcon());
		photo.setBounds(266, 150, 60, 90);
		endBackground.add(photo);

		// Message for win or loss
		if (winGuess) {
			win = new JLabel("You Won!", SwingConstants.CENTER);
			win.setBounds(30, 50, 540, 50);
			win.setFont(titleFont);
			win.setForeground(brown);
			endBackground.add(win);
		}
		if (!winGuess) {
			lose = new JLabel("You Didn't Guess It...");
			lose.setBounds(30, 50, 600, 50);
			lose.setFont(titleFont);
			lose.setForeground(brown);
			endBackground.add(lose);
		}

		// reveal the name of the mystery player
		reveal = new JLabel("The Player is...", SwingConstants.CENTER);
		reveal.setBounds(100, 280, 400, 50);
		reveal.setFont(plainFont);
		reveal.setForeground(brown);
		endBackground.add(reveal, BorderLayout.CENTER);

		showName = new JLabel(answer.getName(), SwingConstants.CENTER);
		showName.setBounds(70, 340, 460, 50);
		showName.setFont(plainFont);
		showName.setForeground(brown);
		endBackground.add(showName);

		// button to return to the home screen
		playAgain = new JButton("PLAY AGAIN");
		playAgain.setBounds(100, 450, 400, 50);
		playAgain.setFont(smallFont);
		playAgain.setForeground(brown);
		playAgain.setBackground(vanilla);
		playAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent playAgain) {
				try {

					resetGame();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		endBackground.add(playAgain);

		updatePanel(endPanel);

	}

	// return to the home screen
	private void resetGame() throws InterruptedException {

		// hide the existing panel
		if (endPanel != null) {
			endPanel.setVisible(false);
		}

		if (gamePanel != null) {
			gamePanel.setVisible(false);
		}

		// reset all variables
		guessed = new ArrayList<>();

		userInput = "";
		guesses = 0;
		playGuess = false;
		playGrid = false;
		winGuess = false;
		easyMode = true;

		gridGuesses = 0;
		correctGuesses = 0;

		// start the program again
		start();

	}

	// NBA HOOP GRIDS GAME
	private void hoopGridsMain() throws InterruptedException {

		// create the game panel
		gamePanel = new JPanel();
		gamePanel.setLayout(null);
		gamePanel.setVisible(true);
		add(gamePanel, BorderLayout.CENTER);

		// add background
		guess = new JLabel(hoopHomePage);
		guess.setBounds(0, 0, FRAMESIZE, FRAMESIZE);
		guess.setVisible(true);
		gamePanel.add(guess);
		updatePanel(gamePanel);

		// start the game!
		hoopGridsStart();
	}

	// choose the categories for the rows and columns
	private ArrayList<String> chooseCategories() {

		final int TEAMS = 30;
		ArrayList<Integer> chosenIndexes = new ArrayList<Integer>();
		ArrayList<String> chosenCategories = new ArrayList<String>();

		// chose a random index from teams
		int randomIndex = (int) (Math.random() * (TEAMS - 1));
		chosenIndexes.add(randomIndex);

		// add the teams with the chosen index to the team categories list
		chosenCategories.add(teamCategories.get(randomIndex));

		// we need 4 team categories for the 3 columns and row 1, so this is repeated 3
		// more times
		// make sure the same index is not chosen multiple times
		for (int y = 0; y < 3; y++) {
			while (chosenIndexes.contains(randomIndex)) {
				randomIndex = (int) (Math.random() * (TEAMS - 1));
			}

			chosenIndexes.add(randomIndex);
			chosenCategories.add(teamCategories.get(randomIndex));
		}

		// chose the stat index and category for row 2
		final int STATS = 3;
		randomIndex = (int) (Math.random() * (STATS));
		chosenCategories.add(statCategories.get(randomIndex));

		// chose the season index and category for row 3
		final int SEASONS = 6;
		randomIndex = (int) (Math.random() * (SEASONS - 1));
		chosenCategories.add(yearCategories.get(randomIndex));

		return chosenCategories;

	}

	// add labels for the rows and columns
	private static void categoryLabel(String rc, int a, int b, int c, int d, JLabel g) {
		JLabel ctgLabel;
		// If the category is 20, it means 20+ ppg season, etc.
		if (rc.equals("15")) {
			ctgLabel = new JLabel("15+ PPG Season");
		} else if (rc.equals("7")) {
			ctgLabel = new JLabel("7+ RPG Season");
		} else if (rc.equals("5")) {
			ctgLabel = new JLabel("5+ APG Season");

			// if the category name begins with a 1 or 2, it is a season category
			// the category is players that played in that season
		} else if (rc.charAt(0) == '1' || rc.charAt(0) == '2') {
			ctgLabel = new JLabel("Played In " + rc);
		}

		// the rest are team categories, the label will be the logo of the team
		else {
			ImageIcon categoryIcon = new ImageIcon("teamPictures/" + rc + ".jpg");
			ctgLabel = new JLabel(categoryIcon);
		}
		ctgLabel.setBounds(a, b, c, d);
		ctgLabel.setVisible(true);
		ctgLabel.setOpaque(true);
		g.add(ctgLabel);
	}

	// create a search bar and search for players
	private void gridSearch(final JButton B, final String r, final String c) {

		// create guessing search bar
		guessField = new JTextField(25);
		guessField.addActionListener(this);
		guessField.setBounds(205, 618, 399, 25);
		guess.add(guessField);

		// every time a key is typed, search for players
		guessField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				repaint();

				searchGrid(B, r, c);

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
		updatePanel(gamePanel);
	}

	// get text from serach bar and update buttons
	private void searchGrid(JButton B, String r, String c) {
		String searchInputG = guessField.getText();

		// remove all components
		for (int i = 0; i < dropDownMenuG.size(); i++) {
			guess.remove(dropDownMenuG.get(i));
		}

		updateDDMGrid(B, searchInputG, r, c);

	}

	// update the player buttons
	public void updateDDMGrid(JButton B, String searchInputG, String r, String c) {

		// update dropdm
		dropDownMenuG = returnDDMGrid(B, searchInputG, r, c);

		for (int i = 0; i < dropDownMenuG.size(); i++) {
			guess.add(dropDownMenuG.get(i));
		}

		updatePanel(gamePanel);

	}

	public ArrayList<JButton> returnDDMGrid(final JButton B, String s, final String r, final String c) {
		// Only 5 players buttons for the grid game because of frame size restrictions
		final ArrayList<JButton> dropDownMenuG = new ArrayList<>(5);
		int count = 0;

		// loop through all players to check if String s is a substring
		for (int i = 0; i < playerGridList.size(); i++) {

			// if its a substring, we are gonna make a new JButton
			if (playerGridList.get(i).getNameG().toLowerCase().contains(s.toLowerCase())) {

				// create new temp button
				final JButton tempButtonG = new JButton(playerGridList.get(i).getNameG());
				tempButtonG.setFont(smallFont);
				tempButtonG.setBackground(pink);
				tempButtonG.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent b) {
						// reactivate all buttons and search bar when a player is clicked
						changeButtonStatus(r1c1, r1c2, r1c3, r2c1, r2c2, r2c3, r3c1, r3c2, r3c3, true);
						guessField.setVisible(false);

						// increase guesses
						gridGuesses++;
						// locate the chosen player
						searchedPlayerG = tempButtonG.getText();
						for (int i = 0; i < playerGridList.size(); i++) {
							if (searchedPlayerG.equalsIgnoreCase(playerGridList.get(i).getNameG())) {
								searchedG = playerGridList.get(i);
							}
						}

						// check if the chosen player matches the categories
						// if so, add the name to the button and make it green
						if (checkCorrect(searchedG, r, c)) {
							correctGuesses++;
							B.setText(searchedG.getNameG());
							B.setFont(smallFont);
							B.setBackground(darkGreen);
							B.setEnabled(false);
						}

						// when the grid is filled, end the game
						if (correctGuesses == FULLGRID) {
							gridOver(gridGuesses);
						}

						// remove the buttons
						for (int i = 0; i < dropDownMenuG.size(); i++) {
							guess.remove(dropDownMenuG.get(i));
						}

						guessField.setText("");
						updatePanel(gamePanel);
					}
				});
				// add JButton to List
				dropDownMenuG.add(tempButtonG);

				count++;

				if (count >= 5) {

					// break loop
					i = playerGridList.size();

				}

			}

		}

		// bounds of the buttons
		int x = 205;
		int y = 643;

		for (int i = 0; i < count; i++) {
			// set bounds for each button
			dropDownMenuG.get(i).setBounds(x, y, 399, 25);

			// put buttons under the search bar
			y += 25;
		}

		return dropDownMenuG;

	}

	// check if the chosen players fit the categories
	private boolean checkCorrect(PlayerGrid searched, String row, String col) {
		boolean result = false;

		// If the category is named "15" the player must have a season of over 15 points
		// per game
		if (row.equals("15")) {
			for (int e = 0; e < searched.getPPG().size(); e++) {
				if (searched.getPPG().get(e) >= 15.0) {
					result = true;
				}
			}
			// player must have a 7+ rebounds per game season
		} else if (row.equals("7")) {
			for (int g = 0; g < searched.getRPG().size(); g++) {
				if (searched.getRPG().get(g) >= 7.0) {
					result = true;
				}
			}
			// player must have a 5+ assists per game season
		} else if (row.equals("5")) {
			for (int h = 0; h < searched.getAPG().size(); h++) {
				if (searched.getAPG().get(h) >= 5.0) {
					result = true;
				}
			}
			// player must have played in the 2000-01 season
		} else if (row.equals("2000")) {
			if (searched.getSeasons().contains("2000-01") && searched.getTeams().contains(col)) {
				result = true;
			}
		}

		// played in 2005-06 season
		else if (row.equals("2005")) {
			if (searched.getSeasons().contains("2005-06") && searched.getTeams().contains(col)) {
				result = true;
			}
		}

		// played in 2010-11 season
		else if (row.equals("2010")) {
			if (searched.getSeasons().contains("2010-11") && searched.getTeams().contains(col)) {
				result = true;
			}
		}

		// played in 2015-16 season
		else if (row.equals("2015")) {
			if (searched.getSeasons().contains("2015-16") && searched.getTeams().contains(col)) {
				result = true;
			}
		}

		// played in 2020-21 season
		else if (row.equals("2020")) {
			if (searched.getSeasons().contains("2020-21") && searched.getTeams().contains(col)) {
				result = true;
			}
		}

		// played in 2023-24 season
		else if (row.equals("2023")) {
			if (searched.getSeasons().contains("2023-24") && searched.getTeams().contains(col)) {
				result = true;
			}

			// if the player has played for both teams (row and column categories)
		} else if (searched.getTeams().contains(row) && searched.getTeams().contains(col)) {
			result = true;

		}

		return result;
	}

	// used to activate or deactivate all of the unguessed buttons in the grid
	private void changeButtonStatus(JButton r1c1, JButton r1c2, JButton r1c3, JButton r2c1, JButton r2c2, JButton r2c3,
			JButton r3c1, JButton r3c2, JButton r3c3, boolean trueFalse) {
		if (r1c1.getBackground() != darkGreen) {
			r1c1.setEnabled(trueFalse);
		}
		if (r1c2.getBackground() != darkGreen) {
			r1c2.setEnabled(trueFalse);
		}
		if (r1c3.getBackground() != darkGreen) {
			r1c3.setEnabled(trueFalse);
		}
		if (r2c1.getBackground() != darkGreen) {
			r2c1.setEnabled(trueFalse);
		}
		if (r2c2.getBackground() != darkGreen) {
			r2c2.setEnabled(trueFalse);
		}
		if (r2c3.getBackground() != darkGreen) {
			r2c3.setEnabled(trueFalse);
		}
		if (r3c1.getBackground() != darkGreen) {
			r3c1.setEnabled(trueFalse);
		}
		if (r3c2.getBackground() != darkGreen) {
			r3c2.setEnabled(trueFalse);
		}
		if (r3c3.getBackground() != darkGreen) {
			r3c3.setEnabled(trueFalse);
		}
	}

	// start the hoop grid game
	private void hoopGridsStart() {

		playGrid = true;
		ArrayList<String> categories = new ArrayList<String>();

		// choose the categories for the grid
		categories = chooseCategories();

		// add button to see rules
		seeRules = new JButton("Rules");
		seeRules.setFont(smallFont);
		seeRules.setForeground(Color.BLACK);
		seeRules.setBackground(pink);
		seeRules.setBounds(700, 0, 100, 20);
		seeRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent seeRules) {
				rules();
			}
		});
		guess.add(seeRules);

		// select the categories
		// all columns and row 1 will be a team
		c1 = categories.get(0);
		c2 = categories.get(1);
		c3 = categories.get(2);
		r1 = categories.get(3);
		// row 2 will be a stat threshold
		r2 = categories.get(4);
		// row 3 will be a season played category
		r3 = categories.get(5);

		// create labels for the categories
		categoryLabel(c1, 183, 27, LABELSIZE, LABELSIZE, guess);
		categoryLabel(c2, 382, 27, LABELSIZE, LABELSIZE, guess);
		categoryLabel(c3, 578, 27, LABELSIZE, LABELSIZE, guess);
		categoryLabel(r1, 35, 150, LABELSIZE, LABELSIZE, guess);
		categoryLabel(r2, 5, 335, 95, 30, guess);
		categoryLabel(r3, 5, 510, 95, 30, guess);

		// create each button
		// when the user goes to make a guess/presses button, all other buttons are
		// deactivated
		// calls on the grid search method with appropriate categories
		r1c1 = new JButton();
		r1c1.setBackground(G.grey);
		r1c1.setBounds(118, 96, 192, 175);
		r1c1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent guess) {
				changeButtonStatus(r1c1, r1c2, r1c3, r2c1, r2c2, r2c3, r3c1, r3c2, r3c3, false);
				gridSearch(r1c1, r1, c1);
			}
		});
		guess.add(r1c1);

		r1c2 = new JButton();
		r1c2.setBackground(G.grey);
		r1c2.setBounds(314, 96, 193, 175);
		r1c2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent guess) {
				changeButtonStatus(r1c1, r1c2, r1c3, r2c1, r2c2, r2c3, r3c1, r3c2, r3c3, false);
				gridSearch(r1c2, r1, c2);
			}
		});
		guess.add(r1c2);

		r1c3 = new JButton();
		r1c3.setBackground(G.grey);
		r1c3.setBounds(511, 96, 183, 175);
		r1c3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent guess) {
				changeButtonStatus(r1c1, r1c2, r1c3, r2c1, r2c2, r2c3, r3c1, r3c2, r3c3, false);
				gridSearch(r1c3, r1, c3);
			}
		});
		guess.add(r1c3);

		r2c1 = new JButton();
		r2c1.setBackground(G.grey);
		r2c1.setBounds(118, 275, 192, 171);
		r2c1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent guess) {
				changeButtonStatus(r1c1, r1c2, r1c3, r2c1, r2c2, r2c3, r3c1, r3c2, r3c3, false);
				gridSearch(r2c1, r2, c1);
			}
		});
		guess.add(r2c1);

		r2c2 = new JButton();
		r2c2.setBackground(G.grey);
		r2c2.setBounds(314, 275, 193, 171);
		r2c2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent guess) {
				changeButtonStatus(r1c1, r1c2, r1c3, r2c1, r2c2, r2c3, r3c1, r3c2, r3c3, false);
				gridSearch(r2c2, r2, c2);
			}
		});
		guess.add(r2c2);

		r2c3 = new JButton();
		r2c3.setBackground(G.grey);
		r2c3.setBounds(511, 275, 183, 171);
		r2c3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent guess) {
				changeButtonStatus(r1c1, r1c2, r1c3, r2c1, r2c2, r2c3, r3c1, r3c2, r3c3, false);
				gridSearch(r2c3, r2, c3);
			}
		});
		guess.add(r2c3);

		r3c1 = new JButton();
		r3c1.setBackground(G.grey);
		r3c1.setBounds(118, 450, 192, 163);
		r3c1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent guess) {
				changeButtonStatus(r1c1, r1c2, r1c3, r2c1, r2c2, r2c3, r3c1, r3c2, r3c3, false);
				gridSearch(r3c1, r3, c1);
			}
		});
		guess.add(r3c1);

		r3c2 = new JButton();
		r3c2.setBackground(G.grey);
		r3c2.setBounds(314, 450, 193, 163);
		r3c2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent guess) {
				changeButtonStatus(r1c1, r1c2, r1c3, r2c1, r2c2, r2c3, r3c1, r3c2, r3c3, false);
				gridSearch(r3c2, r3, c2);
			}
		});
		guess.add(r3c2);

		r3c3 = new JButton();
		r3c3.setBackground(G.grey);
		r3c3.setBounds(511, 450, 183, 163);
		r3c3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent guess) {
				changeButtonStatus(r1c1, r1c2, r1c3, r2c1, r2c2, r2c3, r3c1, r3c2, r3c3, false);
				gridSearch(r3c3, r3, c3);
			}
		});
		guess.add(r3c3);

		updatePanel(gamePanel);

	}

	// end of the grid game
	private void gridOver(int guesses) {
		clearPanel(gamePanel);
		// A score is calculated with a maximum of 1000
		// each incorrect guess will result in 50 lost points until score is 0
		int score = 1000 - (50 * (guesses - FULLGRID));
		if (score < 0) {
			score = 0;
		}

		// background image
		JLabel gridEnd = new JLabel(endGrid);
		gridEnd.setBounds(0, 0, FRAMESIZE, FRAMESIZE);
		gamePanel.add(gridEnd);

		// shows the score to user
		JLabel showScore = new JLabel(Integer.toString(score));
		showScore.setBounds(470, 385, 100, 100);
		showScore.setFont(plainFont);
		gridEnd.add(showScore);

		// button to return to home screen and play the game again
		playAgain = new JButton("PLAY AGAIN");
		playAgain.setBounds(200, 600, 400, 50);
		playAgain.setFont(smallFont);
		playAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent playAgain) {
				try {

					resetGame();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		gridEnd.add(playAgain);

		updatePanel(gamePanel);

	}

}