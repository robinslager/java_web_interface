package Validator.Singlevallidators;

import CommandController.Api_Controller;
import CommandController.Filewriter_Controller;
import CommandHandler.Settings_bot;
import Entity.Bot_Command;
import org.json.JSONObject;
import Enum.Enum_ErrorType;

public class GithubValidator extends SingleValidator {
    private Bot_Command command;
    public GithubValidator(Bot_Command command) {
        this.command = command;
    }

    @Override
    public boolean validate() {
        if(!command.UseGithub()){
            return true;
        } else {
            Filewriter_Controller filewriterController = new Filewriter_Controller();

            JSONObject gitinfo = filewriterController.getGitinfo(command.getSettings().base.get_ServerID(), command.getSettings().base.getauthername());
            if(!gitinfo.getJSONArray("repos").get(0).equals("") && !gitinfo.getString("authentication_key").equals("") &&!gitinfo.getString("username").equals(""))
            {
                // todo no repo arg given do all repos also counts for getissue
                String Repo = gitinfo.getString("username") + "/" + gitinfo.getJSONArray("repos").getString(0);
                this.command.setAuthenticationGithub(new Api_Controller(Repo, gitinfo.getString("authentication_key")));
                if(command.getAuthenticationGithub().check_login() != null){
                    return true;
                } else {
                    command.PushExeption(Enum_ErrorType.authentication_incorrect, false);
                }
            }
            else {
                command.PushExeption(Enum_ErrorType.authentication_Not_Set, false);
            }
            return true;
        }

    }
}
