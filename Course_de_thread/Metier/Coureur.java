package Course_de_thread.Metier;
import java.util.Random;

/**
 * Classe représentant un coureur (thread) dans la course
 * @author Hugo VARAO GOMES DA SILVA
 * @version 7.0
 */
public class Coureur extends Thread 
{
	private static final int DISTANCE_ARRIVEE = 750; // Distance jusqu'à l'arrivée

	private int           numero;
	private int           position;
	private int           vitesse;
	private boolean       enCourse;
	private boolean       termine;
	private CourseThreads modele;
	private Random        random;
	
	/**
	 * Constructeur d'un coureur
	 * @param numero le numéro du coureur
	 * @param modele le modèle de la course
	 */
	public Coureur(int numero, CourseThreads modele) 
	{
		this.numero   = numero;
		this.position = 0;
		this.enCourse = false;
		this.termine  = false;
		this.modele   = modele;

		this.random   = new Random();
		
		// Vitesse aléatoire entre 1 et 5 pixels par itération
		this.vitesse  = random.nextInt(3) + 1;
		this.setName("Coureur-" + numero);
	}
	
	/**
	 * Méthode run() du thread
	 */
	@Override
	public void run() 
	{
		//Le "termine" est utile quand je tue le thread pour eviter que il ce 
		// mette attente infinie sans jamais mourir
		while (!termine && position < DISTANCE_ARRIVEE) 
		{
			//Cette verification et utile si on met en pause la course
			if (enCourse) 
			{
				// Avancer de manière aléatoire
				int avancement = random.nextInt(vitesse) + 1;
				this.position += avancement;
				
				// Notifier le modèle du changement
				this.modele.notifierObservateurs();
				
				// Vérifier si arrivé
				if (position >= DISTANCE_ARRIVEE && !termine) 
				{
					this.termine = true;
					this.modele.coureurTermine(this);
				}
				
				// J'ai découvert qu'il faut une pause aléatoire pour simuler la variation de vitesse
				//Et surtout éviter que ça plante et qu'ils arrivent tous à la fin direct sans l'effet visuel
				try 
				{
					sleep(random.nextInt(50) + 20);
				} 
				catch (InterruptedException e) 
				{
					this.termine = true;
				}
			} 
			else 
			{
				// En attente de démarrage
				try 
				{
					sleep(100);

					//Pour débeugage
					if(termine)
						System.out.println("je suis en attente alor que j'ai terminer");
				} 
				catch (InterruptedException e) 
				{
					this.termine = true;
				}
			}
		}
	}
	
	/**
	 * Démarre le coureur
	 */
	public void demarrer()  {this.enCourse = true;}
	
	/**
	 * Met en pause le coureur
	 */
	public void pause()     {this.enCourse = false;}
	
	/**
	 * Réinitialise le coureur
	 */
	public void reinitialiser() 
	{
		this.position = 0;
		this.termine  = false;
		this.enCourse = false;
		this.vitesse  = random.nextInt(3) + 1;
	}
	
	/**
	 * Arrête définitivement le coureur
	 */
	public void arreter() 
	{
		this.termine = true;
		this.interrupt();
	}
	
	// Getters
	public int     getNumero()   {return numero;}
	public int     getPosition() {return position;}
	public boolean isTermine()   {return termine;}
	public boolean isEnCourse()  {return enCourse;}
}
