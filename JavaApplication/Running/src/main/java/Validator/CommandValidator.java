package Validator;

import Entity.Bot_Command;
import Validator.Singlevallidators.ArgumentValidator;
import Validator.Singlevallidators.GithubValidator;
import Validator.Singlevallidators.PermissionValidator;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;

public class CommandValidator {

    /**
     * @param Command Bot_Command.
     * to initialize everything.
     * with this i dont have to give anything else with the command.
     */
    private Bot_Command Command;

    public CommandValidator(Bot_Command command) {
        this.Command = command;
    }

    public Boolean validate(){
        if(new ArgumentValidator(Command).validate()){
            if(new PermissionValidator(Command).validate()){
                return new GithubValidator(Command).validate();
            }
        }
        return false;
    }
}
