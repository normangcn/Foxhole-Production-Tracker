package org.ng.fhb;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.ng.fhb.service.FoxholeBotListener;

public class FoxholeBot {

                public static void main(String[] args) {
                    String botToken ="empty";  //bot token

                    try {
                        // Build the JDABuilder and set the event listener
                        JDABuilder builder = JDABuilder.createDefault(botToken, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS);
                        builder.addEventListeners(new FoxholeBotListener());  // Register the listener
                        builder.build();  // Connect the bot to Discord
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

