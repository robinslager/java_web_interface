package CommandHandler;

import CommandController.Filewriter_Controller;
import Single_Commands.Command;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Settings_bot {
    // static variable single_instance of type Singleton
    private static Settings_bot single_instance = null;

    // variable of type String
    public MessageReceivedEvent event;
    public Basics base;
    public JSONObject serverinfo;
    public ArrayList<Command> commands = new ArrayList<>();
    public Command command;
    public EventWaiter waiter;

    public boolean executing = false;

    // private constructor restricted to this class itself
    private Settings_bot()
    {

    }
    public void changeExec(){
        if(executing == false){
            executing = true;
        } else {
            executing = false;
        }
    }

    public boolean getexecuting(){
        return executing;
    }

    public void setMessageEvent(MessageReceivedEvent event) {
        this.event = event;
        setBase();
        setServerinfo();
    }
    private void setBase(){
        this.base = new Basics(this.event, null);
    }

    private void setServerinfo(){
        Guild guild;
        try {
            guild = base.get_guild();
        } catch (IllegalStateException e){
            guild = null;
        }
        if(guild != null) {
            JSONObject serverdata = new JSONObject(new Filewriter_Controller().read());
            JSONArray servers = serverdata.getJSONArray("servers");
            for (int i = 0; i < servers.length(); i++) {
                JSONObject server_data = servers.getJSONObject(i);
                if (server_data.get("server_ID").toString().equals(base.get_ServerID())) {
                    serverinfo = server_data;
                }
            }
        }
    }

    // static method to create instance of Singleton class
    public static Settings_bot getInstance()
    {
        if (single_instance == null)
            single_instance = new Settings_bot();

        return single_instance;
    }

//    public static Settings_bot getInstance(Object command)
//    {
//        if (single_instance == null && Bot_Command.class.isAssignableFrom(command.getClass()))
//            single_instance = new Settings_bot();
//
//        return single_instance;
//    }

}