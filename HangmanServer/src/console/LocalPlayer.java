/*
 * Code used in the "Software Engineering" course.
 *
 * Copyright 2017 by Claudio Cusano (claudio.cusano@unipv.it)
 * Dept of Electrical, Computer and Biomedical Engineering,
 * University of Pavia.
 */
package console;

import hangman.Player;
import hangman.Game;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Manage a player playing with the terminal.
 * 
 * @author Claudio Cusano <claudio.cusano@unipv.it>
 */
/*
public class LocalPlayer extends Player {
    
    BufferedReader console;
    ServerSocket  server=new ServerSocket(6789);
    Socket socketClient=server.accept();

        
    /**
     * Constructor.
     */
/*
    public LocalPlayer() {
        console = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
    }
    public Socket waiting(){
        try{
            System.out.println("[0]- Inizializzo il server...");
            server=new ServerSocket(porta);// inizializza il servizio
            System.out.println("[1]-Server pronto,in ascolto sulla porta: "+porta);
            // mi metto in ascolto sulla porta
            socketClient=server.accept();
            System.out.println("[2]-Connessione stabilita con un client");
            // evitiamo connessioni multiple
            server.close();
            in= new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            out=new DataOutputStream(socketClient.getOutputStream());
        }
        catch(IOException e){
            System.err.println(e.getMessage());
        }
        return socketClient;
    }
    
    @Override
    public void update(Game game) {
        switch(game.getResult()) {
            case FAILED:
                printBanner("Hai perso!  La parola da indovinare era '" +
                            game.getSecretWord() + "'");
                break;
            case SOLVED:
                printBanner("Hai indovinato!   (" + game.getSecretWord() + ")");
                break;
            case OPEN:
                int rem = Game.MAX_FAILED_ATTEMPTS - game.countFailedAttempts();
                System.out.print("\n" + rem + " tentativi rimasti\n");
                System.out.println(this.gameRepresentation(game));
                System.out.println(game.getKnownLetters());
                break;
        }
    }

    private String gameRepresentation(Game game) {
        int a = game.countFailedAttempts();
        
        String s = "   ___________\n  /       |   \n  |       ";
        s += (a == 0 ? "\n" : "O\n");
        s += "  |     " + (a == 0 ? "\n" : (a < 5
                ? "  +\n"
                : (a == 5 ? "--+\n" : "--+--\n")));
        s += "  |       " + (a < 2 ? "\n" : "|\n");
        s += "  |      " + (a < 3 ? "\n" : (a == 3 ? "/\n" : "/ \\\n"));
        s += "  |\n================\n";
        return s;
    }
    
    private void printBanner(String message) {
        System.out.println("");
        for (int i = 0; i < 80; i++)
            System.out.print("*");
        System.out.println("\n***  " + message);
        for (int i = 0; i < 80; i++)
            System.out.print("*");
        System.out.println("\n");
    }

    /**
     * Ask the user to guess a letter.
     * 
     * @param game
     * @return
     */
/*
    @Override
    public char chooseLetter(Game game) {
        for (;;) {
            System.out.print("Inserisci una lettera: ");
            String line = null;
            try {
                line = console.readLine().trim();
            } catch (IOException e) {
                line = "";
            }
            if (line.length() == 1 && Character.isLetter(line.charAt(0))) {
                return line.charAt(0);
            } else {
                System.out.println("Lettera non valida.");
            }
        }
    }
}
*/