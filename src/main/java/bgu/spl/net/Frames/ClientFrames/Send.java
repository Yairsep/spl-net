package bgu.spl.net.Frames.ClientFrames;

import bgu.spl.net.Frames.ServerFrames.Message;
import bgu.spl.net.srv.ConnectionsImpl;
import bgu.spl.net.srv.Library;

public class Send implements ClientFrame {

    private String topic;
    private String body;
    private String subscription;
    private String id;
    private ConnectionsImpl connections;

    public Send(String topic, String body) {
        this.topic = topic;
        this.body = body;
    }

    @Override
    public void execute(int connectionId, Library library) {
        Message response;
        response=new Message();
        String output=response.makeMessageFrame(this.subscription,this.topic,this.body);
        connections.send(topic,output);
    }



    @Override
    public void setConnections(ConnectionsImpl<ClientFrame> connections) {
        this.connections = connections;
    }
}
