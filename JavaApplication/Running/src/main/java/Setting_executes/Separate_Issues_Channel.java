package Setting_executes;

import CommandController.Filewriter_Controller;
import CommandHandler.Settings_bot;
import External_functions.update_gitchannel;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Separate_Issues_Channel {
    public Settings_bot settingsBot = Settings_bot.getInstance();
    public void Execute() {
        ChannelAction<Category> channelAction = settingsBot.base.get_guild().createCategory("github");
        channelAction.queue();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        createchannel();
    }

    public void createchannel(){
        String id = "";
        ChannelAction<TextChannel> channel = settingsBot.base.get_guild().getCategoriesByName("github", true).get(0).createTextChannel("Issue\\`s");
        ArrayList<Permission> collection_A = new ArrayList<>();
        collection_A.add(Permission.MESSAGE_READ);
        ArrayList<Permission> collection_D = new ArrayList<>();
        collection_D.add(Permission.MESSAGE_WRITE);
        channel.addPermissionOverride(settingsBot.base.get_guild().getPublicRole(), collection_A, collection_D).queue();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        List<GuildChannel> guildChannels = Settings_bot.getInstance().base.get_guild().getChannels();
        for (int i = 0; i < guildChannels.size(); i++) {
            if(guildChannels.get(i).getName().toLowerCase().equals("issues")){
                 id = guildChannels.get(i).getId();
            }
        }
        System.out.println(id);
        Filewriter_Controller filewriterController = new Filewriter_Controller();

        JSONObject file = new JSONObject(filewriterController.read());
        JSONArray servers = file.getJSONArray("servers");
        for (int i = 0; i < servers.length(); i++) {
            if(Settings_bot.getInstance().base.get_ServerID().equals(servers.getJSONObject(i).getString("server_ID"))) {
                // setting info
                JSONObject settinginfo = filewriterController.getSettingsInfo(Settings_bot.getInstance().base.get_ServerID());
                // channel ID
                settinginfo.put("channelID", id + "");
                JSONObject serverinfo = filewriterController.getServerInfo(Settings_bot.getInstance().base.get_ServerID());
                // replace setting info
                serverinfo.remove("Settings_Info");
                serverinfo.put("Settings_Info", settinginfo);
                servers.remove(i);
                servers.put(serverinfo);
            }
        }
        file.put("servers", servers);
        filewriterController.write(file);
        new update_gitchannel().update();
    }
}
