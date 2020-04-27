package Single_Commands;

import CommandController.Filewriter_Controller;
import net.dv8tion.jda.api.Permission;
import org.json.JSONArray;
import org.json.JSONObject;

public class git_setup extends Command {
    private String[] Argumenten;
    public git_setup(){
        super();
        this.commandName = "GitSetup";
        this.Argumenten = this.Args;
        this.Perms.add(Permission.ADMINISTRATOR);
        this.arguments_numbers = new int[] {2,3};
        // todo option public repo
        this.help = this.HelpBuilder(
                new String[] {"Username", "Repository", "AuthenticationKey"},
                new String[] {"Username Repository AuthenticationKey"},
                new String[] {"your Github Username. this is needed for getting issues", "the Name off the repository", "A authentication Key(Note: this is not needed on a public repo.)"},
                "!GitSetup myusername myrepo privateauthenticaionkey",
                "this is needed for using any git related command for setting up. /n" +
                "Note: if authkey has been set the message with the command will be deleted out of safety."
        );
    }

    @Override
    public void execute() {
        Filewriter_Controller writer = new Filewriter_Controller();
        JSONObject file = new JSONObject(writer.read());
        JSONArray servers = file.getJSONArray("servers");
        for (int i = 0; i < servers.length(); i++) {
            if(this.settings.base.get_ServerID().equals(servers.getJSONObject(i).getString("server_ID"))) {
                JSONObject User = new JSONObject();
                JSONArray repos = new JSONArray().put(this.settings.base.Args[1]);
                User.put("username" , this.settings.base.Args[0]);
                User.put("authentication_key", this.settings.base.Args[2]);
                User.put("repos", repos);
                User.put("discord", this.settings.base.getMsg().getAuthor().getName());
                User.put("current", 1);
                servers.getJSONObject(i).getJSONArray("server_github").put(User);
            }
        }
        file.put("servers", servers);
        writer.write(file);
        this.settings.base.getMsg().getMessage().delete().queue();
        this.settings.base.sendmessage("changes have been applied");
    }
}
