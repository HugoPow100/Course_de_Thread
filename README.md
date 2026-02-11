# Course de Threads - Modéle Java MVC

Auteur : Hugo VARAO GOMES DA SILVA

## Description
Application de course de threads visualisée avec une interface graphique Swing.
Les threads (coureurs) sont représentés par des carrés rouges numérotés qui se déplacent horizontalement sur fond jaune.
Un classement s'affiche au fur et à mesure des arrivées.

## Architecture MVC

### Modèle (Model)
- **CourseThreads.java** : Gère la logique métier de la course
- **Coureur.java** : Représente un thread participant à la course
- **Observer.java** : Interface du pattern Observateur

### Vue (View)
- **VueCourse.java** : Interface graphique avec Swing
- **PanneauCourse.java** : Panneau de dessin de la course (classe interne)

### Contrôleur (Controller)
- **IControleur.java** : Interface du contrôleur (permet de créer différentes vues)
- **Controleur.java** : Implémentation du contrôleur

### Main
- **Main.java** : Point d'entrée de l'application

<br><br>

# Compilation


Pour compiler le projet et le lancer, vous devez utiliser le fichier "run" fourni sois :

### Windows => run.bat (PowerShell)
1. Ouvrez un terminal dans le dossier racine du projet (`coursThread`).
2. Lancez le script :

```powershell
.\run.bat
```

### Linux => run.sh (PowerShell)
1. Ouvrez un terminal dans le dossier racine du projet (`coursThread`).
2. Lancez le script :

```powershell
chmod +x run.sh
./run.sh
```
## <span style="color:red"> JavaDoc </span>

La documentation Java est disponible dans le dossier `doc/`.  
Ouvrez `index.html` pour consulter la documentation.

Commande pour la crée :
```powershell
javadoc -d doc -author -version Course_de_thread/*.java Course_de_thread/Interfaces/*.java Course_de_thread/Metier/*.java Course_de_thread/Vue/*.java
```

<br><br>

## Fonctionnalités

### Boutons de contrôle

1. **Démarrer** 
   - Lance la course
   - Tous les threads commencent à avancer
   
2. **Pause** 
   - Met en pause tous les coureurs
   - Les threads continuent d'exister mais n'avancent plus
   
3. **Réinitialiser**
   - Arrête tous les threads existants
   - Remet les positions à zéro
   - Recrée de nouveaux threads

## Caractéristiques techniques

- **Nombre de threads** : Modifiable dans Main.java (constante NOMBRE_THREADS)
- **Pattern Observateur** : La vue s'actualise automatiquement
- **Interface IControleur** : Permet de créer d'autres vues facilement
- **Threads en Java** : Chaque coureur est un thread indépendant
- **Vitesse aléatoire** : Chaque coureur avance à une vitesse variable
- **Classement dynamique** : Affichage en temps réel des arrivées



## <span style="color:red">Créer votre propre vue Swing</span>

Pour créer votre propre interface graphique, vous devez :

1. **Implémenter l'interface `Observer`** - pour recevoir les notifications
2. **Utiliser l'interface `IControleur`** - pour interagir avec le modèle
3. **S'enregistrer comme observateur** - `controleur.getModele().ajouterObservateur(this)`

<span style="color:red"> **Lien pour vous expliquer ce qu'est un "Observateur"** : [refactoring.guru](https://refactoring.guru/design-patterns/observer)</span> 
Ou Regarder plus loin dans le Readme j'ai fait une explication

#### Ce que vous devez savoir sur le contrôleur :

Le contrôleur implémente l'interface `IControleur` qui expose ces méthodes :

```java
public interface IControleur 
{
    void demarrerCourse();           // Lance la course
    void pauseCourse();              // Met en pause
    void reinitialiserCourse();      // Remet à zéro
    CourseThreads getModele();       // Accède au modèle
    void setNombreThreads(int n);    // Change le nombre de coureurs
}
```

À partir du modèle (`controleur.getModele()`), vous pouvez accéder à :

