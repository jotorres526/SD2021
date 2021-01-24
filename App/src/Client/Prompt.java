package Client;

import Server.Stub;

import java.io.IOException;
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
    public void exit () {
        System.out.println("Dando logout...");
    }

    /**
     * Display do menu de login
     * @return true caso tenha havido sucesso, false caso contrário
     */
    public boolean login (boolean sucLogin) {
        if (!sucLogin) {
            Scanner s = new Scanner(System.in);
            String user, pw;
            boolean res;
            System.out.print("Introduza o utilizador: ");
            user = s.nextLine();
            System.out.print("Introduza a password: ");
            pw = s.nextLine();
            res = stub.login(user, pw);
            if (res) System.out.println("Autenticação bem sucedida!");
            else System.out.println("O username ou a password não estão corretos...");
            sucLogin = true;
        } else System.out.println("Já se encontra autenticado!");
        return sucLogin;
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

    /**
     * Um User sai da sua conta de utilizador
     * @param sucLogin true caso o User se tenha autenticado, false
     *                 caso contrário
     * @return false caso o logout tenha sido bem sucedido, true caso
     * o User não tenha estado autenticado e por isso não conseguirá
     * fazer logout
     */
    public boolean logout(boolean sucLogin) {
        if (sucLogin) {
            System.out.println("Saindo...");
            sucLogin = false;
        }
        else System.out.println("De momento, não se encontra autenticado...");
        return sucLogin;
    }

    /**
     * Verifica se uma String corresponde a um inteiro
     * @param s String
     * @return true caso s corresponda a um inteiro,
     * false caso contrário
     */
    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * Atualiza a localização de um User
     * @param user username
     * @return
     */
    public boolean changeLoc(String user) { //fazer parse
        boolean res = false;
        try {
            Scanner s = new Scanner(System.in);
            String[] loc;
            String answer, locX, locY;
            System.out.println("Para que localização se pretende deslocar? (Responda p.ex: 1 1)");
            answer = s.nextLine();
            loc = answer.split(" ");
            locX = loc[0];
            locY = loc[1];
            if (!isInteger(locX) || !isInteger(locY) || loc[2] != null) System.out.println("Valores inválidos!");
            else {
                res = this.stub.changeLoc(user, locX, locY);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void display() {
        boolean cont = true, sucLogin = false;
        Scanner s = new Scanner(System.in);
        System.out.println("Conexão estabelecida! Escreva help caso necessite de ajuda...");
        while (cont) {
            System.out.print("> ");
            String input = s.nextLine();
            switch (input) {
                case "logout" -> sucLogin = logout(sucLogin);
                case "sair" -> {
                    exit();
                    cont = false;
                }
                case "registar" -> register();
                case "login" -> sucLogin = login(sucLogin);
                case "mudar localizacao" -> {
                    //boolean r = changeLoc();
                }
                case "help" -> {
                    System.out.println("Lista de comandos:");
                    System.out.println(" - login             -> Autenticação");
                    System.out.println(" - logout            -> Termina sessão do utilizador");
                    System.out.println(" - registar          -> Regista o utilizador na aplicação");
                    System.out.println(" - mudar localizacao -> Altera a localização");
                    System.out.println(" - sair              -> Fecha aplicação");
                }
                default -> System.out.println("Comando não encontrado. Escreva 'help' para uma lista de comandos.");
            }
        }
    }
}
