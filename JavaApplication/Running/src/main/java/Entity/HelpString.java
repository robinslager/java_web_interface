package Entity;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class HelpString {
    /**
     * @param Arguments String[]
     * array with all possible argumentens and argument types.
     * will only shown when helpcommand with specified command to show.
     */
    protected String[] Arguments;

    /**
     * @param ArgumentsDiscrition String[]
     * discription of all arguments.
     */
    protected String[] ArgumentsDiscrition;

    /**
     * @param ArgumentsCombinations String[]
     * a array with all possiblecombinations of arguments.
     * will only shown when helpcommand with specified command to show.
     */
    protected String[] ArgumentsCombinations;

    /**
     * @param CommandDiscription String
     * a Discription about the Command. this is the only thing that is shown when viewing all commands with help view all commands.
     */
    protected String CommandDiscription;

    /**
     * @param Examplecommand String
     * a small view what the command looks like.
     * will only shown when helpcommand with specified command to show.
     */
    protected String Examplecommand;

    public HelpString(String[] arguments, String[] Argumentdiscription, String[] argumentsCombinations, String commandDiscription, String examplecommand) {
        this.Arguments = arguments;
        this.ArgumentsDiscrition = Argumentdiscription;
        this.ArgumentsCombinations = argumentsCombinations;
        this.CommandDiscription = commandDiscription;
        this.Examplecommand = examplecommand;
    }

    /**
     * @function getshorthelp()
     *
     * this function is used if !help is used and all commands are shown.
     * @return CommandDiscription
     */
    public String getshorthelp(){
        return this.CommandDiscription;
    }

    /**
     * @function GetFullhelp()
     *
     * will return a detailed explanation about a command.
     * @param Commandname String
     * @return MessageEmbed
     */
    public MessageEmbed GetFullhelp(String Commandname){
        EmbedBuilder builder = new EmbedBuilder();
        StringBuilder help = new StringBuilder("Argumenten: ");
        // all argumenten for discription.
        if(this.Arguments.length != this.ArgumentsDiscrition.length){
            System.out.println("error on argument lenght or argument discription");
            return null;
        }
        else {

            for (int i = 0; i < this.ArgumentsCombinations.length; i++) {
                String[] single_arg_combination = this.ArgumentsCombinations[i].split(" ");
                if (single_arg_combination.length == 0) {

                } else {
                    for (int j = 0; j < single_arg_combination.length; j++) {
                        help.append("<").append(single_arg_combination[j]).append("> ");
                        if (j == single_arg_combination.length - 1) {
                        }
                    }
                    if(i != this.ArgumentsCombinations.length -1) {
                        help.append("\\|| ");
                    }
                }

            }
            help.append("\n");
            for (int i = 0; i < this.Arguments.length; i++) {
                help.append("<").append(this.Arguments[i]).append(">: ").append(this.ArgumentsDiscrition[i]).append("\n");
            }
            builder.setTitle(Commandname);
            builder.setDescription(help);
            return builder.build();
        }
    }


}
