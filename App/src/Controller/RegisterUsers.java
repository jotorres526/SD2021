package Controller;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Classe RegisterUsers
 * Armazena o conjunto de utilizadores que já estiveram na mesma localização
 * de qualquer utilizador. Se um utilizador ainda não partilhou a sua
 * localização com nenhum outro, a lista de utilizadores que lhe está associada
 * é vazia.
 */
public class RegisterUsers {
    private final Map<String, Set<String>> map;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock rlock = lock.readLock();
    private final Lock wlock = lock.writeLock();

    public RegisterUsers() {
        this.map = new HashMap<>();
    }

    public void addUserToList(String user, String newUser) {
        try {
            wlock.lock();
            Collection<String> list = this.map.get(user);
            if (list != null) list.add(newUser);
        } finally {
            wlock.unlock();
        }
    }

    public void createEntry(String user) {
        try {
            wlock.lock();
            this.map.put(user, new TreeSet<>());
        } finally {
            wlock.unlock();
        }
    }

    /**
     * Retorna a lista de todos os User que já entraram em contacto
     * com um determinado User
     * @param user username de um determinado User
     * @return lista de usernames que já entraram em contacto com o user
     */
    public Collection<String> getListUser(String user) {
        try {
            rlock.lock();
            Collection<String> col = new ArrayList<>();
            Collection<String> list = this.map.get(user);
            if (list != null) col = new ArrayList<>(list);
            return col;
        } finally {
            rlock.unlock();
        }
    }

    /*
    public boolean existsKeyUser(String user) { //inútil
        try {
            rlock.lock();
            return this.map.get(user) != null;
        } finally {
            rlock.unlock();
        }
    }*/

    /*
    public boolean addUser(String user) { //inútil
        try {
            wlock.lock();
            boolean success = false;
            if (!existsKeyUser(user)) {
                this.map.put(user, new TreeSet<>());
                success = true;
            }
            return success;
        } finally {
            wlock.unlock();
        }
    }*/

    /**
     * Quando um User muda de localização, poderá entrar em contacto com
     * novos Users, e por isso é necessário que todos esses registos sejam
     * atualizados. Por iss, cada username é adicionado nos registos de
     * todos os outros usernames. Por exemplo, numa lista [A,B,C,D],
     * o username A seria adicionado ao username B,C e D; e o mesmo processo
     * aconteceria para os restantes elementos da lista
     * @param list lista de Users que estão numa certa localização
     */
    public void createNewRegisters(Collection<String> list) {
        try {
            wlock.lock();
            for (String s : list) {
                Set<String> set = this.map.get(s);
                for (String user : list)
                    if (!user.equals(s)) set.add(user);
            }
        } finally {
            wlock.unlock();
        }
    }

}
