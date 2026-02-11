package Course_de_thread.Metier;
import java.util.ArrayList;
import java.util.List;

import Course_de_thread.Interfaces.Observer;

/**
 * Modèle de la course de threads
 * Gère les coureurs et le classement
 * @author Hugo VARAO GOMES DA SILVA
 * @version 7.0
 */
public class CourseThreads 
{
	private List<Coureur> 	coureurs;
	private List<Coureur> 	classement;
	private List<Observer> 	observateurs;
	private int 			nombreCoureurs;
	private boolean 		courseEnCours;
	private boolean			coursTerminer;
	
	/**
	 * Constructeur du modèle
	 * @param nombreCoureurs le nombre de coureurs participants
	 */
	public CourseThreads(int nombreCoureurs) 
	{
		this.nombreCoureurs = nombreCoureurs;
		this.coureurs       = new ArrayList<>();
		this.classement     = new ArrayList<>();
		this.observateurs   = new ArrayList<>();
		this.courseEnCours  = false;
		this.coursTerminer  = false;

		initialiserCoureurs();
	}
	
	/**
	 * Initialise les coureurs
	 */
	private void initialiserCoureurs() 
	{
		this.coureurs.clear();
		for (int i = 0; i < nombreCoureurs; i++) 
		{
			Coureur c = new Coureur(i + 1, this);
			this.coureurs.add(c);
			c.start();
		}
	}
	
	/**
	 * Démarre la course
	 */
	public void demarrerCourse() 
	{
		if (!this.courseEnCours) 
		{
			this.courseEnCours = true;
			for (Coureur c : coureurs) 
			{
				c.demarrer();
			}
			notifierObservateurs();
		}
	}
	
	/**
	 * Met en pause la course
	 */
	public void pauseCourse() 
	{
		if (this.courseEnCours) 
		{
			this.courseEnCours = false;
			for (Coureur c : coureurs) 
			{
				c.pause();
			}
			notifierObservateurs();
		}
	}
	
	/**
	 * Réinitialise la course
	 */
	public void reinitialiserCourse() 
	{
		// Arrêter tous les threads existants
		for (Coureur c : coureurs) 
		{
			c.arreter();
		}
		
		// Attendre que tous les threads se terminent
		for (Coureur c : coureurs) 
		{
			try 
			{
				c.join(1000); // Delais de 1 seconde
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
		// Réinitialiser l'état
		this.classement.clear();
		this.courseEnCours = false;
		this.coursTerminer = false;
		
		// Recréer les coureurs
		initialiserCoureurs();
		notifierObservateurs();
	}
	
	/**
	 * Appelé lorsqu'un coureur termine la course
	 * @param coureur le coureur qui a terminé
	 */
	public synchronized void coureurTermine(Coureur coureur) 
	{
		if (!this.classement.contains(coureur)) 
		{
			this.classement.add(coureur);
			notifierObservateurs();
			
			// Vérifier si tous les coureurs ont terminé
			if (this.classement.size() == this.nombreCoureurs) 
			{
				this.courseEnCours = false;
				this.coursTerminer = true;
			}
		}
	}
	
	/**
	 * Ajoute un observateur
	 * @param obs l'observateur à ajouter
	 */
	public void ajouterObservateur(Observer obs) 
	{
		this.observateurs.add(obs);
	}
	
	/**
	 * Retire un observateur
	 * @param obs l'observateur à retirer
	 */
	public void retirerObservateur(Observer obs) 
	{
		this.observateurs.remove(obs);
	}
	
	/**
	 * Notifie tous les observateurs d'un changement
	 */
	public void notifierObservateurs() 
	{
		for (Observer obs : observateurs) 
		{
			obs.update();
		}
	}
	
	/**
	 * Modifie le nombre de coureurs
	 * @param nombre le nouveau nombre de coureurs
	 */
	public void setNombreCoureurs(int nombre) 
	{
		this.nombreCoureurs = nombre;
	}
	
	// Getters
	public List<Coureur> getCoureurs()	     { return this.coureurs;}
	public List<Coureur> getClassement()     {return this.classement;}
	public boolean       isCourseEnCours()	 {return this.courseEnCours;}
	public boolean       isCourseTerminer()	 {return this.coursTerminer;}
	public int           getNombreCoureurs() { return this.nombreCoureurs;}
}
