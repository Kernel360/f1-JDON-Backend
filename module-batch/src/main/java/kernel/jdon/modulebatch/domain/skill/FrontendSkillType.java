package kernel.jdon.modulebatch.domain.skill;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FrontendSkillType implements SkillType {
    JAVASCRIPT("JavaScript"),
    REACT("React"),
    TYPESCRIPT("TypeScript"),
    HTML("HTML"),
    CSS("CSS"),
    DOCKER("Docker"),
    NEXT_JS("Next.js"),
    NODE_JS("Node.js"),
    REACT_JS("React.js"),
    VUE_JS("Vue.JS"),
    VUEJS("VueJS"),
    FIGMA("Figma"),
    ANGULAR("Angular"),
    UX("UX"),
    WEBGL("WebGL"),
    WEBRTC("WebRTC"),
    SVELTE("Svelte"),
    LARAVEL("Laravel");

    private final String keyword;

    public static List<String> getAllKeywords() {
        return Arrays.stream(FrontendSkillType.values())
            .map(FrontendSkillType::getKeyword)
            .toList();
    }

    @Override
    public String getKeyword() {
        return keyword;
    }
}
