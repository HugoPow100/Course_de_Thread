package Course_de_thread.Vue;

import Course_de_thread.Metier.CourseThreads;
import Course_de_thread.Metier.Coureur;

import javax.swing.JPanel;
import java.awt.*;
import java.util.List;

/**
 * Panneau où se déroule la course
 * @author Hugo VARAO GOMES DA SILVA
 * @version 6.0
 */
class PanneauCourse extends JPanel 
{
	private static final int TAILLE_CARRE = 30;
	private static final int MARGE_GAUCHE = 50;
	private static final int MARGE_DROITE = 200;
	private static final int ESPACEMENT_VERTICAL = 50;

	private CourseThreads modele;
	
	/**
	 * Constructeur du panneau de course
	 * @param modele le modèle de la course
	 */
	public PanneauCourse(CourseThreads modele) 
	{
		this.modele = modele;
		setBackground(new Color(255, 255, 150)); // un Jaune inpeu pale
	}
	
	/**
	 * Dessine le panneau
	 */
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		// Dessiner la ligne d'arrivée 
		int largeurPiste = getWidth() - MARGE_GAUCHE - MARGE_DROITE;
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
		g2d.drawLine(MARGE_GAUCHE + largeurPiste, 0, MARGE_GAUCHE + largeurPiste, getHeight());
		
		// Dessiner les coureurs
		g2d.setStroke(new BasicStroke(1));
		int y = ESPACEMENT_VERTICAL;
		for (Coureur c : modele.getCoureurs()) 
		{
			// Calculer la position X en fonction de la position du coureur
			int posX = MARGE_GAUCHE + (int)((c.getPosition() / 750.0) * largeurPiste);
			
			// Dessiner le carré rouge
			g2d.setColor(Color.RED);
			g2d.fillRect(posX, y, TAILLE_CARRE, TAILLE_CARRE);
			
			// Dessiner le numéro
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("Arial", Font.BOLD, 16));

			String      numero     = String.valueOf(c.getNumero());
			FontMetrics fm         = g2d.getFontMetrics();
			int         numeroX    = posX + (TAILLE_CARRE - fm.stringWidth(numero)) / 2;
			int         numeroY    = y + ((TAILLE_CARRE - fm.getHeight()) / 2) + fm.getAscent();

			g2d.drawString(numero, numeroX, numeroY);
			
			y += ESPACEMENT_VERTICAL;
		}
		
		// Dessiner le classement
		dessinerClassement(g2d, largeurPiste);
	}
	
	/**
	 * Dessine le classement des arrivées
	 * @param g2d le contexte graphique
	 * @param largeurPiste la largeur de la piste
	 */
	private void dessinerClassement(Graphics2D g2d, int largeurPiste) 
	{
		if (this.modele.getClassement().isEmpty()) 
		{
			return;
		}
		
		int x = MARGE_GAUCHE + largeurPiste + 70;
		int y = 50;
		
		if (this.modele.isCourseTerminer())
		{
			g2d.setColor(Color.MAGENTA);
			g2d.setFont(new Font("SansSerif", Font.BOLD, 16));
			g2d.drawString("Course terminée", x-20, y);
			y += 25;
		}

		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Arial", Font.BOLD, 14));
		g2d.drawString("Classement:", x, y);
		
		y += 25;
		g2d.setFont(new Font("Arial", Font.PLAIN, 12));
		
		/* Ici, j'ai rencontré des problèmes de plantage parce que, en parcourant
		 l'ArrayList ou en faisant une copie du tableau, la liste était
		modifiée en même temps donc sa m'était une exception. J'ai donc découvert 
		qu'on pouvait placer un "synchronized" comme suit pour régler le problème. */
		int position = 1;
		List<Coureur> listClassementCopie;
		synchronized (modele) 
		{
			listClassementCopie = new java.util.ArrayList<>(modele.getClassement());
		}

		for (Coureur c : listClassementCopie ) 
		{
			String texte = position + ". Coureur " + c.getNumero();
			g2d.drawString(texte, x, y);
			y += 20;
			position++;
		}
	}
}
