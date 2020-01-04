package bgu.spl.net.srv;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConnectionsImpl<T> implements Connections<T> {

    private ConcurrentHashMap<Integer, ConnectionHandler<T>> connectionsById;
    private Library library;


    public ConnectionsImpl() {
        connectionsById = new ConcurrentHashMap<>();
    }

    public boolean send(int connectionId, T msg) {
        ConnectionHandler<T> temp=connectionsById.get(connectionId);
        if(temp!=null){
            temp.send(msg);
            return true;
        }
        return false;
    }

    @Override
    public void send(String topic, T msg) {
        ConcurrentLinkedQueue<User> byTopic=library.SubscribersToTopicsMap.get(topic);
        for (User user:byTopic) {
            int tempId=user.getId();
            connectionsById.get(tempId).send(msg);
        }
    }

    @Override
    public void disconnect(int connectionId) {
        connectionsById.remove(connectionId);

    }
}
