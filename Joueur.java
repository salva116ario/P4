class Joueur {

    String name;
    char jeton;
    int id, lastColonne, lastLigne;


    public Joueur(int num, String nom) {
        this.id = num;
        this.name = nom;
        this.jeton = " O@".charAt(num);

    }


    public void setLastColonne(int colonne) {
        this.lastColonne = colonne;
    }

    public void setLastLigne(int ligne) {
        this.lastLigne = ligne;
    }



    @Override
    public String toString() {
        return "Joueur n°" + id + " : " + name + " (" + jeton +") ";
    }
}
