package org.ng.fhb.service;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.EmbedBuilder;
import org.ng.fhb.model.BotSupplyData;
import org.ng.fhb.repository.JsonRepo;
import org.ng.fhb.utils.EmbedUtils;

import java.util.HashMap;

public class FoxholeBotListener extends ListenerAdapter {
    public JsonRepo jsonRepo= new JsonRepo();
    String testString = "Concrete Materials";
    int currentQT = 50;
    int targetQT = 100;
    // This method handles the message event and will be triggered on each received message.
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        String[] args = message.split("\\s+");

        if (args[0].equalsIgnoreCase("!updateContribution")) {
            // Example command: !updateContribution user1 material1 50
            String user = event.getAuthor().getName();
            String material = args[1];
            int amount = Integer.parseInt(args[2]);
            onUpdateContributionCommand(user, material, amount);
        } else if (args[0].equalsIgnoreCase("!displayProgress")) {
            onDisplayProgressCommand();
        } else if (args[0].equalsIgnoreCase("!showprogress")) {
            sendProgressEmbed(event);

    } else if (args[0].equalsIgnoreCase("!setTarget")) {
            String material = args[1];
            int target = Integer.parseInt(args[2]);
            onSetTargetCommand(material, target);
        } else if (args[0].equalsIgnoreCase("!leaderboard")) {
            displayLeaderboard(event);
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
    public void onUpdateContributionCommand(String user, String item, int amount) {
        JsonRepo jsonRepo = new JsonRepo();
        BotSupplyData data = jsonRepo.getData();

        BotSupplyData.ItemData itemData = data.getItems().get(item);
        if (itemData == null) {
            event.getChannel().sendMessage("Item not found: " + item).queue();
            return;
        }

        // Update item quantity
        itemData.setQuantity(itemData.getQuantity() + amount);
        jsonRepo.saveData();

        event.getChannel().sendMessage(String.format("Updated %s: +%d (New total: %d/%d)",
                item, amount, itemData.getQuantity(), itemData.getTarget())).queue();
    }
    public void onDisplayProgressCommand() {
        JsonRepo jsonRepo = new JsonRepo();
        BotSupplyData data = jsonRepo.getData();

        StringBuilder progressMessage = new StringBuilder("**Production Progress:**\n");
        data.getItems().forEach((item, itemData) -> {
            progressMessage.append(String.format("%s: %d/%d\n",
                    item, itemData.getQuantity(), itemData.getTarget()));
        });

        event.getChannel().sendMessage(progressMessage.toString()).queue();
    }
    public void onSetTargetCommand(String item, int target) {
        JsonRepo jsonRepo = new JsonRepo();
        BotSupplyData data = jsonRepo.getData();

        BotSupplyData.ItemData itemData = data.getItems().get(item);
        if (itemData == null) {
            event.getChannel().sendMessage("Item not found: " + item).queue();
            return;
        }

        itemData.setTarget(target);
        jsonRepo.saveData();

        event.getChannel().sendMessage(String.format("Set target for %s: %d", item, target)).queue();
    }
    private void displayLeaderboard(MessageReceivedEvent event) {
        BotSupplyData data = jsonRepo.getData();

        StringBuilder leaderboard = new StringBuilder("**Leaderboard:**\n");
        data.getContributions().forEach((user, contributions) -> {
            int total = contributions.values().stream().mapToInt(Integer::intValue).sum();
            leaderboard.append(String.format("%s: %d\n", user, total));
        });

        event.getChannel().sendMessage(leaderboard.toString()).queue();
    }
    public void onListItemsCommand() {
        JsonRepo jsonRepo = new JsonRepo();
        BotSupplyData data = jsonRepo.getData();

        StringBuilder itemList = new StringBuilder("**Available Items:**\n");
        data.getItems().keySet().forEach(item -> itemList.append(item).append("\n"));

        event.getChannel().sendMessage(itemList.toString()).queue();
    }
}
