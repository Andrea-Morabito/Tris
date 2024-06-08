import java.util.Random;
public class Griglia {
    
    public String[][] matrice;
	private final int NUMERO_RIGHE_COLONNE = 3;
    private boolean controlloGiocatore = false, controlloBot = false;
	private Random rand = new Random();
	private int inserimenti = 0;

    public Griglia(){
        matrice = new String[NUMERO_RIGHE_COLONNE][NUMERO_RIGHE_COLONNE];
        for(int i = 0; i < NUMERO_RIGHE_COLONNE; i++){
			for(int j = 0; j < NUMERO_RIGHE_COLONNE; j++){
				matrice[i][j] = " ";
			}
		}
    }

    public String toString(){
        String s = "";
        for(int i = 0; i < NUMERO_RIGHE_COLONNE; i++){
			for(int j = 0; j < NUMERO_RIGHE_COLONNE; j++){
				if(j == 0){
					s = s + "| " + matrice[i][j];
				} else {
					s = s +  " | " + matrice[i][j];
				}
			}
			s = s + " |\n";
			if(i != 2)
			s = s + "|-----------|";
			s = s + "\n";
		}
		return s;
    }

    private int[] scegliCella(int cella){
		int[] posizioneCella = new int[]{0, 0};
		switch(cella){
			case 7:
				posizioneCella[0] = 0;
				posizioneCella[1] = 0;
			break;

			case 8:
				posizioneCella[0] = 0;
				posizioneCella[1] = 1;
			break;

			case 9:
				posizioneCella[0] = 0;
				posizioneCella[1] = 2;
			break;

			case 4:
				posizioneCella[0] = 1;
				posizioneCella[1] = 0;
			break;

			case 5:
				posizioneCella[0] = 1;
				posizioneCella[1] = 1;
			break;

			case 6:
				posizioneCella[0] = 1;
				posizioneCella[1] = 2;
			break;

			case 1:
				posizioneCella[0] = 2;
				posizioneCella[1] = 0;
			break;

			case 2:
				posizioneCella[0] = 2;
				posizioneCella[1] = 1;
			break;

			default:
				posizioneCella[0] = 2;
				posizioneCella[1] = 2;
			break;
		}
		return posizioneCella;
	}

    //Controlla che la cella esista
    private boolean esisteCella(int cella){
		boolean esisteCella = true;
		if((cella >= 10) || (cella <= 0)){
			esisteCella = false;
		}
		return esisteCella;
	}

    //Controlla che la cella sia libera
    public boolean isCellaLibera(int cella){
		int[] posizioneCella = scegliCella(cella);
		int riga = posizioneCella[0];
		int colonna = posizioneCella[1];
		if(esisteCella(cella) == true){
			if(matrice[riga][colonna] == " "){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

    //Inserisce i simboli
    public void inserimento(String simbolo, int cella){
        int[] posizioneCella = scegliCella(cella);
        int riga = posizioneCella[0];
        int colonna = posizioneCella[1];
        if (isCellaLibera(cella) == true) {
            matrice[riga][colonna] = simbolo;
			inserimenti++;
			System.out.println("Inserimenti: " + inserimenti);
		}
    }

    //Controllo orizzontale
    private boolean controlloOrizzonatale(){
        boolean flag = false;
        if ((matrice[0][0].equals("X") && matrice[0][1].equals("X") && matrice[0][2].equals("X")) || 
        (matrice[1][0].equals("X") && matrice[1][1].equals("X") && matrice[1][2].equals("X")) ||
        (matrice[2][0].equals("X") && matrice[2][1].equals("X") && matrice[2][2].equals("X"))){
            controlloGiocatore = true;
            flag = true;
        } else if ((matrice[0][0].equals("O") && matrice[0][1].equals("O") && matrice[0][2].equals("O")) || 
        (matrice[1][0].equals("O") && matrice[1][1].equals("O") && matrice[1][2].equals("O")) ||
        (matrice[2][0].equals("O") && matrice[2][1].equals("O") && matrice[2][2].equals("O"))){
            controlloBot = true;
            flag = true;
        }
        return flag;
    }

    //Controllo verticale
    private boolean controlloVerticale(){
        boolean flag = false;
        if ((matrice[0][0].equals("X") && matrice [1][0].equals("X") && matrice [2][0].equals("X")) ||
        (matrice[0][1].equals("X") && matrice[1][1].equals("X") && matrice[2][1].equals("X")) ||
        (matrice[0][2].equals("X") && matrice[1][2].equals("X") && matrice[2][2].equals("X"))){
            controlloGiocatore = true;
            flag = true;
        } else if ((matrice[0][0].equals("O") && matrice [1][0].equals("O") && matrice [2][0].equals("O")) ||
        (matrice[0][1].equals("O") && matrice[1][1].equals("O") && matrice[2][1].equals("O")) ||
        (matrice[0][2].equals("O") && matrice[1][2].equals("O") && matrice[2][2].equals("O"))){
            controlloBot = true;
            flag = true;
        }
        return flag;
    }

    //Controllo diagonale
    private boolean controlloDiagonale(){
        boolean flag = false;

		if((matrice[0][0].equals("X") && matrice[1][1].equals("X") && matrice[2][2].equals("X")) || 
        (matrice[0][2].equals("X") && matrice[1][1].equals("X") && matrice[2][0].equals("X"))){
			controlloGiocatore = true;
			flag = true;					
		} else if((matrice[0][0].equals("O") && matrice[1][1].equals("O") && matrice[2][2].equals("O")) || 
        (matrice[0][2].equals("O") && matrice[1][1].equals("O") && matrice[2][0].equals("O"))){					
			controlloBot = true;	
			flag = true;					
		}
		return flag;
    }

    public boolean controllo(){
        return controlloOrizzonatale() || controlloVerticale() || controlloDiagonale();
    }

    public String vincitore(){
        String vincitore = "";
        if (controlloGiocatore == true) {
            vincitore = "Hai vinto";
        } else if (controlloBot == true){
            vincitore = "Hai perso";
        }
        return vincitore;
    }

    //Bot
    public int interiRandom(){
		int random = rand.nextInt(9) + 1; 
		while(!isCellaLibera(random)){
			random = rand.nextInt(9) + 1; 
		}
		return random;
	}

    public boolean parita(){
        return inserimenti == 9;
    }

	public int inserimenti(){
		return inserimenti;
	}
}