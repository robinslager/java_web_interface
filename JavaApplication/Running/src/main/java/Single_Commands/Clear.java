package Single_Commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;

import java.util.List;

public class Clear extends Command {
    public Clear() {
        this.commandName = "Clear";
        this.arguments_numbers = new int[] {0};
        this.Perms.add(Permission.MESSAGE_MANAGE);
        this.help = this.HelpBuilder(new String[] {""}, new String[] {""}, new String[] {""},"!clear","will clear all messages in a chat!");
    }

    @Override
    public void execute() {
        if(this.settings.base.Args_length == 0) {
            MessageHistory history = new MessageHistory(this.settings.base.getMsg().getTextChannel());
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
}
