/* 

 * Project: main

 * Package: finalProject

 * Class: PlayerGuess

 * Programmers:  Harrison Hu, Zain Babar

 * Date Created:  5/20/24

 * Description:  This class contains the constructor for players in the guessing game.
 *  Also, all “get” methods and picture initializations are created here.
 */

package finalProject;

import java.awt.Image;

import javax.swing.ImageIcon;

public class PlayerGuess {

	// variables
	private String name;
	private String team;
	private String conference;
	private String division;
	private String position;
	private int height;
	private int age;
	private int jerseyNum;
	private ImageIcon playerImage;
	private ImageIcon teamImage;

	// Constructor for players in the guessing game
	// This also creates the images for the player and his team
	public PlayerGuess(String Name, String Team, String Conference, String Division, String Position, int Height,
			int Age, int JerseyNum) {
		this.name = Name;
		this.team = Team;
		this.conference = Conference;
		this.division = Division;
		this.position = Position;
		this.height = Height;
		this.age = Age;
		this.jerseyNum = JerseyNum;
		this.playerImage = new ImageIcon("playerPictures/" + this.name + ".jpg");
		this.teamImage = new ImageIcon("teamPictures/" + this.team + ".jpg");

	}

	// Get methods for each component of a player
	public String getName() {
		return this.name;
	}

	public String getTeam() {
		return this.team;
	}

	public String getConference() {
		return this.conference;
	}

	public String getDivision() {
		return this.division;
	}

	public String getPosition() {
		return this.position;
	}

	public int getHeightInches() {
		return this.height;
	}

	// Height is converted from inches into a String with feet and inches
	public String getHeight() {
		int feet = this.height / 12;
		int in = this.height % 12;

		String heightFI = (String.valueOf(feet) + "'" + String.valueOf(in));
		return heightFI;
	}

	public int getAge() {
		return this.age;
	}

	public int getJerseyNum() {
		return this.jerseyNum;
	}

	public ImageIcon getPlayerImage() {
		return this.playerImage;
	}

	// Resize the image for the guessing panel
	public ImageIcon getPlayerIcon() {
		Image image = this.playerImage.getImage(); // transform it
		Image newimg = image.getScaledInstance(60, 90, java.awt.Image.SCALE_SMOOTH);
		ImageIcon newImageIcon = new ImageIcon(newimg); // assign to a new ImageIcon instance
		return newImageIcon;
	}

	public ImageIcon getTeamLogo() {
		return this.teamImage;
	}

}