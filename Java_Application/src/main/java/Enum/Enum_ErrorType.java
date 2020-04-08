package Enum;

public enum Enum_ErrorType {

    // GetIssue
    Invalid_Argument_GI("This is not a valid Argument Start With Argument: Type"),
    Missing_Argument_GI("you are missing arguments with the current Type:"),

    // validation / GetIssue
    Invalid_Amount_Arguments("This command does not need this amount of Arguments."),
    Missing_Permissions("You dont have the right Permissions to execute this command"),

    authentication_Not_Set("your authentication key is probably not set try !gitsetup again \nelse conntact the developer"),
    authentication_incorrect("your authentication key is probably Incorrect !gitsetup again \nelse conntact the developer"),

    // Help
    Invalid_Command("This is no command. \n" +
            "Type: !help for a list with all commands ");



    private String Discription;

    public String getDiscription(){
        return this.Discription;
    }
    public String getMissing_Argument(String Argument){
        return this.getDiscription() + Argument;
    }

    Enum_ErrorType(String discription)
    {
        this.Discription = discription;
    };





}
