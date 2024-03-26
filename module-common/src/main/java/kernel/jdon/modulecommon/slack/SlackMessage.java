package kernel.jdon.modulecommon.slack;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SlackMessage {
    private String title;
    private List<Message> messages;
    private String color;

    public SlackMessage(String title) {
        this.title = title;
        messages = new ArrayList<>();
    }

    public static SlackMessage of(String title) {
        return new SlackMessage(title);
    }

    public SlackMessage setColor(String color) {
        this.color = color;
        return this;
    }

    public SlackMessage putMessage(String title, String content) {
        messages.add(new Message(title, content));
        return this;
    }

    @Getter
    @AllArgsConstructor
    static class Message {
        private String title;
        private String content;
    }
}
