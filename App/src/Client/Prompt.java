package Client;

import Server.Stub;

import java.util.Scanner;

public class Prompt {
    private Stub stub;

    public Prompt() {
        stub = null;
    }

    public Prompt(Stub stub) {
        this.stub = stub;
    }

    /**
     * Display do menu de logout
     */
    public void logout () {
        System.out.println("Dando logout...");
    }

    /**
     * Display do menu de login
     * @return true caso tenha havido sucesso, false caso contrário
     */
    public boolean login () {
        Scanner s = new Scanner(System.in);
        String user, pw;
        System.out.print("Introduza o utilizador: ");
        user = s.nextLine();
        System.out.print("Introduza a password: ");
        pw = s.nextLine();
        return stub.login(user, pw);
    }

    public void display() {
        boolean cont = true;
        Scanner s = new Scanner(System.in);
        System.out.println("Conexão estabelecida! Escreva help caso necessite de ajuda...");
        while (cont) {
            System.out.print("> ");
            String input = s.nextLine();
            switch (input) {
                case "logout" :
                    logout();
                    cont = false;
                    break;
                case "login" :
                    boolean suc = login();
                    break;
                case "help" :
                    System.out.println("Lista de comandos:");
                    System.out.println(" - logout -> Termina sessão do utilizador");
                    break;
                default :
                    System.out.println("Comando não encontrado. Escreva 'help' para uma lista de comandos.");
            }
        }
    }
}