```java
CourseThreads modele = controleur.getModele();

// Récupérer les coureurs
List<Coureur> coureurs = modele.getCoureurs();

// Récupérer le classement
List<Coureur> classement = modele.getClassement();

// Savoir si la course est en cours
boolean courseEnCours = modele.isCourseEnCours();

// Savoir si la course est terminer
boolean coursTerminer = modele.isCourseTerminer();


// Pour chaque coureur, vous avez accès à :
for (Coureur c : coureurs) 
{
    int numero = c.getNumero();        // Son numéro
    int position = c.getPosition();    // Sa position (0 à 750)
    boolean termine = c.isTermine();   // S'il a fini
    boolean enCourse = c.isEnCourse(); // S'il court actuellement
}
```

---

##  Architecture MVC Complète

### Schéma d'architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                         UTILISATEUR                             │
└───────────────────────────┬─────────────────────────────────────┘
                            │ Clique sur boutons
                            ↓
┌─────────────────────────────────────────────────────────────────┐
│                        VUE (View)                               │
│  ┌──────────────┐      ┌──────────────┐                         │
│  │  VueCourse   │      │ VueConsole   │  (implements Observer)  │
│  │  (Swing)     │      │  (Console)   │                         │
│  └──────┬───────┘      └──────┬───────┘                         │
│         │ observe              │ observe                        │
└─────────┼──────────────────────┼────────────────────────────────┘
          │                      │
          │   ┌──────────────────┘
          │   │
          ↓   ↓
┌─────────────────────────────────────────────────────────────────┐
│                    CONTRÔLEUR (Controller)                      │
│  ┌──────────────────────────────────────────────────────┐       │
│  │  IControleur (interface) ← découplage !              │       │
│  │       ↑                                              │       │
│  │       │ implements                                   │       │
│  │  Controleur                                          │       │
│  │    - demarrerCourse()                                │       │
│  │    - pauseCourse()                                   │       │
│  │    - reinitialiserCourse()                           │       │
│  └──────────────────────────────────────────────────────┘       │
└────────────────────────────┬────────────────────────────────────┘
                             │ manipule
                             ↓
┌─────────────────────────────────────────────────────────────────┐
│                      MODÈLE (Model)                             │
│  ┌──────────────────────────────────────────────────────┐       │
│  │  CourseThreads                                       │       │
│  │    - List<Coureur> coureurs                          │       │
│  │    - List<Coureur> classement                        │       │
│  │    - List<Observer> observateurs  ← Pattern Observer │       │
│  │    + notifierObservateurs()                          │       │
│  └────────────────┬─────────────────────────────────────┘       │
│                   │ contient                                    │
│                   ↓                                             │
│  ┌──────────────────────────────────────────────────────┐       │
│  │  Coureur (extends Thread)                            │       │
│  │    - int position                                    │       │
│  │    - int vitesse                                     │       │
│  │    + run()  ← Thread exécuté en parallèle            │       │
│  └──────────────────────────────────────────────────────┘       │
└─────────────────────────────────────────────────────────────────┘
```

### <span style="color:red"> Fichiers du projet </span>

#### 🔵 Modèle (Model)
- **CourseThreads.java** : Gère la logique métier de la course
  - Contient la liste des coureurs
  - Gère le classement
  - Implémente le pattern Observateur (liste d'observateurs)
  
- **Coureur.java** : Représente un thread participant à la course
  - Étend la classe `Thread`
  - Avance de manière aléatoire
  - Notifie le modèle à chaque changement

- **Observer.java** : Interface du pattern Observateur
  - Méthode `update()` pour être notifié des changements

#### 🟢 Vue (View)
- **VueCourse.java** : Interface graphique avec Swing
  - Implémente `Observer`
  - Se redessine automatiquement via `update()`
  - Contient les boutons de contrôle
  
- **PanneauCourse.java** : Panneau de dessin (classe interne dans VueCourse)
  - Fond jaune
  - Carrés rouges pour les coureurs
  - Affichage du classement

- **VueConsole.java** : Vue alternative en mode texte
  - Affiche des barres de progression
  - Démontre qu'on peut avoir plusieurs vues

#### 🟡 Contrôleur (Controller)
- **IControleur.java** : Interface du contrôleur
  - Permet de créer différentes implémentations
  - Découplage entre Vue et Contrôleur
  
- **Controleur.java** : Implémentation du contrôleur
  - Fait le lien entre Vue et Modèle
  - Méthodes : `demarrerCourse()`, `pauseCourse()`, `reinitialiserCourse()`

#### 🔴 Main
- **Main.java** : Point d'entrée standard (une seule vue graphique)
- **MainDeuxVues.java** : Démonstration avec deux vues simultanées



## <span style="color:red">Le Pattern Observateur Expliqué </span>

### Le problème

Comment la Vue sait-elle QUAND se redessiner ?

**Bonne solution** : Pattern Observateur (système d'abonnement)

### Comment ça marche ?

C'est comme **YouTube** :
- Vous vous **abonnez** à une chaîne (la Vue s'abonne au Modèle)
- Quand la chaîne publie, **tous les abonnés** reçoivent une notification
- La chaîne ne connaît pas QUI sont ses abonnés (juste qu'ils existent)

### Schéma de flux complet

```
┌────────────────────────────────────────────────────────────────┐
│  ÉTAPE 1 : INITIALISATION (au démarrage)                       │
└────────────────────────────────────────────────────────────────┘

