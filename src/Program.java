import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

public class Program extends JFrame {
	
	private Igra ig;
	private Komp ai;
	private boolean dva, napotezu;
	
	private JButton [][]dugme;
	private JPanel polje;
	private JLabel tekst;
	private JMenuBar menuBar;
	private JMenu menu, submenu;
	private JMenuItem menuItem;
	
	public Program() {
		super("Iks-Oks");
		setResizable(false);
		setBounds(100, 100, 210, 280);
		setLayout(new FlowLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//MENI
		menuBar = new JMenuBar();

		menu = new JMenu("Nova igra");
		menu.getAccessibleContext().setAccessibleDescription("Zapocni novu igru.");
		menuBar.add(menu);

		menuItem = new JMenuItem("Dva igraca");
		menuItem.getAccessibleContext().setAccessibleDescription("Igrajte protiv jos jednog igraca.");
		menuItem.addActionListener(new MeniNova('d'));
		menu.add(menuItem);
		
		submenu = new JMenu("Protiv kompjutera");
		menu.add(submenu);
		
		menuItem = new JMenuItem("Budi X");
		menuItem.getAccessibleContext().setAccessibleDescription("Igraj kao X protiv kompjutera.");
		menuItem.addActionListener(new MeniNova('x'));
		submenu.add(menuItem);
		
		menuItem = new JMenuItem("Budi O");
		menuItem.getAccessibleContext().setAccessibleDescription("Igraj kao O protiv kompjutera.");
		menuItem.addActionListener(new MeniNova('o'));
		submenu.add(menuItem);

		menu.addSeparator();
		
		menuItem = new JMenuItem("Simuliraj");
		menuItem.getAccessibleContext().setAccessibleDescription("Igraj kao X protiv kompjutera.");
		menuItem.addActionListener(new MeniNova('k'));
		menu.add(menuItem);
		
		menu = new JMenu("Pomoc");
		menu.getAccessibleContext().setAccessibleDescription("Pomoc i podaci o igri.");
		menuBar.add(menu);

		menuItem = new JMenuItem("Pravila");
		menuItem.getAccessibleContext().setAccessibleDescription("Kako se igra Iks-Oks.");
		menuItem.addActionListener(new MeniPomoc('p'));
		menu.add(menuItem);

		menu.addSeparator();
		
		menuItem = new JMenuItem("O programu");
		menuItem.getAccessibleContext().setAccessibleDescription("");
		menuItem.addActionListener(new MeniPomoc('o'));
		menu.add(menuItem);

		setJMenuBar(menuBar);
		
		//POLJE
		polje = new JPanel();
		polje.setLayout(new GridLayout(3, 3));
		Dimension d = new Dimension(200, 200);
		polje.setBounds(100, 100, 200, 220);
		polje.setMinimumSize(d);
		polje.setMaximumSize(d);
		polje.setPreferredSize(d);
		c.anchor = GridBagConstraints.NORTH;
		add(polje, c);
		
		dugme = new JButton[3][3];
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++) {
				polje.add(dugme[i][j] = new JButton());
				dugme[i][j].addActionListener(new DugmeAkcija(i, j));
				dugme[i][j].setFont(new Font(null, Font.BOLD, 24));
				dugme[i][j].setText((i == j || i == j+2 || j == i+2 ? "X" : "O"));
				dugme[i][j].setEnabled(false);
		}
		
		//TEKST
		tekst = new JLabel("Dobrodosli u Iks-Oks!");
		tekst.setFont(new Font(null, Font.BOLD, 13));
		c.anchor = GridBagConstraints.PAGE_END;
		add(tekst, c);
		
		addWindowListener(new PrDogadjaji());
		setVisible(true);
		
		ai = new Komp(dugme);
	}
	
	private class DugmeAkcija implements ActionListener {
		
		private int i, j;
		
		public DugmeAkcija(int ii, int jj) {
			super();
			i = ii; j = jj;
		}
		public void actionPerformed(ActionEvent e) {
			if(napotezu || dva) {
				napotezu = false;
				ig.postavi(i, j);
				dugme[i][j].setText("" + Character.toUpperCase(ig.polje(i, j)));
				dugme[i][j].setEnabled(false);
				tekst.setText("Na potezu je " + Character.toUpperCase(ig.naRedu()) + '.');
				if(ig.kraj() != '\0') kraj();
				else if(!dva) {
					napotezu = ai.igraj(ig.naRedu(), false);
					tekst.setText("Na potezu je " + Character.toUpperCase(ig.naRedu()) + '.');
					if(ig.kraj() != '\0') kraj();
				}
			}
		}
	}
	
	private class MeniNova implements ActionListener {
		
		private char c;
		
		public MeniNova(char cc) {
			super();
			c = cc;
		}
		
		public void actionPerformed(ActionEvent d) {
			ig = new Igra();
			dva = c == 'd';
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 3; j++) {
					dugme[i][j].setText("");
					dugme[i][j].setEnabled(true);
			}
			tekst.setFont(new Font(null, Font.PLAIN, 13));
			tekst.setText("Na potezu je " + Character.toUpperCase(ig.naRedu()) + '.');
			pocetak();
		}
		
