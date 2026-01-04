/* 

 * Project: main

 * Package: finalProject

 * Class: PlayerGuessCollection

 * Programmers:  Harrison Hu, Zain Babar

 * Date Created:  5/20/24

 * Description:  This class reads the NBAdata csv file and stores all the information in ArrayLists.
 *  Uses the PlayerGuess constructor to create an ArrayList of players for the guessing game. 
 */

package finalProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PlayerGuessCollection {

//Get the information for each player from the csv file	
	public ArrayList<PlayerGuess> readNBAFile() {
		
		final int SIZE = 273;

		ArrayList<PlayerGuess> playerList = new ArrayList<PlayerGuess>();

		try {

			// Load file
			FileReader fr = new FileReader("nbadata.csv");
			BufferedReader br = new BufferedReader(fr);

			String line;

			// Skip the line containing the titles
			br.readLine();

			// Read the players
			for (int i = 0; i < SIZE; i++) {

				line = br.readLine();

				// Split the line into its information
				String[] playerInfo = line.split(",");

				// Get player data
				String name = playerInfo[0];
				String team = playerInfo[1];
				String conference = playerInfo[2];
				String division = playerInfo[3];
				String position = playerInfo[4];
				String stringHeight = playerInfo[5];
				String stringAge = playerInfo[6];
				String stringJerseyNumber = playerInfo[7];

				// Convert height and string to integer values
				int height = Integer.valueOf(stringHeight);
				int age = Integer.valueOf(stringAge);
				int jerseyNumber = Integer.valueOf(stringJerseyNumber);

				// Create the player using constructor from PlayerGuess
				PlayerGuess playerGuess = new PlayerGuess(name, team, conference, division, position, height, age,
						jerseyNumber);

				// add new player to the list
				playerList.add(playerGuess);

			}

			// Close the file reader
			fr.close();
			br.close();

		} catch (IOException e) {
			System.out.println("Error:" + e.getMessage());
		}

		return playerList;

	}

}
