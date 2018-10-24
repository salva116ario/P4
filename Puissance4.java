import java.util.Scanner;

public class Puissance4 {

    private static Scanner saisie;
    private static int nbreLignes = 6, nbreColonnes = 7, nbreJetonsNecessaires = 4, compteur = 0;
    private static boolean partieFinie = false;

    public static void main(String args[]) {

        Grid grille = new Grid(nbreColonnes, nbreLignes);
        ModeDeJeu modeJeu = new ModeDeJeu(choixModeDeJeu());
        Joueur[] joueur = new Joueur[3];
        initJoueurs(joueur, modeJeu);
        int joueurEnCours = tirageAuSort();
        System.out.println("C'est " + joueur[joueurEnCours].name + " (" + joueur[joueurEnCours].jeton + ") qui commence.");
        affichageGrille(grille);
        jouer(grille, modeJeu, joueur, joueurEnCours);
    }


    private static void jouer(Grid grille, ModeDeJeu modeJeu, Joueur[] joueur, int joueurEnCours) {

        do {
            if (modeJeu.getModeDeJeu() == 1 && joueurEnCours == 2) {
                partieFinie = computerPlaying(grille, joueur[1], joueur[1].lastColonne, joueur[1].lastLigne, joueur[2]);
            } else {
                choixColonne(joueur[joueurEnCours], grille);
            }
            affichageGrille(grille);

            if (!((modeJeu.getModeDeJeu() == 1) && (joueurEnCours == 2))) {
                if (verifHorizontale(grille, joueur[joueurEnCours], joueur[joueurEnCours].lastLigne) || verifVerticale(grille, joueur[joueurEnCours], joueur[joueurEnCours].lastColonne) || verifDiagonales(grille, joueur[joueurEnCours], joueur[joueurEnCours].lastColonne, joueur[joueurEnCours].lastLigne)) {
                    finPartie(joueur[joueurEnCours]);
                    partieFinie = true;
                }
                if (verifGrillePleine()) {
                    matchNul();
                    partieFinie = true;
                }
            }
            joueurEnCours = joueurEnCours % 2 + 1;
        } while (!partieFinie);
    }


    private static void initJoueurs(Joueur[] joueur, ModeDeJeu modeJeu) {
        if (modeJeu.getModeDeJeu() == 1) {
            joueur[2] = new Joueur(2, "Computer");
        }

        for (
                int i = 1; i < modeJeu.getModeDeJeu() + 1; i++) {
            joueur[i] = new Joueur(i, getNom(i));
        }

        for (
                int i = 1;
                i < 3; i++) {
            System.out.println(joueur[i].toString());
        }

    }


    private static int choixModeDeJeu() {
        System.out.println("Choisissez le mode de jeu :\n1 - Mode un joueur VS computer\n2 - Mode deux joueurs");
        boolean choixValide;
        String choixMode;
        saisie = new Scanner(System.in);
        do {
            choixValide = true;
            choixMode = saisie.nextLine();
            if (!choixMode.matches("[1-2]")) {
                System.out.println("Choix incorrect, recommencez : ");
                choixValide = false;
            }
        } while (!choixValide);
        return Integer.parseInt(choixMode);

    }


    private static String getNom(int num) {
        System.out.println("Joueur " + (num) + " entrez votre prénom : ");
        return saisie.nextLine();
    }

    private static int tirageAuSort() {
        int hasard = (int) (Math.random() * 10);
        return (hasard % 2 == 0) ? 1 : 2;
    }

    private static void affichageGrille(Grid grille) {
        compteur++;
        System.out.println("\nTOUR NUMERO " + compteur + "\n");
        String affichageCase = " O@";
        for (int i = nbreLignes - 1; i >= 0; i--) {
            for (int j = 0; j < nbreColonnes; j++) {
                System.out.print("| " + affichageCase.charAt(grille.getCases(j, i)) + " ");
            }
            System.out.println("|");
        }

        for (int i = 0; i < nbreColonnes; i++) {
            System.out.print("|---");
        }

        System.out.println("|");

        for (int i = 0; i < nbreColonnes; i++) {
            System.out.print("| " + i + " ");
        }

        System.out.println("|");
    }


    private static void choixColonne(Joueur joueur, Grid grille) {
        int colonneChoisie = 0;
        boolean choixValide;


        do {
            choixValide = false;

            System.out.println("\n" + joueur + ", veuillez choisir votre colonne : ");
            String choix = saisie.nextLine();

            if (choix.matches("[0-9]")) {
                choixValide = true;
                colonneChoisie = Integer.parseInt(choix);
                if ((colonneChoisie < 0) || (colonneChoisie >= nbreColonnes)) {
                    System.out.println("Choix invalide recommencez.");
                    choixValide = false;
                }
            } else System.out.println("Choix invalide recommencez.");
        } while (!choixValide);

        testColonne(joueur, grille, colonneChoisie);
        joueur.setLastColonne(colonneChoisie);
        joueur.setLastLigne(determineLigne(joueur, grille, colonneChoisie));
    }

    private static int determineLigne(Joueur joueur, Grid grille, int colonneChoisie) { //détermine à quelle ligne le jeton s'arrête.
        for (int i = 0; i < nbreLignes; i++) {
            if (grille.cases[colonneChoisie][i] == 0) {
                grille.cases[colonneChoisie][i] = joueur.id;
                return i;
            }
        }
        return 0;
    }

