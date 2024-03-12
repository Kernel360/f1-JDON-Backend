package kernel.jdon.moduledomain.skill.domain;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
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
}
