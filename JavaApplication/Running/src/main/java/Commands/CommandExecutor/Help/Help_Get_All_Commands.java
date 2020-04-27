package Commands.CommandExecutor.Help;

import Commands.*;
import Entity.Bot_Command;
import Resourses.CommandsList;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;

public class Help_Get_All_Commands extends Help{
    public Help_Get_All_Commands() {
        ArrayList<Bot_Command> Commands = new CommandsList().ReturnCommands();
        // makes message wit a short command description for all commands.
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Commands");
        for (Bot_Command command : Commands) {
            embed.addField(command.getCommandName(), command.getHelp().getshorthelp(), false);
        }
        this.Settings.base.sendmessage(embed.build());
    }

}
