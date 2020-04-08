package Single_Commands;

import CommandHandler.Settings_bot;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueState;

import java.util.Collections;
import java.util.List;

import static java.lang.Integer.parseInt;

public class GetIssue extends Command {

    public GetIssue() {
        this.commandName = "GetIssue";
        this.Github = true;
        this.arguments_numbers = new int[]{0, 1, 2};
        // todo : label, asignee
        this.help = HelpBuilder(
                new String[]{"Reposetory", "State"},
                new String[]{"Reposetory", "State", "Reposetory State"},
                new String[]{"get all issues from githup reponame", "get issues with given state: Open, Closed.  can be empty"},
                "!GetIssue examplerepo Open",
                "gets issues from all repos or from speciefied ones.\n from defealt it will get all issues or it will get issues with specified state.");
    }

    @Override
    public void execute() {
        // check git info
        if (githubapi != null) {
            if(Args_length == 0) {
                List<GHIssue> issues = githubapi.getissues(GHIssueState.ALL);
                if (issues != null) {
                    Collections.reverse(issues);
                    this.settings.base.create_Issue_messge(
                            issues, this.settings.event.getTextChannel()
                    );
                }
            } else if(Args_length == 1) {
                if (Args[0].toLowerCase().equals("open") || Args[0].toLowerCase().equals("closed")) {
                    GHIssueState state;
                    if (Args[0].toLowerCase().equals("open")) {
                        state = GHIssueState.OPEN;
                    } else if (Args[0].toLowerCase().equals("closed")) {
                        state = GHIssueState.CLOSED;
                    } else {
                        state = GHIssueState.ALL;
                    }
                    List<GHIssue> issues = githubapi.getissues(state);
                    if (issues != null) {
                        Collections.reverse(issues);
                        this.settings.base.create_Issue_messge(
                                issues, this.settings.event.getTextChannel()
                        );
                    }
                } else{
                    int issue_ID = parseInt(Args[0]);
                    
                }
            }
            Settings_bot.getInstance().changeExec();
        }
    }
}