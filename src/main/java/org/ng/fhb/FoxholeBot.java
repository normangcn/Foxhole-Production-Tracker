package org.ng.fhb;
import net.dv8tion.jda.api.JDABuilder;
import org.ng.fhb.service.FoxholeBotListener;

public class FoxholeBot {

                public static void main(String[] args) {
                    // Ensure you have your bot token set correctly
                    String botToken = "MyToken";  //bot token

                    try {
                        // Build the JDABuilder and set the event listener
                        JDABuilder builder = JDABuilder.createDefault(botToken);
                        builder.addEventListeners(new FoxholeBotListener());  // Register the listener
                        builder.build();  // Connect the bot to Discord
                    } catch (Exception e) {
                        e.printStackTrace();  // Handle any errors during bot initialization
                    }
                }
            }

