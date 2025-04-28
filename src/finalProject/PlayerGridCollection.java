/* 

 * Project: main

 * Package: finalProject

 * Class: PlayerGridCollection

 * Programmers:  Harrison Hu, Zain Babar

 * Date Created:  5/20/24

 * Description:  This class reads the nbaGridGame csv file and stores all the category and player
 *  information in ArrayLists. This class also sorts duplicate players,
 *  adding their stats from different seasons to the player. 
 *  Uses the PlayerGrid constructor to create an ArrayList of players for the grid game.
 *  Also has methods to return categories for the rows and columns.
 */

package finalProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PlayerGridCollection {

	// Gets the stat categories from the second line of the csv file
	public ArrayList<String> readStatCategories() {

		ArrayList<String> statCategories = new ArrayList<>();

		try {
			FileReader fr = new FileReader("nbaGridGame.csv");
			BufferedReader br = new BufferedReader(fr);

			// skip 1st line
			br.readLine();

			// split the line into 3 Strings and add them to a list of stat categories
			String line = br.readLine();
			if (line != null) {
				String[] statCategory = line.split(",");
				for (String stat : statCategory) {
					statCategories.add(stat);
				}
			}

			br.close();
			fr.close();

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}

		return statCategories;
	}

	// Gets a list of years played for row 3 category
	public ArrayList<String> readYearCategories() {
		ArrayList<String> yearCategories = new ArrayList<>();

		try {
			FileReader fr = new FileReader("nbaGridGame.csv");
			BufferedReader br = new BufferedReader(fr);

			// skip first two lines
			br.readLine();
			br.readLine();

			// Add each year to an ArrayList containing year categories
			String line = br.readLine();
			if (line != null) {
				String[] yearCategory = line.split(",");
				for (String year : yearCategory) {
					yearCategories.add(year);
				}
			}

			br.close();
			fr.close();

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}

		return yearCategories;
	}

	// Gets all NBA teams in a list, to be used as categories
	public ArrayList<String> readTeamCategories() {
		ArrayList<String> teamCategories = new ArrayList<>();

		try {
			FileReader fr = new FileReader("nbaGridGame.csv");
			BufferedReader br = new BufferedReader(fr);

			// Split the 30 teams and add to an ArrayList
			String line = br.readLine();
			if (line != null) {
				String[] teamCategory = line.split(",");
				for (String team : teamCategory) {
					teamCategories.add(team);
				}
			}

			br.close();
			fr.close();

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}

		return teamCategories;
	}

	// Getting the information of each player
	// File contains the stats of every player to play in seasons 1996-2023
	public ArrayList<PlayerGrid> readGridFile() {
		ArrayList<PlayerGrid> playerGridList = new ArrayList<>();

		try {
			FileReader fr = new FileReader("nbaGridGame.csv");
			BufferedReader br = new BufferedReader(fr);

			// Skip the fist four lines containing category information and labels
			br.readLine();
			br.readLine();
			br.readLine();
			br.readLine();

			// Each row has the information of a player in a given season, so split the line
			// into individual elements
			String line;
			while ((line = br.readLine()) != null) {
				String[] playerGridInfo = line.split(",");

				// There will be duplicates of the same player, so only team, stats and season
				// will be taken from that line
				boolean duplicate = false;
				int duplicateIndex = -1;
				for (int j = 0; j < playerGridList.size(); j++) {
					if (playerGridList.get(j).getNameG().equals(playerGridInfo[0])) {
						duplicate = true;
						duplicateIndex = j;
						break;
					}
				}

				// Add information to the existing player's arraylists
				if (duplicate) {
					playerGridList.get(duplicateIndex).addTeam(playerGridInfo[1]);
					playerGridList.get(duplicateIndex).addPPG(Float.valueOf(playerGridInfo[2]));
					playerGridList.get(duplicateIndex).addRPG(Float.valueOf(playerGridInfo[3]));
					playerGridList.get(duplicateIndex).addAPG(Float.valueOf(playerGridInfo[4]));
					playerGridList.get(duplicateIndex).addSeason(playerGridInfo[5]);
				} else {
					// If the player does not already exist, create a new list for each category
					// except name
					// Add the information from that season
					String name = playerGridInfo[0];

					ArrayList<String> teams = new ArrayList<>();
					teams.add(playerGridInfo[1]);

					ArrayList<Float> ppg = new ArrayList<>();
					ppg.add(Float.valueOf(playerGridInfo[2]));

					ArrayList<Float> rpg = new ArrayList<>();
					rpg.add(Float.valueOf(playerGridInfo[3]));

					ArrayList<Float> apg = new ArrayList<>();
					apg.add(Float.valueOf(playerGridInfo[4]));

					ArrayList<String> seasons = new ArrayList<>();
					seasons.add(playerGridInfo[5]);

					// Use constructor to create that player and add it to the list of players
					PlayerGrid player = new PlayerGrid(name, teams, ppg, rpg, apg, seasons);
					playerGridList.add(player);
				}
			}

			br.close();
			fr.close();

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}

		return playerGridList;
	}
}