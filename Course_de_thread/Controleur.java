package Course_de_thread;
import Course_de_thread.Interfaces.IControleur;
import Course_de_thread.Metier.CourseThreads;

/**
 * Contrôleur de la course de threads
 * Fait le lien entre le modèle et la vue
 * @author Hugo VARAO GOMES DA SILVA
 * @version 4.0
 */
public class Controleur implements IControleur 
{
	private CourseThreads modele;
	
	/**
	 * Constructeur du contrôleur
	 * @param nombreThreads le nombre de threads participants
	 */
	public Controleur(int nombreThreads) 
	{
		this.modele = new CourseThreads(nombreThreads);
	}
	
	/**
	 * Démarre la course
	 */
	@Override
	public void demarrerCourse() 
	{
		this.modele.demarrerCourse();
	}
	
	/**
	 * Met en pause la course
	 */
	@Override
	public void pauseCourse() 
	{
		this.modele.pauseCourse();
	}
	
	/**
	 * Réinitialise la course
	 */
	@Override
	public void reinitialiserCourse() 
	{
		this.modele.reinitialiserCourse();
	}
	
	/**
	 * Récupère le modèle
	 * @return le modèle CourseThreads
	 */
	@Override
	public CourseThreads getModele() 
	{
		return this.modele;
	}
	
	/**
	 * Définit le nombre de threads
	 * @param nombre le nombre de threads
	 */
	@Override
	public void setNombreThreads(int nombre) 
	{
		this.modele.setNombreCoureurs(nombre);
	}

	
}
