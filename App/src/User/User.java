package User;

import Server.Stub;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class User {
    private String username;
    private String password;
    private boolean privileged;
    // TODO: Encapsulamento
    private Location location;
    private boolean infected;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock wlock = lock.writeLock();
    private final Lock rlock = lock.readLock();

    public User(String username, String password, boolean privileged) {
        this.username = username;
        this.password = password;
        this.privileged = privileged;
        this.infected = false;
        this.location = new Location();
    }

    public Location getLocation() {
        try {
            rlock.lock();
            return new Location(this.location);
        }
        finally {
          rlock.unlock();
        }
    }

    public void setLocation(Location location) {
        try {
            wlock.lock();
            this.location = location.clone();
        } finally {
            wlock.unlock();
        }
    }

    public boolean isPrivileged() {
        return this.privileged;
    }

    public boolean isInfected() {
        return this.infected;
    }

    public void setInfected(boolean infected) {
        this.infected = infected;
    }

    /**
     * Retorna true se o User está atualmente numa localizacao e false caso não esteja
     * A variável loc serve para alcançar a localizacao atual que está na cauda
     * da lista de localizacoes do User
     * @param l localizacao
     * @return true se estiver na location, false caso contrário
     */
    public boolean isInLocation(Location l) {
        try {
            rlock.lock();
            boolean r = false;
            if (this.location.equals(l)) r = true;
            return r;
        }
        finally {
            rlock.unlock();
        }
    }

    /**
     * Verifica se a password corresponde à do User
     * @param pw password
     * @return true caso corresponda, false caso contrário
     */
    public boolean login(String pw) {
        try {
            rlock.lock();
            return this.password.equals(pw);
        } finally {
            rlock.unlock();
        }
    }

    /**
     * Verifica se uma dada localização é igual à mais atual
     * @param l localização
     * @return true caso seja igual, false caso contrário
     */
    public boolean locEquals(Location l) {
        return this.location.equals(l);
    }
}

