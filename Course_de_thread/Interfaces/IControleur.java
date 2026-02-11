package Course_de_thread.Interfaces;
import Course_de_thread.Metier.CourseThreads;

/**
 * Interface du contrôleur pour la course de threads
 * Permet de découpler la vue du contrôleur
 * @author Hugo VARAO GOMES DA SILVA
 * @version 1.0
 */
public interface IControleur 
{
	/**
	 * Démarre la course
	 */
	void demarrerCourse();
	
	/**
	 * Met en pause la course
	 */
	void pauseCourse();
	
	/**
	 * Réinitialise la course
	 */
	void reinitialiserCourse();
	
	/**
	 * Récupère le modèle de la course
	 * @return le modèle CourseThreads
	 */
	CourseThreads getModele();
	
	/**
	 * Définit le nombre de threads participants
	 * @param nombre le nombre de threads
	 */
	void setNombreThreads(int nombre);
}