Main.java
  │
  ├─→ new Controleur(8)
  │     │
  │     └─→ new CourseThreads(8)
  │           │
  │           └─→ observateurs = [] (liste vide)
  │
  └─→ new VueCourse(controleur)
        │
        └─→ controleur.getModele().ajouterObservateur(this)
              │
              └─→ observateurs = [VueCourse]


┌────────────────────────────────────────────────────────────────┐
│  ÉTAPE 2 : L'UTILISATEUR CLIQUE SUR "Démarrer"                 │
└────────────────────────────────────────────────────────────────┘

VueCourse (bouton)
  │
  └─→ controleur.demarrerCourse()
        │
        └─→ modele.demarrerCourse()
              │
              └─→ Pour chaque Coureur : c.demarrer()
                    │
                    └─→ enCourse = true


┌────────────────────────────────────────────────────────────────┐
│  ÉTAPE 3 : LES THREADS S'EXÉCUTENT (en parallèle !)            │
└────────────────────────────────────────────────────────────────┘

Coureur 1 (Thread)          Coureur 2 (Thread)          Coureur 3...
  │                           │                           │
  └─→ run() {                 └─→ run() {                 └─→ run() {
        while(...) {                while(...) {                while(...) {
          position += 5                position += 3                position += 7
          │                            │                            │
          └─→ modele.notifierObservateurs()
                │
                └─────────────────┬──────────────────┐
                                  │                  │
                                  ↓                  ↓
                         VueCourse.update()    VueConsole.update()
                                  │                  │
                                  └─→ repaint()      └─→ println()
        }                         }                  }
      }                         }                  }


┌────────────────────────────────────────────────────────────────┐
│  ÉTAPE 4 : UN COUREUR ARRIVE (position >= 750)                │
└────────────────────────────────────────────────────────────────┘

Coureur 1 (Thread)
  │
  └─→ if (position >= 750) {
        modele.coureurTermine(this)
          │
          └─→ classement.add(coureur)  ← synchronized !
              │
              └─→ notifierObservateurs()
                    │
                    └─→ VueCourse.update()
                          │
                          └─→ Redessine avec le classement mis à jour
      }
```

### Code détaillé du flux

#### 1️. La Vue s'abonne au démarrage

```java
// Dans VueCourse.java
public VueCourse(IControleur controleur) 
{
    this.controleur = controleur;
    
    // S'ABONNER aux notifications du modèle
    controleur.getModele().ajouterObservateur(this);
    //                                          ↑
    //                                   "this" = VueCourse
}
```

#### 2️. Le Modèle garde ses abonnés

```java
// Dans CourseThreads.java
private List<Observer> observateurs = new ArrayList<>();

public void ajouterObservateur(Observer obs) 
{
    observateurs.add(obs); // Ajouter à la liste
}

// observateurs = [VueCourse, VueConsole, ...]
```

#### 3️. Un Coureur (Thread) change de position

```java
// Dans Coureur.java - méthode run()
public void run() 
{
    while (!termine && position < 750) 
    {
        if (enCourse) 
        {
            // AVANCER
            int avancement = random.nextInt(vitesse) + 1;
            position += avancement;
            
            // NOTIFIER le modèle
            modele.notifierObservateurs();
            
            // PAUSE aléatoire
            try 
            {
                sleep(random.nextInt(50) + 20);
            } 
            catch (InterruptedException e) 
            {
                termine = true;
            }
        }
    }
}
```

#### 4️. Le Modèle notifie TOUS ses observateurs

```java
// Dans CourseThreads.java
public void notifierObservateurs() 
{
    // Parcourir la liste d'observateurs
    for (Observer obs : observateurs) 
    {
        obs.update(); // Appeler update() sur chaque observateur
    }
}

