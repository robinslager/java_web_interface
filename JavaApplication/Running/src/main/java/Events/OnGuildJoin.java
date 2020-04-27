package Events;

import CommandController.Filewriter_Controller;
import CommandHandler.Basics;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import org.json.JSONArray;
import org.json.JSONObject;

public class OnGuildJoin {
    public OnGuildJoin(GuildJoinEvent event){
        Basics helper = new Basics(null, event);
        String serverID = helper.get_ServerID();
        String serverName = helper.getGuildname();

        JSONObject file = new JSONObject(new Filewriter_Controller().read());
        JSONObject serverinfo = new JSONObject();
        JSONArray ServerSettings = new JSONArray();
        JSONObject message_on_join = newsetting("Message On User Join", false);
        JSONObject issuechannel= newsetting("sepparate Issue Channel", false);
        JSONObject settings_info = new JSONObject();


        ServerSettings.put(message_on_join);
        ServerSettings.put(issuechannel);
        serverinfo.put("server_name", serverName);
        serverinfo.put("server_ID", serverID);
        serverinfo.put("server_github", new JSONArray());
        serverinfo.put("server_pass", "");
        serverinfo.put("server_Settings", ServerSettings);
        serverinfo.put("Settings_Info", settings_info);


        file.getJSONArray("servers").put(serverinfo);
        new Filewriter_Controller().write(file);
        // todo chance for individual user to do things
    }
    private JSONObject newsetting(String settingstring, boolean value){
        JSONObject setting = new JSONObject();

        setting.put("Setting", settingstring);
        if(value) {
            setting.put("Value", "True");
        } else {
            setting.put("Value", "False");
        }
        return setting;
    }

}
