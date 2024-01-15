package kernel.jdon.crawler.wanted.skill;

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

	@Override
	public String getKeyword() {
		return keyword;
	}
}
