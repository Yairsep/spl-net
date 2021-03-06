package bgu.spl.net.srv;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Library {

    public static ConcurrentHashMap<String, ConcurrentLinkedQueue<User>> SubscribersToTopicsMap;
    public static ConcurrentHashMap<String, User> allUsers;
    public static ConcurrentHashMap<Integer, User> connectionIdMap;
    private AtomicInteger userId=new AtomicInteger(0);


    public int getUserId(){
        return userId.getAndIncrement();
    }



    public Library() {
        this.SubscribersToTopicsMap = new ConcurrentHashMap<>();
        this.allUsers = new ConcurrentHashMap<>();
        this.connectionIdMap = new ConcurrentHashMap<>();
    }


    //Getters

    public User getUser(String userName){
        return allUsers.get(userName);
    }

    public ConcurrentHashMap<String, ConcurrentLinkedQueue<User>> getSubscribersToTopicsMap() {
        return SubscribersToTopicsMap;
    }


    public ConcurrentHashMap<String, User> getAllUsers() {
        return allUsers;
    }

    public void setUserByTopic(User newUser,String topic){
        if(SubscribersToTopicsMap.get(topic)!=null) {
            SubscribersToTopicsMap.get(topic).add(newUser);
        }
        else {
            ConcurrentLinkedQueue<User> temp = new ConcurrentLinkedQueue<>();
            temp.add(newUser);
            SubscribersToTopicsMap.put(topic,temp);
        }
    }

    public ConcurrentLinkedQueue<User> getUsersByTopic (String topic){
        return SubscribersToTopicsMap.get(topic);
    }


    public ConcurrentHashMap<Integer, User> getConnectionIdMap() {
        return connectionIdMap;
    }
    //Setters


}
