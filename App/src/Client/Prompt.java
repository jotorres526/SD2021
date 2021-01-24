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

    /**
     * Display de registo de um utilizador
     * caso contrário
     */
    public void register() {
        Scanner s = new Scanner(System.in);
        String user, pw, answer;
        boolean privileged, ret;
        System.out.println("Introduza o seu username: ");
        user = s.nextLine();
        System.out.println("Introduza a password: ");
        pw = s.nextLine();
        do {
            System.out.println("É um utilizador Premium? (s/n)");
            answer = s.nextLine();
        } while (!answer.equals("s") && !answer.equals("n"));
        privileged = answer.equals("s");
        ret = stub.register(user, pw, privileged);
        if (ret) System.out.println("Registo completo!");
        else System.out.println("Ups! Esse username já existe...");
    }

    public void changeLoc() { //por acabar
        Scanner s = new Scanner(System.in);
        String loc;
        System.out.println("Para que localização se pretende deslocar? (Responda p.ex: 1 1)");
        loc = s.nextLine();
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
                case "registar":
                    register();
                    break;
                case "login" :
                    boolean suc = login();
                    break;
                case "help" :
                    System.out.println("Lista de comandos:");
                    System.out.println(" - logout -> Termina sessão do utilizador");
                    System.out.println(" - registar -> Regista o utilizador na aplicação");
                    break;
                default :
                    System.out.println("Comando não encontrado. Escreva 'help' para uma lista de comandos.");
            }
        }
    }
}
