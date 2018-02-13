package ru.geekbrains.server;

import java.util.ArrayList;

public class BaseAuthService implements AuthService{
    private class Entry{              //фейковый вложенный класс(для замены базы данных)
        private String login;
        private String pass;
        private String nick;
        public Entry(String login, String pass, String nick){
            this.login = login;
            this.pass = pass;
            this.nick = nick;
        }
    }
    private ArrayList<Entry> entries;
    public BaseAuthService(){     //конструктор
        this.entries = new ArrayList<>();  //список объектов
        entries.add(new Entry("login1", "pass1", "Rick"));
        entries.add(new Entry("login2", "pass2", "Morty"));
        entries.add(new Entry("login3", "pass3", "Bet"));
    }
    @Override
    public void start(){
    }
    @Override
    public void stop(){
    }
    @Override
    public String getNickByLoginPass(String login, String pass){ //проверяем правильность логина и пароля
        for(Entry entry : entries){
            if(entry.login.equals(login) && entry.pass.equals(pass)) return entry.nick;
        }
        return null;
    }
}
