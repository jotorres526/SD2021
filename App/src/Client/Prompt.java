package Client;

import Server.Stub;
import User.Location;
import User.User;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;

public class Prompt {
    private final Stub stub;

    public Prompt() {
        this.stub = new Stub();
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

    public void registar() {
        String user, pw, answer;
        boolean ret, privileged;
        Scanner s = new Scanner(System.in);
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
     * Display do menu de login
     * @return true caso tenha havido sucesso, false caso contrário
     */
    public String login (boolean sucLogin) {
        String user = null;
        if (!sucLogin) {
            Scanner s = new Scanner(System.in);
            String pw;
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
        return user;
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
     * @return true caso s corresponda não a um inteiro,
     * false caso contrário
     */
    public boolean isNotInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return true;
        }
        return false;
    }

    /**
     * Atualiza a localização de um User
     * @param user username
     */
    public void changeLoc(String user) { //fazer parse
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
            if (isNotInteger(locX) || isNotInteger(locY) || locX == null || locY == null)
                System.out.println("Os valores da localização são 2 e têm de ser inteiros...");
            else res = this.stub.changeLoc(user, locX, locY);
            if (res) System.out.println("Atualização bem sucedida!");
            else System.out.println("Essa localização é inválida. Relembre-se que a localização está entre (0,0) e (10,10)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Quantas pessoas estão numa dada localização
     * Consoante o resultado, o User pode decidir se se pretende deslocar
     * ou não
     * @param user username
     * @throws IOException exceção
     */
    public void howManyInLoc(String user) throws IOException {
        Scanner s = new Scanner(System.in);
        String[] loc;
        String answer, locX, locY;
        int number;
        boolean sn;
        System.out.println("Em que localização pretende saber o número de pessoas? (Responda p.ex: 1 1)");
        answer = s.nextLine();
        loc = answer.split(" ");
        locX = loc[0];
        locY = loc[1];
        if (isNotInteger(locX) || isNotInteger(locY) || locX == null || locY == null)
            System.out.println("Valores inválidos!");
        else {
            number = this.stub.howManyInLocation(locX, locY);
            if (number > 1) {
                System.out.println("Estão neste momento " + number + " pessoas em (" + locX + "," + locY + ")");
                System.out.println("Pretende deslocar-se para lá? (s/n)");
                answer = s.nextLine();
                if (answer.equals("s")) sn = this.stub.changeLoc(user, locX, locY);
            } else if (number == 1) System.out.println("Está neste momento 1 pessoa em (" + locX + "," + locY + ")");
            else if (number == 0) System.out.println("Não há de momento ninguém nessa localização...");
            else System.out.println("Essa localização não é válida!");
        }
    }

    public void loadMapa(boolean privileged, int n) throws IOException {
        if (privileged) {
            Map<Location, Collection<String>> map = this.stub.loadMap(privileged, n);
            for(Map.Entry<Location, Collection<String>> entry : map.entrySet()) {
                Collection<String> list = entry.getValue();
                System.out.print("Localização " + entry.getKey().toString() + ": ");
                if (list.isEmpty()) System.out.println("Vazia");
                for(String s : list)
                    System.out.println("\n - Utilizador: " + s);
            }
        } else System.out.println("Não tem permissões para carregar o mapa!");
    }

    public void display() throws IOException {
        boolean cont = true, sucLogin = false, privileged = true;
        User user = null;
        int limit = 1;
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
                case "registar" -> registar();
                case "login" -> {
                    if (!sucLogin) {
                        String pw, username;
                        System.out.print("Introduza o utilizador: ");
                        username = s.nextLine();
                        System.out.print("Introduza a password: ");
                        pw = s.nextLine();
                        if (stub.login(username, pw)) {
                            System.out.println("Autenticação bem sucedida!");
                            sucLogin = true;
                        } else {
                            System.out.println("O username ou a password não estão corretos...");
                        }
                    } else System.out.println("Já se encontra autenticado!");
                }
                case "atualizar localizacao" -> {
                    if (sucLogin)
                        changeLoc(user.getUsername());
                }
                case "quantas pessoas" -> {
                    if (sucLogin)
                        howManyInLoc(user.getUsername());
                }
                case "carregar mapa" -> {
                    if (sucLogin)
                        loadMapa(privileged, limit);
                }
                case "help" -> {
                    System.out.println("Lista de comandos:");
                    System.out.println(" - login                 -> Autenticação");
                    System.out.println(" - logout                -> Termina sessão do utilizador");
                    System.out.println(" - registar              -> Regista o utilizador na aplicação");
                    System.out.println(" - atualizar localizacao -> Altera a localização");
                    System.out.println(" - quantas pessoas       -> Diz quantas pessoas se encontram numa determinada localização");
                    System.out.println(" - carregar mapa         -> Carrega o Mapa com todas as localizações e utilizadores associados");
                    System.out.println(" - sair                  -> Fecha aplicação");
                }
                default -> System.out.println("Comando não encontrado. Escreva 'help' para uma lista de comandos.");
            }
        }
    }
}
