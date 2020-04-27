package Validator.Singlevallidators;

import Entity.Bot_Command;
import Enum.Enum_ErrorType;

public class ArgumentValidator extends SingleValidator{

    /**
     * @param Command
     * command that is being checked
     */
    private Bot_Command Command;

    /**
     * @param Arguments_Allowed
     * a int array with all amounts of arguments that may be used with a command.
     */
    private int[] Arguments_Allowed;

    /**
     * @param Arguments
     * All Arguments
     */
    private String[] Arguments;

    public ArgumentValidator(Bot_Command command) {
        Command = command;
        Arguments_Allowed = command.getArguments_Allowed();
        Arguments = command.getSettings().base.getArgs();
    }

    /**
     * checks if correct argument amount of arguments are given.
     * checks if correct argument types of arguments are given.
     * @return boolean
     */
    @Override
    public boolean validate() {
        Boolean validate = false;
        for (int ArgumentAllowed_option : Arguments_Allowed) {
            if(Command.getSettings().base.getArgs_length() == ArgumentAllowed_option){
                validate = true;
            }
        }
        // exeption is only set when validate isn`t true
        if(!validate){
            Command.PushExeption(
                    Enum_ErrorType.Invalid_Amount_Arguments, true);
            return false;
        }


        // todo if there are more argument types than check for a type.
        if(validate){
            return true;
        } else {
            return false;
        }

    }
}
