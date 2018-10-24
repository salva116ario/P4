public class Grid {

    int[][] cases;


    public Grid(int nbreColonnes, int nbreLignes) {
        this.cases = new int[nbreColonnes][nbreLignes];
    }

    public void setCases(int colonne, int ligne, int statutCase) {
        cases[colonne][ligne] = statutCase;
    }

    public int getCases(int colonne, int ligne) {
        return cases[colonne][ligne];
    }

}
