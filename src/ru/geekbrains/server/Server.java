package ru.geekbrains.server;

import ru.geekbrains.common.ServerConst;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server implements ServerConst{
    private Vector<ClientHandler> clients; //тип списка "вектор"(устаревший),который со но вообще не стоит его использовать
    // потому что все его методы сынхонайз, что замедляет приложение. Когда один поток делает что то с членом "вектора".
    // ни один другой поток работаеть не будет в это время. Но зато будет полная потокобезопасность.

    private AuthService authService;
    public AuthService getAuthService(){ //конструктор для автаризации
        return authService;
    }
    public Server(){                         //конструктор
        ServerSocket serverSocket = null;
        Socket socket = null;
        clients = new Vector<>();  //инициализируем вектор
        try{
            serverSocket = new ServerSocket(PORT);
            authService = new BaseAuthService();
            authService.start(); //placeholder
            System.out.println("Сервер запущен, ждем клиентов");
            while(true){
                socket = serverSocket.accept(); //ждем подключений, сервер становится на паузу
                clients.add(new ClientHandler(this, socket)); //добавляем клиента
                System.out.println("Клиент подключился");
            }
        }catch(IOException e){
            System.out.println("Ошибка инициализации");
        }finally{
            try{
                serverSocket.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    public void broadcast(String message){ //отправляем всем клиентам сообщение
        for(ClientHandler client : clients){ //проходим по массиву клиентов
            client.sendMessage(message);
        }
    }
    public void unsubscribeMe(ClientHandler c){ //если клиент вышел из чата, то удаляем его из списка
        clients.remove(c);
    }
}