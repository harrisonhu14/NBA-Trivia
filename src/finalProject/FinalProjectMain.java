/* 

 * Project: main

 * Package: finalProject

 * Class: FinalProjectMain

 * Programmers:  Harrison Hu, Zain Babar

 * Date Created:  5/20/24

 * Description:  Creates the 816x800, non-resizable frame and adds the GUI. 
 */

package finalProject;

import javax.swing.JFrame;
import javax.swing.*;

public class FinalProjectMain {

	public static void main(String[] args) throws InterruptedException {

		FinalProjectGUI gui = new FinalProjectGUI();

		// create the frame
		JFrame window = new JFrame("Final Project");

		window.setSize(816, 800);

		window.setResizable(false);

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// add the GUI to the frame
		window.add(gui);

		window.setVisible(true);
	}

}
