package me.tehbeard.BeardStat.commands.interactive;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;

import me.tehbeard.BeardStat.BeardStat;
import me.tehbeard.vocalise.parser.ConfigurablePrompt;
import me.tehbeard.vocalise.parser.PromptBuilder;
import me.tehbeard.vocalise.parser.PromptTag;

@PromptTag(tag="findplayer")
public class FindPlayerPrompt extends ValidatingPrompt implements ConfigurablePrompt {

    private Prompt next;

    public String getPromptText(ConversationContext context) {
        // TODO Auto-generated method stub
        return "Enter a player to lookup";
    }

    public void configure(ConfigurationSection config, PromptBuilder builder) {

        builder.makePromptRef(config.getString("id"),this);
        next = config.isString("next") ? builder.locatePromptById(config.getString("next")) : builder.generatePrompt(config.getConfigurationSection("next"));
    }

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {
        // TODO Auto-generated method stub
        return (BeardStat.self().getStatManager().findPlayerBlob(input)!=null);
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context,
            String input) {
        context.setSessionData("player", input);
        return next;
    }

}
