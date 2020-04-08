package Single_Commands;

import CommandController.Filewriter_Controller;
import External_functions.JavaClassLoader;
import net.dv8tion.jda.api.EmbedBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

public class Settings extends Command {
    public Settings() {
        this.commandName = "Settings";
        this.arguments_numbers = new int[] {0,2};
    }

    @Override
    public void execute() {
        if(this.settings.base.Args_length == 0){
            Filewriter_Controller writer = new Filewriter_Controller();
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Settings");
            builder.setDescription("to change a setting type: !settings <number of setting> <Value>");
            JSONArray settings = writer.getServerSettings(this.settings.base.get_ServerID());
            for (int i = 0; i < settings.length(); i++) {
                builder.addField(i+1 + " " + settings.getJSONObject(i).getString("Setting"), settings.getJSONObject(i).getString("Value"), false );
            }
            this.settings.base.sendmessage(builder.build());
        } else {
            Filewriter_Controller writer = new Filewriter_Controller();
            JSONArray settings = writer.getServerSettings(this.settings.base.get_ServerID());
            for (int i = 0; i < settings.length(); i++) {
                if(i+1 == Integer.parseInt(this.settings.base.Args[0])){
                    // setting is chosen setting from here.
                    JSONObject setting = settings.getJSONObject(i);
                    if(!setting.getString("Value").toLowerCase().equals(this.settings.base.Args[1].toLowerCase())){
                        // todo execute
                        System.out.println("check");
                        JavaClassLoader javaClassLoader = new JavaClassLoader();
                        javaClassLoader.invokeClassMethod(settings.getJSONObject(i).getString("Setting"));
                        this.settings.base.sendmessage(settings.getJSONObject(i).getString("Setting") + " has been changed to " +  this.settings.base.Args[1]);
                    }
                }
            }
        }
    }
}
