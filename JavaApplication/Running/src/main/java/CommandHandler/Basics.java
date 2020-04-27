package CommandHandler;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.kohsuke.github.GHIssue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class Basics {
    private MessageReceivedEvent msg = null;
    private GuildJoinEvent servr = null;

    public String[] getArgs() {
        return Args;
    }

    public int getArgs_length() {
        return Args_length;
    }

    public String[] Args;

    public int Args_length;

    public Basics(MessageReceivedEvent msg, GuildJoinEvent servr) {
        this.msg = msg;
        this.servr = servr;
        this.SetArguments();

    }

    public String getauthername()
    {
        if(msg != null){
            return msg.getAuthor().getName();
        }else {
            error("message");
        }
        return "";
    }

    public User get_user(){
        if(msg != null){
            return msg.getAuthor();
        } else  {
            return null;
        }
    }

    public MessageReceivedEvent getMsg() {
        if(msg != null){
        return msg;
        }else {
            error("message");
        }
        return null;

    }

    public String getmessage() {
        if(msg != null){
            return msg.getMessage().getContentRaw();
        }else {
            error("message");
        }
        return null;
    }



    public Guild get_guild(){
        if(servr != null) {
            return servr.getGuild();
        } else {
           return msg.getGuild();
        }
    }
    private void SetArguments(){
        String[] parts = msg.getMessage().getContentRaw().split(" ");
        if(Arrays.copyOfRange(parts, 1, parts.length).length == 0){
            Args = null;
            Args_length = 0;
        }else {
            Args = Arrays.copyOfRange(parts, 1, parts.length);
            Args_length = Args.length;
        }
    }

    public String get_ServerID(){
        return get_guild().getId();
    }

    public String getGuildname(){
        return get_guild().getName();
    }

    public void create_Issue_messge(List<GHIssue> issues, TextChannel channel) {
        for (GHIssue issue : issues) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("issue: " + issue.getTitle());
            builder.addField("ID:", "" + issue.getNumber(), true);
            builder.addBlankField(false);
            builder.addField("Description:", "" + issue.getBody(), false);
            builder.addField("State:", "" + issue.getState(), false);
            try {
                if (issue.getAssignee() == null) {
                    builder.addField("Assignee:", "unassigned", false);
                } else {
                    builder.addField("Assignee:", issue.getAssignee().getName(), false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            channel.sendMessage(builder.build()).queue();
        }
    }

    public void sendmessage(String textsend){
        if(msg != null){
            msg.getChannel().sendMessage(textsend).queue();
        } else {
            TextChannel channel = servr.getGuild().getDefaultChannel();
            channel.sendMessage(textsend).queue();
        }
    }
    public void sendmessage(MessageEmbed textsend){
        if(msg != null){
            msg.getChannel().sendMessage(textsend).queue();
        } else {
            TextChannel channel = servr.getGuild().getDefaultChannel();
            channel.sendMessage(textsend).queue();
        }
    }

    private void error(String var){
        log.println("Error " + var + " not set basics.java");
    }


//        return msg;
//    }

}
