/* 

 * Project: main

 * Package: finalProject

 * Class: PlayerGrid

 * Programmers:  Harrison Hu, Zain Babar

 * Date Created:  5/20/24

 * Description:  This class contains the constructor for players in the grid game.
 *  Also, all “get” methods and methods to add information to players are here.
 */

package finalProject;

import java.util.ArrayList;

public class PlayerGrid {

	// variables
	private String name;
	private ArrayList<String> teams;
	private ArrayList<Float> ppg;
	private ArrayList<Float> rpg;
	private ArrayList<Float> apg;
	private ArrayList<String> seasons;

	// Constructor for players that can be guessed in the grid game
	public PlayerGrid(String Name, ArrayList<String> Teams, ArrayList<Float> ppg, ArrayList<Float> rpg,
			ArrayList<Float> apg, ArrayList<String> seasons) {
		this.name = Name;
		this.teams = Teams;
		this.ppg = ppg;
		this.rpg = rpg;
		this.apg = apg;
		this.seasons = seasons;

	}

	// Add a team to the player
	public void addTeam(String team) {
		this.teams.add(team);
	}

	// Add a points per game average from a season
	public void addPPG(float ppg) {
		this.ppg.add(ppg);
	}

	// Add a rebounds per game average from a season
	public void addRPG(float rpg) {
		this.rpg.add(rpg);
	}

	// Add an assists per game average from a season
	public void addAPG(float apg) {
		this.apg.add(apg);
	}

	// Add a season played
	public void addSeason(String season) {
		this.seasons.add(season);
	}

	// Get methods for variables
	public String getNameG() {
		return this.name;
	}

	public ArrayList<String> getTeams() {
		return this.teams;
	}

	public ArrayList<Float> getPPG() {
		return this.ppg;
	}

	public ArrayList<Float> getRPG() {
		return this.rpg;
	}

	public ArrayList<Float> getAPG() {
		return this.apg;
	}

	public ArrayList<String> getSeasons() {
		return this.seasons;
	}

}