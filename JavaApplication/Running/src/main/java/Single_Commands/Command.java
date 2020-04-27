package Single_Commands;

import CommandController.Api_Controller;
import CommandController.Filewriter_Controller;
import CommandHandler.Settings_bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public abstract class Command {
    // message event
    protected MessageReceivedEvent event;
    // command constructor
    protected String help = this.HelpBuilder(new String[] {""}, new String[] {""}, new String[] {""},"","");
    protected String help_Discription;
    protected String commandName;
    protected ArrayList<Permission> Perms = new ArrayList<>();
    protected String[] Args;
    protected int Args_length;
    protected EmbedBuilder embedBuilder = new EmbedBuilder();
    // used in this file only
    protected Settings_bot settings;
    protected int[] arguments_numbers;
    protected boolean Github = false;
    protected boolean GithubFunction = false;
    protected Api_Controller githubapi;



    public Command() {
        // for initialization
        if(Settings_bot.getInstance().event != null){
            this.settings = Settings_bot.getInstance();
        }

    }






    /**
     * PreExecute is used for checking arumentents Needed before the command is executed.
     * so we can check for perms arguments, and if github is good registed.
     *
     */
    public void PreExecute(){
        Args = this.settings.base.Args;
        Args_length = this.settings.base.Args_length;
        event = this.settings.event;

        boolean argcheck = false;
        boolean Permcheck = false;
        boolean gitcheck = false;

        String Response = "";

        // checking if argument numbers are correct with the command that is given.
        if(arguments_numbers == null){
            argcheck = true;
        } else {
            for (int arguments_number : arguments_numbers) {
                if (Args_length == arguments_number) {
                    argcheck = true;
                }
            }
            if(!argcheck){
                Response = "Invalid number of Arguments. \nFor extra information type: !help " + this.commandName;
            }
        }

        // checking the users permission within discord for executing command
        if(this.Perms.size() == 0){
            Permcheck = true;
        }else {
            TextChannel tc = event.getTextChannel();
            if(Objects.requireNonNull(event.getMember()).hasPermission(tc, Perms)){
                Permcheck = true;
            } else {
                this.settings.base.getMsg().getMessage().delete().queue();
                Response = "you dont have " + Perms.toString().toLowerCase() + " Permissions to do this command!";
            }
        }
        // Github (needs to be used for any call that needs github api)
        // other checks needed otherwise can give errors.
        // note errors dont matter but they can be misleading.
        if(Github == true && Permcheck == true && argcheck == true){
            GithubFunction = true;
            JSONObject serverinfo = Settings_bot.getInstance().serverinfo;
            Filewriter_Controller filewriterController = new Filewriter_Controller();

            JSONObject gitinfo = filewriterController.getGitinfo(Settings_bot.getInstance().base.get_ServerID(), Settings_bot.getInstance().base.getauthername());
            if(!gitinfo.getJSONArray("repos").get(0).equals("") && !gitinfo.getString("authentication_key").equals("") &&!gitinfo.getString("username").equals(""))
            {
                // todo no repo arg given do all repos also counts for getissue
                String Repo = gitinfo.getString("username") + "/" + gitinfo.getJSONArray("repos").getString(0);
                githubapi = new Api_Controller(Repo, gitinfo.getString("authentication_key"));
                if(githubapi.check_login() != null){
                    gitcheck = true;
                } else {
                    Response = "your authentication key is probably inccorrect try !gitsetup again";
                }
            }
            else {
                Response = "your authentication key is probably not set try !gitsetup again \nelse conntact the developer. ";
            }
        } else {
            // if command.Github = false(Default) git check needs to be true
            gitcheck = true;
        }

        if ((Permcheck && argcheck && gitcheck) || (argcheck && gitcheck && event.getMember().isOwner())){

            // todo check if channel setting is true
            if(GithubFunction) {
                // used for setting sepperate issue channel.
                this.settings.changeExec();
                Thread thread = new Thread(this::execute);
                thread.start();
                while (this.settings.getexecuting()) {
                }
                // if delay is not here execute can be skiped
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gitchannel_update();
                GithubFunction = false;
            } else {
                execute();
            }
        // something is incorrect so send responce.
        } else {
            Settings_bot.getInstance().base.sendmessage(Response);
        }

    }
    public abstract void execute();
    public void gitchannel_update(){
        // new update_gitchannel().update();
    }



    public String getHelp() {
        return help;
    }

    public String getHelp_Discription(){return help_Discription; }

    public String getCommandName() {
        return commandName;
    }

    protected String HelpBuilder(String[] Argumenten, String[] args_pos, String[] Argumentdiscription, String Examplecommand, String General_disc){
        StringBuilder help = new StringBuilder("Argumenten: ");
        // all argumenten for discription.
        if(Argumenten.length != Argumentdiscription.length){
            System.out.println("error on argument lenght or argument discription");
            return null;
        }
        else {
            // todo general discr
            for (int i = 0; i < args_pos.length; i++) {
                String[] single_arg_poss = args_pos[i].split(" ");
                if (single_arg_poss.length == 0) {

                } else {
                    for (int j = 0; j < single_arg_poss.length; j++) {
                        help.append("<").append(single_arg_poss[j]).append("> ");
                        if (j == single_arg_poss.length - 1) {
                        }
                    }
                    if(i != args_pos.length -1) {
                        help.append("\\|| ");
                    }
                }

            }
            help.append("\n");
            for (int i = 0; i < Argumenten.length; i++) {
                help.append("<").append(Argumenten[i]).append(">: ").append(Argumentdiscription[i]).append("\n");
            }
            this.help_Discription = General_disc;
            return help.toString();
        }
    }
}
