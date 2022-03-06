package com.PJA.WordCloud;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class WordPanel extends JPanel implements MouseListener{
	private int WIDTH = 600;
	private int HEIGHT = 900;
	private Dimension SIZE = new Dimension(WIDTH, HEIGHT);
	
	private JLabel inputPrompt;
	private JTextArea inputField;
	private JScrollPane inputScroll;
	private JLabel enter, output, info;
	private Border border;
	
	final private String commonWords = "THE BE TO OF AND A IN THAT HAVE I IT FOR NOT";
	
	public WordPanel() {
		border = BorderFactory.createLineBorder(Color.BLACK, 1);
		setPreferredSize(SIZE);
		setDisplay();
		setLayout(null);
		setBackground(new Color(191, 234, 236));
	}

	private void setDisplay() {
		inputPrompt = new JLabel("Enter text here: ");
		
		inputPrompt.setBounds(WIDTH/2 - (int)inputPrompt.getPreferredSize().getWidth()/2,
				50, 
				(int)inputPrompt.getPreferredSize().getWidth(), 
				(int)inputPrompt.getPreferredSize().getHeight());
		add(inputPrompt);
		
		inputField = new JTextArea();
		inputScroll = new JScrollPane(inputField);
		inputScroll.setBounds(50, 75, 500, 400);
	
		add(inputScroll);
		
		enter = new JLabel("Enter");
		enter.setFont(new Font("Tacoma", Font.BOLD, 24));
		enter.setBounds(WIDTH/2 - (int)enter.getPreferredSize().getWidth()/2-5,
				550, 
				(int)enter.getPreferredSize().getWidth()+5, 
				(int)enter.getPreferredSize().getHeight());
		enter.addMouseListener(this);
		add(enter);
		
		output = new JLabel();
		output.setBounds(100, 650, 400, 200);
		add(output);
		
		info = new JLabel();
		info.setIcon(new ImageIcon(getScaledImage((new ImageIcon("src/main/java/com/PJA/images/infoIcon.png").getImage()), 40, 40)));
		info.setBounds(20, 20, 40, 40);
		info.addMouseListener(this);
		add(info);
	}
	
	private void generateWordCloud(String input) {
		
		HttpResponse<String> response = Unirest.post("https://wordcloudservice.p.rapidapi.com/generate_wc")
				.header("content-type", "application/json")
				.header("x-rapidapi-host", "wordcloudservice.p.rapidapi.com")
				.header("x-rapidapi-key", "cba7c322d3mshdfb8cbc663f08efp12b215jsn6316052e096b")
				.body("{\r\n"
						+ "    \"f_type\": \"png\",\r\n"
						+ "    \"width\": 400,\r\n"
						+ "    \"height\": 200,\r\n"
						+ "    \"s_max\": \"7\",\r\n"
						+ "    \"s_min\": \"1\",\r\n"
						+ "    \"f_min\": 1,\r\n"
						+ "    \"r_color\": \"TRUE\",\r\n"
						+ "    \"r_order\": \"TRUE\",\r\n"
						+ "    \"s_fit\": \"FALSE\",\r\n"
						+ "    \"fixed_asp\": \"FALSE\",\r\n"
						+ "    \"rotate\": \"TRUE\",\r\n"
						+ "    \"textblock\": \"" + input + "\"\r\n}")
				.asString();
		
		output.setBounds(100, 650, 400, 200);
		output.setIcon(new ImageIcon(urlToImage(response.getBody().substring(12, response.getBody().length() - 3))));
		
	}
	
	private String parseInput(String input) {
		String result = "";
		String[] arr = input.toUpperCase().split("[^'â€™A-Z]");
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] != "" && !commonWords.contains(arr[i])) {
				result += arr[i] + " ";
			}
		}
		return result;
	}
	
	private Image urlToImage(String urlString) {
        URL url = null;

        try {
            url = new URL(urlString);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }

        try {
            Image image = ImageIO.read(url);
            return(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; 
    }
	
	public Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		Object src = e.getSource();
		
		if (src == enter) {

			enter.setBorder(null);
			generateWordCloud(parseInput(inputField.getText()));
		}
		if (src == info) {
			JOptionPane.showMessageDialog(null, "Enter large texts like lyrics, poems, etc, to create a word cloud");
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		Object src = e.getSource();
		
		if (src == enter) {
			enter.setBorder(border);
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		Object src = e.getSource();
		
		if (src == enter) {
			enter.setBorder(border);
		}
		
	}

	@Override
	public void mouseExited(MouseEvent e) {

		Object src = e.getSource();
		
		if (src == enter) {
			enter.setBorder(null);
		}
		
	}

}	
	
