package com.bot.conversation;

import com.codepoetics.protonpack.collectors.CompletableFutures;
import com.microsoft.bot.builder.ActivityHandler;
import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.builder.UserState;
import com.microsoft.bot.schema.ChannelAccount;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class CustomActivityHandler extends ActivityHandler {

    private static final String WELCOME = "Here you go!";
    @Autowired
    private UserState userState;

    @Override
    public CompletableFuture<Void> onTurn(TurnContext turnContext) {
        return super.onTurn(turnContext)
                .thenCompose(saveResult -> userState.saveChanges(turnContext));
    }

    @Override
    protected CompletableFuture<Void> onMembersAdded(List<ChannelAccount> membersAdded, TurnContext turnContext) {
        return membersAdded.stream()
                // Find new members
                .filter(member -> StringUtils.equals(member.getId(), turnContext.getActivity().getRecipient().getId()) )
                // set the message
                .map(channelAccount -> turnContext.sendActivity(MessageFactory.text(WELCOME)))
                // collect futures
                .collect(CompletableFutures.toFutureList())
                .thenApply(resp -> null);
    }

    @Override
    protected CompletableFuture<Void> onMessageActivity(TurnContext turnContext) {
        return turnContext.sendActivity(MessageFactory.text("You said: "+ turnContext.getActivity().getText().toString())).thenApply(resp -> null);
    }
}
