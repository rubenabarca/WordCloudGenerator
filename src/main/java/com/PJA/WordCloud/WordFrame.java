package com.PJA.WordCloud;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class WordFrame extends JFrame {


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WordFrame frame = new WordFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WordFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(100, 100);
		setResizable(false);
		setContentPane(new WordPanel());
		setTitle("Word Cloud Generator");
		
		pack();
	}

}
