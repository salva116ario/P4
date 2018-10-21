/* Jeu de Puissance 4 - Simplon LPS 3
 * Ce jeu se joue à deux joueurs sur une grille de hauteur 6 et de largeur 7.
 * Les joueurs laissent tomber chacun leur tour un jeton dans une colonne.
 * Lorsqu’un joueur aligne 4 jetons de sa couleur (en ligne ou en diagonale), il a gagné.
 * Il peut arriver qu’aucun joueur ne parvienne à aligner 4 jetons, donnant lieu à un match nul.
 */

import java.util.Scanner;


public class Puissance4 {


	static Scanner saisie;
	static int nbCol = 7, nbLig = 6;
	static int nbJetonsNecessaires = 4;
	static int joueurEnCours = 1, ligneEnCours = 0, colEnCours = 0;
	static int[][] grille = new int[nbCol][nbLig];
	static String[] joueur = new String[3]; // la variable joueur[0] ne sera pas utilisée...
	static char[] jeton = { ' ', 'O', '@' };
	static int compteur = 0;
	static boolean partieFinie = false;


	public static void main(String[] args) {
		saisie = new Scanner(System.in);
		System.out.println(choixModeJeu());
		partieJeu();

	}

	public static int choixModeJeu() {
    System.out.println("Choisissez le mode de jeu :");
    System.out.println(" 1 - Mode un joueur VS computer");
    System.out.println(" 2 - Mode deux joueurs");
    boolean choixValide = true;
    do {
    String choixMode = saisie.nextLine();
    if (!choixMode.matches("[1-2]")) {
      System.out.println("Choix incorrect");
      choixValide = false;
    }
  } while (!choixValide);
  return Integer.parseInt(choixMode);

}

	public static void initJoueurs() {
		for (int i = 1; i < 3; i++) {
			System.out.println("Joueur " + (i) + " entrez votre prénom : ");
			joueur[i] = saisie.nextLine();
		}
		System.out.println(joueur[1] + " " + jeton[1] + "  vs  " + joueur[2] + " " + jeton[2]);
	}


	public static void tirageAuSort() {
		int hasard = (int) (Math.random() * 10);
		joueurEnCours = (hasard % 2 == 0) ? 1 : 2;
		System.out.println("C'est " + joueur[joueurEnCours] + " qui commence...\n");
	}


	public static void affichageGrille() {
		compteur++;
		System.out.println("\nTOUR NUMERO " + compteur + "\n");

		for (int i = nbLig - 1; i >= 0; i--) {
			for (int j = 0; j < nbCol; j++) {
				System.out.print("| " + jeton[grille[j][i]] + " ");
			}
			System.out.println("|");
		}

		for (int i = 0; i < nbCol; i++) {
			System.out.print("|---");
		}

		System.out.println("|");

		for (int i = 0; i < nbCol; i++) {
			System.out.print("| " + i + " ");
		}

		System.out.println("|");
	}


	public static void choixColonne() {
		int colChoix = 0;
		boolean choixValide;

		do {
			choixValide = false;

		    System.out.println("\n" + joueur[joueurEnCours] + " (" + jeton[joueurEnCours]
		        + ") , veuillez choisir votre colonne : ");
					String choix = saisie.nextLine();

				if (choix.matches("[0-9]")) {
					choixValide=true;
					colChoix = Integer.parseInt(choix);
						if (colChoix<0 || colChoix>nbCol) {
							System.out.println("Choix invalide recommencez.");
							choixValide = false;
						}
				} else {
					System.out.println("Choix invalide recommencez.");
				}
		} while (choixValide == false);

		testColonne(colChoix);

		colEnCours = colChoix;
		ligneEnCours = detLig();
	}


	public static void testColonne(int col) { // méthode pour vérifier si la colonne est pleine.
		if (grille[col][nbLig-1] != 0) {
			System.out.println("Colonne pleine recommencez.");
			choixColonne();
		}
	}
		// Cette boucle va enregistrer la position du jeton dans la grille
		// La colonne est celle indiquée par le joueur.
		// La ligne correspondra à la première case disponible dans cette colonne.


