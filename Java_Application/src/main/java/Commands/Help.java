package Commands;

import Commands.CommandExecutor.Help.Help_Get_All_Commands;
import Commands.CommandExecutor.Help.Help_Get_Single_Command;
import Enum.ArgumentTypes;
import Entity.Bot_Command;
import Entity.HelpString;
import Resourses.CommandsList;

import java.util.ArrayList;

import static Enum.ArgumentTypes.*;

public class Help extends Bot_Command {
    public Help() {
        this.CommandName = "Help";
        this.Arguments_Allowed = new int[]{0, 1};
        this.ArgumentTypes_Allowed = new ArgumentTypes[] {NONE, STRING};
        this.Help = new HelpString(
                new String[] {"CommandName"},
                new String[] {"the name of the command that you want to view."},
                new String[] {"CommandName"},
                "shows a description of all commands \n" +
                        "with no argument you get all commands.",
                "!help help"
        );
    }

    @Override
    public void Execute() {
        if (this.Settings.base.getArgs_length() == this.Arguments_Allowed[0]) {
           new Help_Get_All_Commands();
        } else {
           new Help_Get_Single_Command();
        }

    }

    public ArrayList<Bot_Command> getCommandslist(){
        return new CommandsList().ReturnCommands();
    }
}
