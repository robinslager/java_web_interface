package Single_Commands;

import CommandHandler.Settings_bot;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class CreateIssue extends Command {

    protected String body = "";
    protected boolean eventcheck = false;

    public CreateIssue() {
        this.commandName = "CreateIssue";
        this.Github = true;
        // title asignee
        this.arguments_numbers = new int[]{1, 2};
    }

    @Override
    public void execute() {

        if (this.settings.base.Args_length == 1) {
            String title = this.settings.base.Args[0];
            this.settings.base.sendmessage("Issue discription:");
            // todo change setbody to actual change / creation of issue
            EventWaiter waiter = Settings_bot.getInstance().waiter;
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
            waiter.waitForEvent(MessageReceivedEvent.class, e -> true,
                    e -> issue(title, e.getMessage().getContentRaw()),
                    4, TimeUnit.MINUTES, () -> this.settings.base.sendmessage("Sorry, you took too long.")
            );
        }
    }
    private void issue(String Title, String Discription){
        this.githubapi.CreateIssue(Title, Discription);
        settings.event.getMessage().getChannel().sendMessage("Issue Created").queue();
        Settings_bot.getInstance().changeExec();
    }
}
