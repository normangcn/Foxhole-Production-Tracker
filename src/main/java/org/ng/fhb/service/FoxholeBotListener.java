package org.ng.fhb;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.EmbedBuilder;

public class FoxholeBotListener extends ListenerAdapter {

    // This method handles the message event and will be triggered on each received message.
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // Check if the message starts with "!showprogress" command
        String message = event.getMessage().getContentRaw();
        if (message.startsWith("!showprogress")) {
            // Parse the material and target progress (you can refine this part as needed)
            String material = "Iron"; // For example, you can parse the material from the message
            int currentProgress = 50; // Example current progress
            int target = 100; // Example target

            // Call the method to send the embed with progress
            sendProgressEmbed(event, material, currentProgress, target);
        }
    }

    // This method sends an embed with the progress of the material
    private void sendProgressEmbed(MessageReceivedEvent event, String material, int currentProgress, int target) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Progress for " + material);
        embed.setColor(0x1F8B4C); // Green color

        // Calculate progress percentage
        int progressPercentage = (int) ((double) currentProgress / target * 100);
        String progressBar = EmbedUtils.generateProgressBar(progressPercentage);  // Assuming EmbedUtils has this method

        // Add description with progress and the progress bar
        embed.setDescription("**Progress:** " + currentProgress + "/" + target + " (" + progressPercentage + "%)\n" + progressBar);
        embed.setFooter("Requested by " + event.getAuthor().getName(), event.getAuthor().getAvatarUrl());

        // Send the embed to the channel where the command was triggered
        event.getChannel().sendMessageEmbeds(embed.build()).queue();
    }
}
