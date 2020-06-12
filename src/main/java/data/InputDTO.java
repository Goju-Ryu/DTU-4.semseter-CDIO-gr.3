package data;

import com.google.gson.Gson;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * This class is intented to translate the input string we get to a json object.
 */
public class InputDTO {
    private Gson g = new Gson();

    public String getUsrInput(){
        String usrInput ="";
        String target = "localhost:50051";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build();
        InputAccesPoint accessInput = new InputAccesPoint(channel);
        try{
            usrInput = accessInput.getInput("ManGUI");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        usrInput = stringToJson(usrInput);
        return usrInput;
    }

    public String stringToJson(String toJson){
        String str = g.toJson(toJson);
        return str;
    }


    //TODO: Transform the string into a JSON object that you use when you initilize the cards
}
