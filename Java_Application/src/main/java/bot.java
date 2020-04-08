import CommandHandler.Settings_bot;
import Events.OnGuildJoin;
import Events.OnMessageRecieved;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;

public class bot extends ListenerAdapter {
    private Message msg;
    private static JDABuilder builder;

    public static void main(String[] args) throws LoginException {
        EventWaiter waiter = new EventWaiter();
        builder = new JDABuilder(AccountType.BOT);
        String Token = "INSERT-NEW-TOKEN";
        builder.setToken(Token);
        builder.addEventListeners(new bot());
        builder.addEventListeners(waiter);
        Settings_bot.getInstance().waiter = waiter;
        builder.build();
    }
    // invite url:  https://discordapp.com/api/oauth2/authorize?client_id=632977724583182336&permissions=8&scope=bot


    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        // event
        new OnGuildJoin(event);
    }
    // todo on guild leave delete data

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // event
        new OnMessageRecieved(event);
    }


}