    private static int determineLigne(Grid grille, int colonneTestee) {
        for (int i = 0; i < nbreLignes; i++) {
            if (grille.cases[colonneTestee][i] == 0) {
                return i;
            }
        }
        return 0;
    }

    private static void testColonne(Joueur joueur, Grid grille, int colonne) { // méthode pour vérifier si la colonne est pleine.
        if (grille.cases[colonne][nbreLignes - 1] != 0) {
            System.out.println("Colonne pleine recommencez.");
            choixColonne(joueur, grille);
        }
    }


    private static boolean verifHorizontale(Grid grille, Joueur joueur, int ligne) {
        int compteurJeton = 0;

        for (int i = 0; i < nbreColonnes; i++) {

            if (grille.cases[i][ligne] != joueur.id) {
                compteurJeton = 0;
            }
            if (grille.cases[i][ligne] == joueur.id) {
                compteurJeton++;
            }
            if (compteurJeton == nbreJetonsNecessaires) {
                return true;
            }
        }
        return false;
    }


    private static boolean verifVerticale(Grid grille, Joueur joueur, int colonne) {
        int compteurJeton = 0;

        for (int i = 0; i < nbreLignes; i++) {
            if (grille.cases[colonne][i] != joueur.id) {
                compteurJeton = 0;
            }
            if (grille.cases[colonne][i] == joueur.id) {
                compteurJeton++;
            }
            if (compteurJeton == nbreJetonsNecessaires) {
                return true;
            }
        }
        return false;
    }


    public static boolean verifDiagonales(Grid grille, Joueur joueur, int colonne, int ligne) {
        int colTest = colonne;
        int ligneTest = ligne;
        int compteurJeton = 1;
        boolean continueWhile = true;

        // verif première diagonale : / du bas à gauche du tableau vers le haut à droite du tableau

        while (colTest > 0 && ligneTest > 0 && continueWhile) {
            colTest--;
            ligneTest--;

            if (grille.cases[colTest][ligneTest] == joueur.id) {
                compteurJeton++;
            } else {
                continueWhile = false;
            }
        }

        continueWhile = true;
        colTest = colonne;
        ligneTest = ligne;
        while (colTest < nbreColonnes - 1 && ligneTest < nbreLignes - 1 && continueWhile) {
            colTest++;
            ligneTest++;
            if (grille.cases[colTest][ligneTest] == joueur.id) {
                compteurJeton++;
            } else {
                continueWhile = false;
            }
        }

        if (compteurJeton >= nbreJetonsNecessaires) {
            return true;
        }


        // verif deuxième diagonale : \ du haut à gauche du tableau vers le bas à droite du tableau
        compteurJeton = 1;
        continueWhile = true;
        colTest = colonne;
        ligneTest = ligne;
        while (colTest > 0 && ligneTest < nbreLignes - 1 && continueWhile) {
            colTest--;
            ligneTest++;
            if (grille.cases[colTest][ligneTest] == joueur.id) {
                compteurJeton++;
            } else {
                continueWhile = false;
            }
        }

        continueWhile = true;
        colTest = colonne;
        ligneTest = ligne;
        while (colTest < nbreColonnes - 1 && ligneTest > 0 && continueWhile) {
            colTest++;
            ligneTest--;
            if (grille.cases[colTest][ligneTest] == joueur.id) {
                compteurJeton++;
            } else {
                continueWhile = false;
            }
        }
        if (compteurJeton >= nbreJetonsNecessaires) {
            return true;
        }

        return false;
    }


    private static boolean verifGrillePleine() {
        return compteur == (nbreLignes * nbreColonnes + 1);
    }


    private static void finPartie(Joueur joueur) {
        System.out.println("La partie est terminée : " + joueur.name + " gagne");
    }


    private static void matchNul() {
        System.out.println("La grille est pleine, match nul");
    }


    private static boolean computerPlaying(Grid grille, Joueur joueur1, int colonneJoueur1, int ligneJoueur1, Joueur computer) {


        int ligneTestee;

        for (int i = 0; i < nbreColonnes; i++) {
            if (grille.cases[i][nbreLignes-1] != 0) continue;
                ligneTestee = determineLigne(grille, i);
                grille.setCases(i,ligneTestee,2);
                if (verifHorizontale(grille, computer, ligneTestee) || verifVerticale(grille, computer, i) || verifDiagonales(grille, computer, i, ligneTestee)) {
                    finPartie(computer);
                    return true;
                } else {
                    grille.setCases(i,ligneTestee,0);
                }
            }


        for (int i = 0; i < nbreColonnes; i++) {
            if (grille.cases[i][nbreLignes - 1] != 0) continue;
                ligneTestee = determineLigne(grille, i);
                grille.setCases(i,ligneTestee,1);
                if (verifHorizontale(grille, joueur1, ligneTestee) || verifVerticale(grille, joueur1, i) || verifDiagonales(grille, joueur1, i, ligneTestee)) {
                    grille.setCases(i, ligneTestee,2);
                    return false;
                } else {
                    grille.setCases(i,ligneTestee,0);
                }
            }


        for (int i = 0; i < nbreColonnes; i++) {
            if (grille.cases[i][nbreLignes - 1] == 0) {
                grille.setCases(i, determineLigne(grille,i), 2);

                return false;
            }
        }

        return false;
    }


}
