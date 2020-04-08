package Single_Commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

public class Ban_User extends Command {

    public Ban_User() {
        this.commandName = "BanUser";
        this.arguments_numbers = new int[] {2,3};
        this.Perms.add(Permission.BAN_MEMBERS);
        //todo help string

    }

    @Override
    public void execute() {
        // mentioned count ban 1 user.
        if(this.settings.base.getMsg().getMessage().getMentionedMembers().size() == 1){
            User banned = this.settings.base.getMsg().getMessage().getMentionedMembers().get(0).getUser();
            Member bannedmember = this.settings.base.get_guild().getMember(this.settings.base.getMsg().getMessage().getMentionedMembers().get(0).getUser());
            if (bannedmember.hasPermission(Permission.BAN_MEMBERS) ||
                    bannedmember.isOwner() || bannedmember.hasPermission(Permission.ADMINISTRATOR)) {
                this.settings.base.sendmessage("Can't ban a member with higher or equal highest role");
            } else {
                if(this.settings.base.Args_length == 3){
                    this.settings.base.get_guild().ban(
                            this.settings.base.getMsg().getMessage().getMentionedMembers().get(0).getUser(),
                            Integer.parseInt(this.settings.base.Args[1]),
                            this.settings.base.Args[0])
                            .queue();
                    this.settings.base.sendmessage("User " + banned.getName() + " is banned for " + this.settings.base.Args[1] + "day, `s");
                } else {
                    this.settings.base.get_guild().ban(
                            this.settings.base.getMsg().getMessage().getMentionedMembers().get(0).getUser(),
                            0,
                            this.settings.base.Args[0])
                            .queue();
                    this.settings.base.sendmessage("User " + banned.getName() + " is banned for ever");
                }
            }
        }
        else {
            this.settings.base.sendmessage("you need to mention only 1 user!");
        }
    }
}