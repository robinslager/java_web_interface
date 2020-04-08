package Single_Commands;

import org.kohsuke.github.GHIssueState;

public class change_issue_state extends Command {
    public change_issue_state() {
        this.commandName = "IssueState";
        this.arguments_numbers = new int[]{1};
        this.Github = true;
    }

    @Override
    public void execute() {

        if (githubapi != null) {
            GHIssueState state;
            if(Args[1] == "open"){
                state = GHIssueState.OPEN;
                this.githubapi.ChangeState(githubapi.GetIssue(Integer.parseInt(this.Args[0])), state);
            } else if(Args[1] == "closed"){
                state = GHIssueState.CLOSED;
                this.githubapi.ChangeState(githubapi.GetIssue(Integer.parseInt(this.Args[0])), state);
            }
            this.settings.event.getChannel().sendMessage("issue id: " + Args[0] + "has been changed to the state of " + Args[1]).queue();
            this.settings.changeExec();

        }



    }
}
