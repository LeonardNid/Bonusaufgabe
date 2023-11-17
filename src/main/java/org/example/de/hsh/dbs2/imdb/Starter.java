package org.example.de.hsh.dbs2.imdb;

import javax.swing.SwingUtilities;

import org.example.de.hsh.dbs2.imdb.gui.SearchMovieDialog;
import org.example.de.hsh.dbs2.imdb.gui.SearchMovieDialogCallback;

public class Starter {

	/**
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Starter().run();
			}
		});
	
	}
	
	public void run() {
		SearchMovieDialogCallback callback = new SearchMovieDialogCallback();
		SearchMovieDialog sd = new SearchMovieDialog(callback);
		sd.setVisible(true);
	}

}