// Si observateurs = [VueCourse, VueConsole]
// → VueCourse.update() est appelé
// → VueConsole.update() est appelé
```

#### 5️. La Vue reçoit la notification et se redessine

```java
// Dans VueCourse.java
@Override
public void update() 
{
    // Redessiner le panneau
    panneauCourse.repaint();
}

// Le système appelle automatiquement paintComponent()
// qui redessine les carrés rouges aux nouvelles positions !
```

---

## Les Threads en Détail

### Création des threads

```java
// Dans CourseThreads.java
private void initialiserCoureurs() 
{
    for (int i = 0; i < nombreCoureurs; i++) 
    {
        Coureur c = new Coureur(i + 1, this);
        coureurs.add(c);
        c.start(); // ← Lance le thread (appelle run() en arrière-plan)
    }
}
```

### Cycle de vie d'un Coureur (Thread)

```
NEW (créé)
  │
  │ start()
  ↓
RUNNABLE (en attente d'exécution)
  │
  │ Le système lui donne du temps CPU
  ↓
RUNNING (exécute run())
  │
  ├─→ sleep() ──→ TIMED_WAITING  ──┐
  │                                │
  │ ← temps écoulé ────────────────┘
  │
  ├─→ enCourse = false ──→ Boucle sans avancer
  │
  ├─→ position >= 750 ──→ termine = true
  │
  ↓
TERMINATED (fin de run())
```

### Synchronisation importante

```java
// Dans CourseThreads.java
public synchronized void coureurTermine(Coureur coureur) 
{
    //     ↑
    //     synchronized = un seul thread à la fois !
    
    if (!classement.contains(coureur)) {
        classement.add(coureur);
        notifierObservateurs();
    }
}
```

**Pourquoi `synchronized` ?**
- Plusieurs threads peuvent arriver en même temps
- Sans `synchronized`, on pourrait avoir des bugs de concurrence
- Exemple : deux threads ajoutent au classement simultanément → corruption de données

---


## Caractéristiques Techniques

### Configuration

```java
// Dans Main.java
final int NOMBRE_THREADS = 8; // Modifiable !
```

### Vitesse des coureurs

Chaque coureur a une vitesse aléatoire :
```java
// Dans Coureur.java
this.vitesse = random.nextInt(3) + 1; // Entre 1 et 3
int avancement = random.nextInt(vitesse) + 1; // Variation
```

### Distance de la course

```java
private static final int DISTANCE_ARRIVEE = 750; // pixels
```

### Fréquence de mise à jour

```java
sleep(random.nextInt(50) + 20); // Entre 20 et 70 ms
```

---

## Points d'Apprentissage vue en Cours

Cette application illustre **tous** les concepts du cours sur les threads :

### Création de threads
- Extension de la classe `Thread`
- Implémentation de la méthode `run()`
- Appel de `start()` (jamais `run()` directement !)

### Cycle de vie des threads
- **NEW** : Après `new Coureur()`
- **RUNNABLE** : Après `start()`
- **TIMED_WAITING** : Pendant `sleep()`
- **TERMINATED** : Fin de `run()`

### Méthodes importantes
- `start()` : Lance le thread
- `run()` : Code exécuté par le thread
- `sleep(ms)` : Met en pause le thread
- `interrupt()` : Interrompt le thread
- `join()` : Attend la fin du thread

### Synchronisation
- Mot-clé `synchronized` sur `coureurTermine()`
- Évite les problèmes de concurrence

### Gestion de plusieurs threads
- 10 threads s'exécutent en parallèle
- Chacun avance indépendamment
- Coordination via le modèle

### Architecture MVC
- Séparation claire Modèle / Vue / Contrôleur
- Découplage via interfaces

### Pattern Observateur
- Communication Modèle → Vue sans couplage
- Permet plusieurs vues simultanées

---



### Ressources
- Pattern Observateur : [refactoring.guru](https://refactoring.guru/design-patterns/observer)
- Documentation Java sur les Threads : [docs.oracle.com](https://docs.oracle.com/javase/tutorial/essential/concurrency/)



# Auteur

Hugo VARAO GOMES DA SILVA



