package External_functions;

import CommandController.Api_Controller;
import CommandController.Filewriter_Controller;
import CommandHandler.Settings_bot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueState;

import java.util.Collections;
import java.util.List;

public class update_gitchannel {
    public void update() {
        TextChannel channel = null;
        Api_Controller githubapi = null;
        Filewriter_Controller writer = new Filewriter_Controller();
        JSONObject file = new JSONObject(writer.read());
        JSONArray servers = file.getJSONArray("servers");
        for (int i = 0; i < servers.length(); i++) {
            if (Settings_bot.getInstance().base.get_ServerID().equals(servers.getJSONObject(i).getString("server_ID"))) {
                String ChannelID = servers.getJSONObject(i).getJSONObject("Settings_Info").getString("channelID");
                channel = Settings_bot.getInstance().event.getJDA().getTextChannelById(ChannelID);
                JSONObject serverinfo = Settings_bot.getInstance().serverinfo;
                Filewriter_Controller filewriterController = new Filewriter_Controller();
                clear(channel);
                JSONObject gitinfo = filewriterController.getGitinfo(Settings_bot.getInstance().base.get_ServerID(), Settings_bot.getInstance().base.getauthername());
                if (!gitinfo.getJSONArray("repos").get(0).equals("") && !gitinfo.getString("authentication_key").equals("") && !gitinfo.getString("username").equals("")) {
                    // todo no repo arg given do all repos also counts for getissue
                    String Repo = gitinfo.getString("username") + "/" + gitinfo.getJSONArray("repos").getString(0);
                    githubapi = new Api_Controller(Repo, gitinfo.getString("authentication_key"));

                }
                List<GHIssue> issues = githubapi.getissues(GHIssueState.ALL);
                if (issues != null) {
                    Collections.reverse(issues);
                    Settings_bot.getInstance().base.create_Issue_messge(
                            issues, channel
                    );
                }

            }

        }
    }
    public void clear (TextChannel channel) {
        MessageHistory history = new MessageHistory(channel);
        List<Message> msgs;
        try {
            while (true) {
                msgs = history.retrievePast(1).complete();
                msgs.get(0).delete().queue();
            }
        } catch (Exception ex) {

        }
    }
}


