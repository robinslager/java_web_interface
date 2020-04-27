package Resourses;

import Commands.GetIssue;
import Commands.Help;
import Entity.Bot_Command;


import java.util.ArrayList;

public class CommandsList {
    private ArrayList<Bot_Command> Commands = new ArrayList<>();
    public ArrayList<Bot_Command> ReturnCommands(){
        Commands.add(new Help());
        Commands.add(new GetIssue());

        return Commands;
    }
}
