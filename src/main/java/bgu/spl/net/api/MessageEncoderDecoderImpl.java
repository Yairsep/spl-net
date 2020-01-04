package bgu.spl.net.api;
import bgu.spl.net.Frames.ClientFrames.*;
import bgu.spl.net.Frames.Frames;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class MessageEncoderDecoderImpl<T> implements MessageEncoderDecoder<T> {

    private byte[] bytes =new byte[1<<10];
    private int len=0;



    @Override
    public T decodeNextByte(byte nextByte) {
        String nextStringByte= String.valueOf(nextByte);
        if(nextStringByte=="\n"){
            return (T)popString();
        }
        pushByte(nextByte);
        return null;


    }

    @Override
    public byte[] encode(T message) {
        //@TODO CHECK ABOUT CASES
        return ((String)message).getBytes();
    }


    public void pushByte(byte nextbyte){
        if(len>=bytes.length){
            bytes= Arrays.copyOf(bytes,len*2);
        }
        bytes[len++]=nextbyte;
    }

    private Frames popString(){
        String result=new String(bytes,0,len,StandardCharsets.UTF_8);
        len=0;
        return makeFrame(result);
    }
    private Frames makeFrame(String result){
        String [] output = result.split(System.lineSeparator());
        Frames outputFrame = null;


        switch (output[0]){
            case "CONNECT":
                outputFrame = new Connect(output[3].substring(6),output[4].substring(9),output[2].substring(6),output[1].substring(15));
                break;
            case "DISCONNECT":
                outputFrame=new Disconnect(output[1].substring(5));
                break;
            case "UNSUBSCRIBE":
                outputFrame= new Unsubscribe(output[1].substring(12),output[2].substring(3),output[3].substring(8));
                break;
            case "SUBSCRIBE":
                outputFrame=new Subscribe(output[1].substring(12),output[2].substring(3),output[3].substring(8));
            case "SEND":
                outputFrame = new Send();
                break;
        }

        return outputFrame;
    }



}

