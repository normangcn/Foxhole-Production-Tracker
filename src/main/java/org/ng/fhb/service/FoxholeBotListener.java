package org.ng.fhb.service;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.EmbedBuilder;
import org.ng.fhb.model.BotSupplyData;
import org.ng.fhb.repository.JsonRepo;
import org.ng.fhb.utils.DateTimeUtils;
import org.ng.fhb.utils.EmbedUtils;
import org.ng.fhb.utils.StringsUtils;

import java.util.Arrays;
import java.util.List;

public class FoxholeBotListener extends ListenerAdapter {
    public JsonRepo jsonRepo= new JsonRepo();
    public BotService currentStock = new BotService(jsonRepo);

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        String[] args = message.split("\\s+");

        if (args[0].equalsIgnoreCase("!submit")) {
            if (args.length < 3) {
                event.getChannel().sendMessage("Invalid command format. Use: !submit <material> <quantity>").queue();
                return;
            }
            // Example command: !submit user1 material1 50
            String user = event.getAuthor().getName();
            String material = args[1];
            int amount;
            try {
                amount = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                event.getChannel().sendMessage("Quantity must be a valid number.").queue();
                return;
            }
            onUpdateContributionCommand(event, user, material, amount);
        } else if (args[0].equalsIgnoreCase("!progress")) {
            sendProgressEmbed(event, currentStock);
        } else if (args[0].equalsIgnoreCase("!target")) {
            // Parse the arguments considering quoted strings
            List<String> arguments = StringsUtils.parseArguments(message);
            // Ensure there are at least two arguments
            if (arguments.size() < 3) {
                event.getChannel().sendMessage("Usage: !target <item> <target>").queue();
                return;
            }
            try {
                // Extract the item and target
                String item = arguments.get(1);
                int target = Integer.parseInt(arguments.get(2));
                onSetTargetCommand(event, item, target);
            } catch (NumberFormatException e) {
                event.getChannel().sendMessage("The target must be a valid number.").queue();
            }
        }
    }

    // This method sends an embed with the progress of the material
    private void sendProgressEmbed(MessageReceivedEvent event, BotService currentStock) {
        EmbedBuilder embed = new EmbedBuilder();
        JsonRepo jsonRepo = new JsonRepo();
        DateTimeUtils dateTimeUtils = new DateTimeUtils();
        embed.setTitle("Progress for " + dateTimeUtils.formatDateTimeToNow());
        embed.setColor(0x1F8B4C); // Green color
        BotSupplyData data = jsonRepo.getData();

        StringBuilder description = new StringBuilder();
        data.getItems().forEach((item, itemData) -> {
            int progressPercentage = (int) ((double) itemData.getQuantity() / itemData.getTarget() * 100);
            String progressBar = EmbedUtils.generateProgressBar(progressPercentage);
            description.append(item)
                    .append(" [")
                    .append(progressPercentage)
                    .append("%]: ")
                    .append(progressBar)
                    .append(" (")
                    .append(itemData.getQuantity())
                    .append("/")
                    .append(itemData.getTarget())
                    .append(")\n");
        });
        embed.setDescription(description.toString());
        embed.setFooter("Requested by " + event.getAuthor().getName(), event.getAuthor().getAvatarUrl());
        // Send the embed to the channel where the command was triggered
        event.getChannel().sendMessageEmbeds(embed.build()).queue();
    }
    public void onUpdateContributionCommand(MessageReceivedEvent event, String user, String item, int amount) {
        JsonRepo jsonRepo = new JsonRepo();
        BotSupplyData data = jsonRepo.getData();

        BotSupplyData.ItemData itemData = data.getItems().get(item);
        if (itemData == null) {
            event.getChannel().sendMessage("Item not found: " + item).queue();
            return;
        }
        itemData.setQuantity(itemData.getQuantity() + amount);
        jsonRepo.saveData();
        event.getChannel().sendMessage(String.format("Updated %s: +%d (New total: %d/%d)",
                item, amount, itemData.getQuantity(), itemData.getTarget())).queue();
    }

public void onSetTargetCommand(MessageReceivedEvent event, String item, int target) {
        // Check if the user has the required role
    if (!isUserAuthorized(event)) {
        event.getChannel().sendMessage("You do not have permission to set targets.").queue();
        return;
    }
    // Retrieve the current data from the repository
    JsonRepo jsonRepo= new JsonRepo();
    BotSupplyData data = jsonRepo.getData();
    BotSupplyData.ItemData itemData = data.getItems().get(item);
    if (itemData == null) {
        event.getChannel().sendMessage("Item not found: " + item).queue();
        return;
    }
    // Update the target value for the item
    itemData.setTarget(target);
    jsonRepo.saveData();
    event.getChannel().sendMessage(String.format("Set target for %s to %d.", item, target)).queue();
}

    private boolean isUserAuthorized(MessageReceivedEvent event) {
        // Define the required roles (e.g., "Admin" or "Officer")
        List<String> authorizedRoles = Arrays.asList("Admin", "Officer");
        // Check if the user has any of the required roles
        return event.getMember().getRoles().stream()
                .anyMatch(role -> authorizedRoles.contains(role.getName()));
    }
    private void displayLeaderboard(MessageReceivedEvent event) {
        BotSupplyData data = jsonRepo.getData();

//        StringBuilder leaderboard = new StringBuilder("**Leaderboard:**\n");
//        data.getContributions().forEach((user, contributions) -> {
//            int total = contributions.wait().stream().mapToInt(Integer::intValue).sum();
//            leaderboard.append(String.format("%s: %d\n", user, total));
//        });

//        event.getChannel().sendMessage(leaderboard.toString()).queue();
    }
    public void onListItemsCommand(MessageReceivedEvent event) {
        JsonRepo jsonRepo = new JsonRepo();
        BotSupplyData data = jsonRepo.getData();

        StringBuilder itemList = new StringBuilder("**Available Items:**\n");
        data.getItems().keySet().forEach(item -> itemList.append(item).append("\n"));

        event.getChannel().sendMessage(itemList.toString()).queue();
    }
}
