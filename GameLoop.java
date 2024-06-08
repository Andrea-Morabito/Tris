import java.io.IOException;
import java.util.Scanner;

public class GameLoop {

    private boolean isGameRunning;
    private Griglia griglia;
    private Thread gameThread;
    private Scanner key = new Scanner(System.in);
    private final String CERCHIO = "O";
	private final String CROCE = "X";
    
    public GameLoop(){
        isGameRunning = false;
        griglia = new Griglia();
    }

    public void run() {
        isGameRunning = true;
        gameThread = new Thread(this::processGameLoop);
        gameThread.start();
    }

    public void stop() {
        isGameRunning = false;
    }

    //Modalità di gioco
    private void introduzione(){
		System.out.println("TRIS GAME");
		System.out.println("Modalita' di gioco: inserisci la posizione del simbolo seguendo il tastierino numerico della tastiera");
        System.out.println("  7 | 8 | 9 ");
        System.out.println(" -----------");
        System.out.println("  4 | 5 | 6 ");
        System.out.println(" -----------");
        System.out.println("  1 | 2 | 3 ");
        System.out.println();
	}

    private void render() {
        griglia.toString();
    }

    //Controllo cella vuota e inserimento
    private void turnoGiocatore(int posizione){
		while(!griglia.isCellaLibera(posizione)){
			System.out.println("Hai inserito una cella gia' occupata, Reinserisci");
			posizione = key.nextInt();
		}
		griglia.inserimento(this.CROCE, posizione);
	}

    private void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("Non si riesce a cancellare la console");
            System.out.println(e.getStackTrace());
        }
    } 

    //Scelta avversario (bot) e inserimento
    private void turnoBot() {
        if (griglia.inserimenti() < 9) {
            griglia.inserimento(this.CERCHIO, griglia.interiRandom());   
        }
	}

    //Scelta giocatore e inserimento
    private void processInput() {
        System.out.println(griglia.toString());
        System.out.println("Inserisci il numero del tastierino numerico corrispondente alla casella");
		int posizione = key.nextInt();
		turnoGiocatore(posizione);
        if(griglia.controllo() == false){
            turnoBot();
        }
		clearConsole();
    }

    //Resoconto
    private void update() {
        if (griglia.controllo()) {
            System.out.println(griglia.toString());
		    System.out.println(griglia.vincitore());
            this.stop();
        } else if (griglia.parita()){
            System.out.println(griglia.toString());
            System.out.println("Parita'");
            this.stop();
        }
	}

    //Processi
    private void processGameLoop() {
        introduzione();
        while (isGameRunning) {
		    render();
            processInput();
            update();
        }
    }

}