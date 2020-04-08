package Single_Commands;

public class change_current_repo extends Command  {

    public change_current_repo() {
        this.commandName = "ChangeRepo";
        this.arguments_numbers = new int[] {1};
        this.help = this.HelpBuilder(
                new String[] {"Reposetory Name"},
                new String[] {"Reposetory Name"},
                new String[] {"Reposetory Name: github repository name"},
                "",
                "Changing the repository to a other known one. \n" +
                        "all repos need to be registerd with the gitsetup command"
        );

    }

    @Override
    public void execute() {

    }
}
