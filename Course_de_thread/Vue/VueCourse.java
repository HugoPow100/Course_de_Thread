package Course_de_thread.Vue;

import javax.swing.*;

import Course_de_thread.Interfaces.IControleur;
import Course_de_thread.Interfaces.Observer;
import Course_de_thread.Metier.Coureur;
import Course_de_thread.Metier.CourseThreads;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Vue graphique de la course de threads
 * Implémente Observer pour se mettre à jour automatiquement
 * @author Hugo VARAO GOMES DA SILVA
 * @version 6.0
 */
public class VueCourse extends JFrame implements Observer 
{
	private IControleur   controleur;
	private PanneauCourse panneauCourse;
	private JButton       btnDemarrer;
	private JButton       btnPause;
	private JButton       btnReinitialiser;
	private JPanel        panneauBoutons;
	
	/**
	 * Constructeur de la vue
	 * @param controleur le contrôleur de l'application
	 */
	public VueCourse(IControleur controleur) 
	{
		this.controleur = controleur;
		
		// Configuration de la fenêtre
		setTitle("Course de Threads");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 600);
		setLocationRelativeTo(null);
		
		// Enregistrer la vue comme observateur du modèle (car il peut il avoir plusieur vue)
		this.controleur.getModele().ajouterObservateur(this);
		
		// Initialiser les composants
		initialiserComposants();
		
		setVisible(true);
	}
	
	/**
	 * Initialise les composants graphiques
	 */
	private void initialiserComposants() 
	{
		setLayout(new BorderLayout());
		
		// Panneau de course
		this.panneauCourse = new PanneauCourse(controleur.getModele());
		add(panneauCourse, BorderLayout.CENTER);
		
		// Panneau de boutons
		this.panneauBoutons = new JPanel();
		this.panneauBoutons.setBackground(Color.LIGHT_GRAY);
		
		this.btnDemarrer      = new JButton("Démarrer");
		this.btnPause         = new JButton("Pause");
		this.btnReinitialiser = new JButton("Réinitialiser");
		
		// Action listeners
		this.btnDemarrer.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				controleur.demarrerCourse();
			}
		});
		
		this.btnPause.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				controleur.pauseCourse();
			}
		});
		
		this.btnReinitialiser.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				controleur.reinitialiserCourse();
			}
		});
		
		this.panneauBoutons.add(btnDemarrer);
		this.panneauBoutons.add(btnPause);
		this.panneauBoutons.add(btnReinitialiser);
		
		add(this.panneauBoutons, BorderLayout.SOUTH);
	}
	
	/**
	 * Mise à jour de la vue (pattern Observer)
	 */
	@Override
	public void update() 
	{
		// Redessiner le panneau de course
		this.panneauCourse.repaint();
	}
}

