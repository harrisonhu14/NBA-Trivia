/* 

 * Project: main

 * Package: finalProject

 * Class: guessOperations

 * Programmers:  Harrison Hu, Zain Babar

 * Date Created:  5/20/24

 * Description:  Contains methods for the guessing game that compare the mystery player and searched players’
 * 			data and returns a colour to be displayed to the user. 
 * 			Grey is incorrect, yellow is close when easy mode is selected, and green is correct.
 */

package finalProject;

import java.awt.Color;

public class GuessOperations {

	Color grey = new Color(220, 220, 220);
	Color yellow = new Color(255, 255, 200);
	Color green = new Color(80, 200, 120);

	// grey (WRONG)
	// yellow (CLOSE) only for easyMode
	// green (CORRECT)

	// Compare guessed player age and mystery player age
	public Color checkAge(PlayerGuess guessedPlayer, PlayerGuess answerPlayer, boolean easyMode) {

		Color result = grey;

		// If answer is close
		if ((Math.abs(answerPlayer.getAge() - guessedPlayer.getAge()) <= 2)
				&& answerPlayer.getAge() != guessedPlayer.getAge() && easyMode) {

			result = yellow;

		}

		// If answer is correct
		if (answerPlayer.getAge() == guessedPlayer.getAge()) {

			result = green;

		}

		// Return result
		return result;

	}

	// grey (NOT CLOSE)
	// yellow (GuessedPlayer height is within 2 of answerPlayer height)
	// green (CORRECT)
	// Compare heights
	public Color checkHeight(PlayerGuess guessedPlayer, PlayerGuess answerPlayer, boolean easyMode) {

		Color result = grey;

		// If answer is close
		if ((Math.abs(answerPlayer.getHeightInches() - guessedPlayer.getHeightInches()) <= 2)
				&& (answerPlayer.getHeightInches() != guessedPlayer.getHeightInches()) && easyMode) {

			result = yellow;

		}

		// If answer is correct
		else if (answerPlayer.getHeightInches() == guessedPlayer.getHeightInches()) {

			result = green;

		}

		// Return result
		return result;

	}

	// grey (INCORRECT, NOT CLOSE)
	// yellow (GuessedPlayer number is within 2 of answerPlayer jersey number)
	// green (CORRECT)
	// Compare jersey numbers
	public Color checkJerseyNumber(PlayerGuess guessedPlayer, PlayerGuess answerPlayer, boolean easyMode) {

		Color result = grey;

		// If guessed Jersey Num is within 2 of Jersey Num
		if ((Math.abs(answerPlayer.getJerseyNum() - guessedPlayer.getJerseyNum()) <= 2)
				&& (answerPlayer.getJerseyNum() != guessedPlayer.getJerseyNum()) && easyMode) {

			result = yellow;

		}

		// If answer is correct
		else if (answerPlayer.getJerseyNum() == guessedPlayer.getJerseyNum()) {

			result = green;

		}

		// Return result
		return result;

	}

	// grey (NOT SAME CONFERENCE)
	// green (CORRECT)

	public Color checkConference(PlayerGuess guessedPlayer, PlayerGuess answerPlayer) {

		Color result = grey;

		// If conferences are the same
		if (guessedPlayer.getConference().equals(answerPlayer.getConference())) {

			result = green;

		}

		// Return result
		return result;

	}

	// grey (NOT SAME DIVISION)
	// green (CORRECT DIVISION)

	public Color checkDivision(PlayerGuess guessedPlayer, PlayerGuess answerPlayer) {

		Color result = grey;

		// If guessed division is the same as answer division
		if (guessedPlayer.getDivision().equals(answerPlayer.getDivision())) {

			result = green;

		}

		// Return result
		return result;

	}

	// grey (NOT SAME TEAM)
	// green (SAME TEAM)

	public Color checkTeam(PlayerGuess guessedPlayer, PlayerGuess answerPlayer) {

		Color result = grey;

		// If teams are the same
		if (guessedPlayer.getTeam().equals(answerPlayer.getTeam())) {

			result = green;

		}

		// Return result
		return result;

	}

	// grey (NOT SAME PLAYER)
	// green (SAME PLAYER)

	public Color checkName(PlayerGuess guessedPlayer, PlayerGuess answerPlayer) {

		Color result = grey;

		// If teams are the same
		if (guessedPlayer.getName().equals(answerPlayer.getName())) {

			result = green;

		}

		// Return result
		return result;

	}

	// SPECIAL CASES: SG/PG and SF/PF

	// incorrect (not close)
	// yellow (close)
	// green (correct)
	public Color checkPosition(PlayerGuess guessedPlayer, PlayerGuess answerPlayer, boolean easyMode) {

		Color result = grey;

		// only show yellows for easy mode
		if (easyMode) {
			// Guard can have 2 positions: SG and PG

			// If guessed player is SG and answer player is PG or vice versa, set as yellow
			// (close)
			if ((guessedPlayer.getPosition().equals("SG") && answerPlayer.getPosition().equals("PG"))
					|| (guessedPlayer.getPosition().equals("PG") && answerPlayer.getPosition().equals("SG"))) {

				// If the "Guard Position" Guess is close
				result = yellow;

			}

			// only show yellows for easy mode

			// Forward can have 2 positions: PF and SF

			// If guessed player is SF and answer player is PF or vice versa, set as yellow
			// (close)
			else if (((guessedPlayer.getPosition().equals("SF") && answerPlayer.getPosition().equals("PF"))
					|| (guessedPlayer.getPosition().equals("PF") && answerPlayer.getPosition().equals("SF")))) {

				// If the "Forward Position" Guess is close
				result = yellow;

			}
		}

		// If the positions are equal, set as correct
		if (guessedPlayer.getPosition().equals(answerPlayer.getPosition())) {

			result = green;

		}

		// Return result
		return result;

	}

}
