package kernel.jdon.modulecommon.slack;

import static com.slack.api.webhook.WebhookPayloads.*;
import static org.springframework.util.StringUtils.*;

import java.io.IOException;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
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
    private static final String serverStartColor = "#439FE0";
    private static final String activeColor = "#009000";
    private static final String errorColor = "#FF0000";

    private final Slack slackClient = Slack.getInstance();
    private final SlackProperties slackProperties;
    private final ApplicationContext applicationContext;

    /** 슬랙 메시지 전송 **/
    public void sendMessage(final SlackMessage slackMessage) {
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
    private List<Field> getFieldList(final SlackMessage slackMessage) {
        return slackMessage.getMessages().stream()
            .map(message -> generateSlackField(message.getTitle(), message.getContent()))
            .toList();
    }

    /** Slack Field 생성 **/
    private Field generateSlackField(final String title, final String value) {
        return Field.builder()
            .title(title)
            .value(value)
            .valueShortEnough(false)
            .build();
    }

    public void sendServerStart(final String moduleName) {
        final String activeProfile = String.join(", ", applicationContext.getEnvironment().getActiveProfiles());
        sendMessage(
            SlackMessage.of("서버 실행 알림")
                .setColor(serverStartColor)
                .putMessage("Start-Module-Name", moduleName)
                .putMessage("Active-Profile", activeProfile));
    }

    public void sendJobError(final String jobName) {
        final String activeProfile = String.join(", ", applicationContext.getEnvironment().getActiveProfiles());
        sendMessage(
            SlackMessage.of("Job 에러 발생 알림")
                .setColor(errorColor)
                .putMessage("Active-Profile", activeProfile)
                .putMessage("Job-Name", jobName));
    }

    public void sendStepError(final String stepName) {
        final String activeProfile = String.join(", ", applicationContext.getEnvironment().getActiveProfiles());
        sendMessage(
            SlackMessage.of("Step 에러 발생 알림")
                .setColor(errorColor)
                .putMessage("Active-Profile", activeProfile)
                .putMessage("Step-Name", stepName));
    }

    private void sendStep(final String stepName, final String title) {
        final String activeProfile = String.join(", ", applicationContext.getEnvironment().getActiveProfiles());
        sendMessage(
            SlackMessage.of(title)
                .setColor(activeColor)
                .putMessage("Step-Name", stepName)
                .putMessage("Active-Profile", activeProfile));
    }

    private void sendScheduler(final String jobSchedulerName, final String title) {
        final String activeProfile = String.join(", ", applicationContext.getEnvironment().getActiveProfiles());
        sendMessage(
            SlackMessage.of(title)
                .setColor(activeColor)
                .putMessage("Scheduler-Name", jobSchedulerName)
                .putMessage("Active-Profile", activeProfile));
    }

    private void sendJob(final String jobName, final String title) {
        final String activeProfile = String.join(", ", applicationContext.getEnvironment().getActiveProfiles());
        sendMessage(
            SlackMessage.of(title)
                .setColor(activeColor)
                .putMessage("Job-Name", jobName)
                .putMessage("Active-Profile", activeProfile));
    }

    public void sendSchedulerStart(final String jobSchedulerName) {
        sendScheduler(jobSchedulerName, "배치 스케줄러 실행 알림");
    }

    public void sendSchedulerEnd(final String jobSchedulerName) {
        sendScheduler(jobSchedulerName, "배치 스케줄러 종료 알림");
    }

    public void sendJobStart(final String jobName) {
        sendJob(jobName, "배치 Job 실행 알림");
    }

    public void sendJobEnd(final String jobName) {
        sendJob(jobName, "배치 Job 종료 알림");
    }

    public void sendStepStart(final String stepName) {
        sendStep(stepName, "배치 Step 실행 알림");
    }

    public void sendStepEnd(final String stepName) {
        sendStep(stepName, "배치 Step 종료 알림");
    }

    @Getter
    @RequiredArgsConstructor
    @ConfigurationProperties(prefix = "logging.slack")
    static class SlackProperties {
        private final String webhookUri;
    }
}
