package Commands;

import Commands.CommandExecutor.Issue.GetIssue_All_CurrentRepo;
import Enum.ArgumentTypes;
import Entity.Bot_Command;
import Enum.Enum_ErrorType;
import Entity.HelpString;


public class GetIssue extends Bot_Command{

    public GetIssue() {

        this.CommandName = "GetIssue";
        // Arguments
        // type of retrieval single / list. depending on this go on.
        this.Arguments_Allowed = new int[] {0, 1, 2, 3, 4};
        this.ArgumentTypes_Allowed = new ArgumentTypes[] {ArgumentTypes.All};
        this.Help = new HelpString(
                new String[]{"type", "ID", "Repository", "State", "assignee" },
                new String[]{"types you gen get: \n" +
                        "single: a single issue in this case use of ID is mandatory, Repository if you have multiple repository`s, \n" +
                        "all: all issue across al your repository`s, \n" +
                        "list: a list with specified things like asignee, repository, state","get all issues from githup reponame", "get issues with given state: Open, Closed.  can be empty", "the person whe the issue is assigned"},
                new String[]{"Reposetory", "State", "Reposetory State"},
                "gets issues from all repos or from speciefied ones.\n from default it will get all issues from the current repo \n" +
                        "(Dont know wht your current repo is? type: !currentrepo).",
                "!GetIssue examplerepo Open"
                );

    }


    @Override
    public void Execute() {
        if(this.Settings.base.getArgs().length == this.Arguments_Allowed[0]){
            new GetIssue_All_CurrentRepo();
        } else if(this.Settings.base.getArgs().length == this.Arguments_Allowed[1]){
            switch (this.Settings.base.getArgs()[0]){
                case "all":
                    break;
                case "list":
                    this.PushExeption(Enum_ErrorType.Missing_Argument_GI, Enum_ErrorType.Missing_Argument_GI.getMissing_Argument("Repository"), true);
                    break;
                case "single":
                    this.PushExeption(Enum_ErrorType.Missing_Argument_GI, Enum_ErrorType.Missing_Argument_GI.getMissing_Argument("ID"), true);
                    break;
                default:
                    int number;
                    try {
                        number = Integer.parseInt(this.Settings.base.getArgs()[0]);
                    } catch (NumberFormatException e){
                        number = 0;
                    }
                    if(number != 0){
                        // todo command executor getissue_single_currentrepo.
                    }
                    this.PushExeption(Enum_ErrorType.Invalid_Argument_GI,true);
            }
        }
    }
}