		private void pocetak() {
			napotezu = false;
			ai.nova(ig);
			switch(c)
			{
			case 'x': 	napotezu = true;
						break;
			case 'o': 	napotezu = ai.igraj();
						break;
			case 'k':	while(ig.kraj() == '\0') ai.igraj();
						kraj();
			}
		}
	}
	
	
	private class MeniPomoc implements ActionListener {
	
		private char c;
		
		public MeniPomoc(char cc) {
			super();
			c = cc;
		}
		
		public void actionPerformed(ActionEvent d) {
			switch(c)
			{
			case 'p': 	JOptionPane.showMessageDialog(
							null,
							"Cilj igre je da spojite tri ista znaka horizontalno, vertikalno, ili dijagonalno,\ni sprecite svog protivnika da uradi isto. X uvek igra prvi.\n\n" +
							"Da zapocnete igru, pritisnite meni 'Nova igra',\ni zatim odaberite jednu od ponudjenih opcija.",
							"Kako igrati?",
							JOptionPane.QUESTION_MESSAGE
						);
						break;
			case 'o': 	JOptionPane.showMessageDialog(
							null,
							"Iks-Oks\nby Velibor Bacujkov\n2013.",
							"O programu",
							JOptionPane.INFORMATION_MESSAGE
						);
			}
		}
	}

	private class PrDogadjaji extends WindowAdapter {
		public void windowClosing(WindowEvent d)
		{ dispose(); }
	}
	
	private static class Komp extends Thread {
		
		private Igra ig;
		private boolean igrao;
		
		private JButton [][]dugme;
		
		public Komp(JButton [][]d)
		{ dugme = d; }
		
		public void nova(Igra igr) {
			ig = igr;
			igrao = false;
		}
		
		public boolean igraj() {
			return igraj(ig.naRedu(), false);
		}
		
		private boolean igraj(char c, boolean g) {
			
			if(!igrao) {
				if(c == 'x' && !g) {
					Random rand = new Random();
					switch(rand.nextInt(5))
					{
					case 0: 	return postavi(0, 0);
					case 1: 	return postavi(0, 2);
					case 2:		return postavi(2, 0);
					case 3:		return postavi(2, 2);
					case 4: 	return postavi(1, 1);
					}
				}
				else if(ig.prazno(1, 1)) return postavi(1, 1);
			}
			
			if(ig.prazno(1, 1)) {
				if(ig.polje(0, 0) == c && ig.polje(0, 0) == ig.polje(2, 2)) return postavi(1, 1);
				if(ig.polje(0, 2) == c && ig.polje(0, 2) == ig.polje(2, 0)) return postavi(1, 1);
			}
			else if(ig.polje(1, 1) == c) {
				if(ig.polje(1, 1) == ig.polje(0, 0) && ig.prazno(2, 2)) return postavi(2, 2);
				if(ig.polje(1, 1) == ig.polje(2, 2) && ig.prazno(0, 0)) return postavi(0, 0);
				if(ig.polje(1, 1) == ig.polje(0, 2) && ig.prazno(2, 0)) return postavi(2, 0);
				if(ig.polje(1, 1) == ig.polje(2, 0) && ig.prazno(0, 2)) return postavi(0, 2);
			}
			
			for(int i = 0; i < 3; i++) {
				if(ig.polje(i, 0) == c) {
					if(ig.polje(i, 0) == ig.polje(i, 1) && ig.prazno(i, 2)) return postavi(i, 2);
					if(ig.polje(i, 0) == ig.polje(i, 2) && ig.prazno(i, 1)) return postavi(i, 1);
				}
				if(ig.prazno(i, 0))
					if(!ig.prazno(i, 1) && ig.polje(i, 1) == ig.polje(i, 2)) return postavi(i, 0);
				if(ig.polje(0, i) == c) {
					if(ig.polje(0, i) == ig.polje(1, i) && ig.prazno(2, i)) return postavi(2, i);
					if(ig.polje(0, i) == ig.polje(2, i) && ig.prazno(1, i)) return postavi(1, i);
				}
				if(ig.prazno(0, i))
					if(!ig.prazno(1, i) && ig.polje(1, i) == ig.polje(2, i)) return postavi(0, i);
			}
			
			if(!g) return igraj((c == 'x' ? 'o' : 'x'), true);
			else return postavi();
		}
		
		private boolean postavi(int i, int j) {
			igrao = true;
			ig.postavi(i, j);
			dugme[i][j].setText("" + Character.toUpperCase(ig.polje(i, j)));
			dugme[i][j].setEnabled(false);
			return true;
		}
		
		private boolean postavi() {
			int i, j;
			Random rand = new Random();
			
			do {
				i = rand.nextInt(3);
				j = rand.nextInt(3);
			} while(!ig.prazno(i, j));
			return postavi(i, j);
		}
	}
	
	private void kraj() {
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++) dugme[i][j].setEnabled(false);
		tekst.setFont(new Font(null, Font.BOLD, 13));
		switch(ig.kraj())
		{
		case 'x': 	tekst.setText("Pobedio je X!"); break;
		case 'o': 	tekst.setText("Pobedio je O!"); break;
		case 'n': 	tekst.setText("Nema pobednika."); break;
		}
		
	}
	
	public static void main(String[] args) {
		new Program();
	}

}
