/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import hangman.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Claudio Cusano <claudio.cusano@unipv.it>
 */
public class HangmanServer extends Player{

    /**
     * @param args the command line arguments
     */
    private ServerSocket server=null;
    private Socket socketClient=null;
    BufferedReader in;
    DataOutputStream out;
    BufferedReader keyboard;
    //DataInputStream inff;
    int porta;
    String gameRepresentation;

    public HangmanServer() {
        this.porta = 6789;
        waiting();
    }

    public boolean communicate(){
        try{
            String letto=in.readLine();
            System.out.println("--<"+ letto +'\n');
            System.out.println("--> ");
            keyboard=new BufferedReader(new InputStreamReader(System.in));
            String risposta=keyboard.readLine();
            out.writeBytes(risposta + "\n");
            if(risposta.equals("Chiudo")){
                return false;
            }
            else
                return true;
        }
        catch(IOException e){
            System.err.println(e.getMessage());
        }
        return false;
    }
    public String invia() throws IOException{
        String letto=in.readLine();
        return letto;

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

    public static void main(String argv[]) throws Exception {
        Hangman s  = new Hangman();
        Player player = new HangmanServer();
        // Player player = new ArtificialPlayer();
        s.playGame(player);

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
                try {
                    System.out.println("\n" + rem + " tentativi rimasti\n");
                    System.out.println(this.gameRepresentation(game));
                    System.out.println(game.getKnownLetters());
                    out.writeBytes("\n" + rem + " tentativi rimasti\n");
                    out.writeBytes(this.gameRepresentation(game));
                    out.writeBytes(game.getKnownLetters());
                }
                catch (IOException e){
                    System.err.println(e.getMessage());
                }
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
        gameRepresentation=s;
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
    @Override
    public char chooseLetter(Game game) {
        for (;;) {
            String line = null;
            try{
                System.out.println("Inserisci una lettera: ");
                out.writeBytes("Inserisci una lettera: ");
                line = invia();
            } catch (IOException e) {
                line = "";
            }
            if (line.length() == 1 && Character.isLetter(line.charAt(0))) {
                return line.charAt(0);
            } else {
                try {
                    System.out.println("Lettera non valida.");
                    out.writeBytes("Lettera non valida.");
                }
                catch (IOException e){
                    System.err.println(e.getMessage());
                }

            }
        }
    }
    
}
