package data;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * This class is intented to translate the input string we get to a json object.
 */
public class InputDTO {
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
        return usrInput;
    }

    //TODO: Transform the string into a JSON object that you use when you initilize the cards
}
