package Course_de_thread;
import javax.swing.SwingUtilities;

import Course_de_thread.Interfaces.IControleur;
import Course_de_thread.Vue.VueCourse;

/**
 * Classe principale pour lancer la course de threads
 * @author Hugo VARAO GOMES DA SILVA
 * @version 2.0
 */
public class Main 
{
	public static void main(String[] args) 
	{
		// Nombre de threads participants
		final int NOMBRE_THREADS =10;

		// Lancer l'interface graphique dans le thread Swing
		SwingUtilities.invokeLater(new Runnable() 
		{
			@Override
			public void run() 
			{
				// Créer le contrôleur
				IControleur controleur = new Controleur(NOMBRE_THREADS);

				// Créer la vue
				VueCourse vue = new VueCourse(controleur);
			}
		});
	}
}
