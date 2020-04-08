package Commands.CommandExecutor.Help;

import Commands.*;
import Entity.Bot_Command;
import Enum.Enum_ErrorType;

import java.util.ArrayList;

public class Help_Get_Single_Command extends Help{
    public Help_Get_Single_Command() {
        ArrayList<Bot_Command> Commands = this.getCommandslist();
        boolean valid = false;
        for (int i = 0; i < Commands.size(); i++) {
            // gives help message of specified command.
            if(Settings.base.Args[0].toLowerCase().equals(Commands.get(i).getCommandName().toLowerCase())){
                Bot_Command command = Commands.get(i);
                Settings.base.sendmessage(command.getHelp().GetFullhelp(command.getCommandName()));
                valid = true;
            }
        }
        if(!valid) {
            this.PushExeption(Enum_ErrorType.Invalid_Command, true);
        }
    }

}
