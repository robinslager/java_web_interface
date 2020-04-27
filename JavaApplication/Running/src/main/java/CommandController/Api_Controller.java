package CommandController;

import CommandHandler.Settings_bot;
import org.kohsuke.github.*;

import java.io.IOException;
import java.util.List;

public class Api_Controller {
    private GitHub git;
    private GHRepository repo;

    public Api_Controller(String reponame, String authtoken) {
        try {
            this.git = new GitHubBuilder().withOAuthToken(authtoken, reponame).build();

            repo = git.getRepository(reponame);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<GHIssue> getissues(GHIssueState State) {
        try {
            return repo.getIssues(State);
        } catch (IOException e) {
            return null;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public GHIssue GetIssue(int IssueID){
        try {
            return repo.getIssue(IssueID);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void CreateIssue(String Title, String Discription) {
        try {
            repo.createIssue(Title).body(Discription).create();
            Settings_bot.getInstance().executing = false;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void ChangeState(GHIssue issue, GHIssueState state) {
        try {
            if(state == GHIssueState.OPEN){
                issue.close();
            } else {
                issue.reopen();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GHUser check_login(){
        try {
            return git.getMyself();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