	public static int detLig() { //détermine à quelle ligne le jeton s'arrête.
		for (int i = 0; i < nbLig; i++) {
			if (grille[colEnCours][i] == 0) {
				grille[colEnCours][i] = joueurEnCours;
				return i;
			}
		}
		return 0;
	}


	public static boolean verifHoriz() {
		int compteurJeton = 0;

		for (int i = 0; i < nbCol; i++) {

			if (grille[i][ligneEnCours] != joueurEnCours) {
				compteurJeton = 0;
			}
			if (grille[i][ligneEnCours] == joueurEnCours) {
				compteurJeton++;
			}
			if (compteurJeton == nbJetonsNecessaires) {
				return true;
			}
		}
		return false;
	}


	public static boolean verifVertic() {
		int compteurJeton = 0;

		for (int i = 0; i < nbLig; i++) {
			if (grille[colEnCours][i] != joueurEnCours) {
				compteurJeton = 0;
			}
			if (grille[colEnCours][i] == joueurEnCours) {
				compteurJeton++;
			}
			if (compteurJeton == nbJetonsNecessaires) {
				return true;
			}
		}
		return false;
	}


	public static boolean verifDiag() {
		int colTest = colEnCours;
		int ligneTest = ligneEnCours;
		int compteurJeton = 1;
		boolean continueWhile = true;

		// verif première diagonale : / du bas à gauche du tableau vers le haut à droite du tableau

		while (colTest > 0 && ligneTest > 0 && continueWhile) {
			colTest--;
			ligneTest--;

			if (grille[colTest][ligneTest] == joueurEnCours) {
				compteurJeton++;
			} else {
				continueWhile = false;
			}
		}

		continueWhile = true;
		colTest = colEnCours;
		ligneTest = ligneEnCours;
		while (colTest < nbCol - 1 && ligneTest < nbLig - 1 && continueWhile) {
			colTest++;
			ligneTest++;
			if (grille[colTest][ligneTest] == joueurEnCours) {
				compteurJeton++;
			} else {
				continueWhile = false;
			}
		}

		if (compteurJeton >= nbJetonsNecessaires) {
			return true;
		}


		// verif deuxième diagonale : \ du haut à gauche du tableau vers le bas à droite du tableau
		compteurJeton = 1;
		continueWhile = true;
		colTest = colEnCours;
		ligneTest = ligneEnCours;
		while (colTest > 0 && ligneTest < nbLig - 1 && continueWhile) {
			colTest--;
			ligneTest++;
			if (grille[colTest][ligneTest] == joueurEnCours) {
				compteurJeton++;
			} else {
				continueWhile = false;
			}
		}

		continueWhile = true;
		colTest = colEnCours;
		ligneTest = ligneEnCours;
		while (colTest < nbCol - 1 && ligneTest > 0 && continueWhile) {
			colTest++;
			ligneTest--;
			if (grille[colTest][ligneTest] == joueurEnCours) {
				compteurJeton++;
			} else {
				continueWhile = false;
			}
		}
		if (compteurJeton >= nbJetonsNecessaires) {
			return true;
		}

		return false;
	}


	public static boolean verifGrillePleine() {
		return compteur == (nbLig * nbCol + 1);
	}


	public static void finPartie() {
		partieFinie = true;
		System.out.println("La partie est terminée : " + joueur[joueurEnCours] + " gagne");
	}


	public static void partieNul() {
		partieFinie = true;
		System.out.println("La grille est pleine, match nul");
	}

	public static void partieJeu() {
		initJoueurs();
		tirageAuSort();
		affichageGrille();

		do {
			choixColonne();
			affichageGrille();

			if (verifHoriz() || (verifVertic()) || (verifDiag())) {
				finPartie();
			}

			else if (verifGrillePleine()) {
				partieNul();
			}
			// Les tests de vérification n'ont pas désigné de vainqueur ou une partie nulle :
			// On change de joueur, et la partie continue
			joueurEnCours = joueurEnCours%2 + 1;

		} while (!partieFinie);

	}
}
