package CommandController;

import CommandHandler.Settings_bot;
import Entity.Bot_Command;
import Resourses.CommandsList;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;


public class CommandController {

    private String CommandString;
    private Settings_bot settings_bot;
    public CommandController(String cmd, MessageReceivedEvent event) {
        // first intitialize of settingsbot
        settings_bot = Settings_bot.getInstance();
        settings_bot.setMessageEvent(event);
        this.CommandString = cmd;
        executeCommand();
    }

    /**
     * @function executeCommand
     *
     * executes all commands.
     *
     * NOTE:
     * all commands work on there own thread.
     * if a command fails for some reason or it returns a error that i have missed during development,
     * than this wil not shut down the hole application but just that thread.
     * this also will prevent long waiting times if commands are alot on the same time.
     */
    private void executeCommand(){
        ArrayList<Bot_Command> commands = new CommandsList().ReturnCommands();
        for (int i = 0; i < commands.size(); i++) {
            if(commands.get(i).getCommandName().toLowerCase().equals(CommandString.toLowerCase())){
                Bot_Command command = commands.get(i);
                new Thread(command::CommandValidation).start();
            }
        }
    }
}