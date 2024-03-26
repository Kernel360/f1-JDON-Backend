package kernel.jdon.modulecommon.slack;

import static com.slack.api.webhook.WebhookPayloads.*;
import static org.springframework.util.StringUtils.*;

import java.io.IOException;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlackSender {
    private final Slack slackClient = Slack.getInstance();
    private final SlackProperties slackProperties;

    /** 슬랙 메시지 전송 **/
    public void sendMessage(SlackMessage slackMessage) {
        if (!hasText(slackProperties.getWebhookUri())) {
            return;
        }

        try {
            slackClient.send(slackProperties.getWebhookUri(), payload(p -> p
                .text(slackMessage.getTitle()) // 메시지 제목
                .attachments(List.of(
                    Attachment.builder().color(slackMessage.getColor()) // 메시지 색상
                        .fields(getFieldList(slackMessage))
                        .build()))));
        } catch (IOException e) {
            log.error("[SlackSender.sendMessage] 슬랙 알림 메세지 발생중 에러 발생");
            e.printStackTrace();
        }
    }

    /** 메시지 본문 내용 **/
    @NotNull
    private List<Field> getFieldList(SlackMessage slackMessage) {
        return slackMessage.getMessages().stream()
            .map(message -> generateSlackField(message.getTitle(), message.getContent()))
            .toList();
    }

    /** Slack Field 생성 **/
    private Field generateSlackField(String title, String value) {
        return Field.builder()
            .title(title)
            .value(value)
            .valueShortEnough(false)
            .build();
    }

    @Getter
    @RequiredArgsConstructor
    @ConfigurationProperties(prefix = "logging.slack")
    static class SlackProperties {
        private final String webhookUri;
    }
}
