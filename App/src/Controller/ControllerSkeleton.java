package Controller;

import User.Location;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Collection;
import java.util.Map;

/**
 * A classe ControllerSkeleton serve como um middleware que decide a qual dos
 * "Controllers" recorrer, dependendo da query que está a ser executada.
 * Neste momento, como só foi implementado um Controller, a escolha desta classe será
 * simples, mas, eventualmente, será necessário criar novos Controllers que armazenam
 * diferentes objetos e aí irá ser posta em prática a utilidade desta classe.
 * Por isso, esta classe está preparada para uma eventual expansão de código.
 */
public class ControllerSkeleton implements Skeleton {
    private final UsersController userscontroller;
    private final RegisterUsers regUsers;

    public ControllerSkeleton(UsersController controller, RegisterUsers regUsers) {
        this.userscontroller = controller;
        this.regUsers = regUsers;
    }

    @Override
    public void handle(DataInputStream dis, DataOutputStream dos) throws Exception {
        int limit = 5;
        boolean cont = true;
        while (cont) {
            switch (dis.readUTF()) {
                /*
                 * Registo de um User:
                 * Primeiro, introduz-se o username, password e autorização especial
                 * De seguida, o UsersController verifica se mais algum User tem
                 * o mesmo username e, em caso afirmativo, não permite o registo do
                 * novo User. Caso o username não exista, insere-o no Map do UsersController
                 * e cria uma entrada no Map do RegisterUsers.
                 */
                case "register":
                    String name = dis.readUTF();
                    String pw = dis.readUTF();
                    boolean privileged = dis.readBoolean();
                    boolean suc = this.userscontroller.register(name, pw, privileged);
                    if (suc) this.regUsers.createEntry(name);
                    dos.writeBoolean(suc);
                    dos.flush();
                    break;
                /*
                 * Autenticação de um User:
                 * O UsersController verifica se o username existe e, em caso afirmativo,
                 * verifica se a password está correta. Se ambos estiverem corretos,
                 * o processo de autenticação é validado. Caso o username não exista
                 * ou a password esteja incorreta, a autenticação não é validada.
                 */
                case "login":
                    String username = dis.readUTF();
                    String password = dis.readUTF();
                    boolean success = this.userscontroller.login(username, password);
                    dos.writeBoolean(success);
                    dos.flush();
                    break;
                /*
                 * Mudar Localização:
                 * Começa por receber a localização que o utilizador pretende
                 * De seguida adiciona-a à cabeça da lista de localizações do
                 * User, passando a ser a localização mais atual
                 */
                case "change location":
                    String n = dis.readUTF();
                    String locX = dis.readUTF();
                    String locY = dis.readUTF();
                    Location loc = new Location(locX, locY);
                    boolean r = this.userscontroller.setLocalizacao(n, loc, limit);
                    if (r) { //localização inválida ou user não existe
                        Collection<String> registo = this.userscontroller.getNewRegUsers(loc);
                        this.regUsers.createNewRegisters(registo);
                    }
                    dos.writeBoolean(r);
                    dos.flush();
                    break;
                /*
                 * Quantas pessoas estão numa determinada localização:
                 * Retorna o número de pessoas numa localização
                 */
                case "how many people in location":
                    String x = dis.readUTF();
                    String y = dis.readUTF();
                    Location l = new Location(x,y);
                    int number = -1;
                    if (l.isInLimit(limit)) number = this.userscontroller.getNumberInLoc(new Location(x, y));
                    dos.writeInt(number); //-1 caso a localização seja inválida, >0 caso contrário
                    dos.flush();
                    break;
                //TODO terminar query de comunicar infeção
                /*
                 * Um User comunica que está infetado. A partir daí, o seu thread
                 * é bloqueado e todos os Users que estiveram em contacto com ele
                 * são notificados de tal ocorrência
                 */
                case "communicate infection": //para além de comunicar, avisar todos os users que já tiveram na loc do User
                    //Collection<String> listPotInfected = this.regUsers.getListUser(u);
                    break;
                /*
                 * Carregar o mapa:
                 * Retorna a lista de todas as localizações (livres e ocupadas) e,
                 * quando estão ocupadas por um ou mais Users, apresenta-os
                 */
                case "loadmap":
                    int num = dis.readInt();
                    Map<Location, Collection<String>> map = this.userscontroller.loadMap(num);
                    for (Map.Entry<Location, Collection<String>> entry : map.entrySet()) {
                        dos.writeBoolean(true);
                        dos.writeUTF(entry.getKey().getCoordX());
                        dos.writeUTF(entry.getKey().getCoordY());
                        dos.writeInt(entry.getValue().size());
                        for (String user : entry.getValue())
                            dos.writeUTF(user);
                    }
                    dos.writeBoolean(false);
                    dos.flush();
                    break;
                case "is privileged":
                    String user = dis.readUTF();
                    dos.writeBoolean(this.userscontroller.isUserPrivileged(user));
                    dos.flush();
                    break;
                /*
                 * Condição de saída
                 */
                case "exit":
                    cont = false;
                    break;
            }
        }
    }
}
