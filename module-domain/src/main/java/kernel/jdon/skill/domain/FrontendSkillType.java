package kernel.jdon.skill.domain;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FrontendSkillType implements SkillType {
	JAVASCRIPT("JavaScript", "자바스크립트", Arrays.asList("ES6", "Ajax", "jQuery")),
	REACT("React", "리액트", Arrays.asList("Redux", "React Hooks", "Next.js")),
	TYPESCRIPT("TypeScript", "타입스크립트", Arrays.asList("JavaScript", "Static Typing")),
	HTML("HTML", "HTML", Arrays.asList("Semantic HTML", "HTML5")),
	CSS("CSS", "CSS", Arrays.asList("Flexbox", "Grid", "CSS Variables")),
	DOCKER("Docker", "도커", Arrays.asList("Container", "Docker Compose", "Docker Swarm")),
	NEXT_JS("Next.js", "Next.js", Arrays.asList("SSR", "Static Site Generation")),
	NODE_JS("Node.js", "노드.js", Arrays.asList("Express", "NPM", "Yarn")),
	REACT_JS("React.js", "리액트.js", Arrays.asList("React Native", "JSX")),
	VUE_JS("Vue.JS", "뷰.JS", Arrays.asList("Vuex", "Vue Router", "Nuxt.js")),
	VUEJS("VueJS", "뷰JS", Arrays.asList("Vue 3", "Composition API")),
	FIGMA("Figma", "피그마", Arrays.asList("Design", "Prototyping", "Collaboration")),
	ANGULAR("Angular", "앵귤러", Arrays.asList("TypeScript", "RxJS", "Angular Material")),
	UX("UX", "사용자 경험", Arrays.asList("User Research", "Usability Testing", "Interaction Design")),
	WEBGL("WebGL", "WebGL", Arrays.asList("3D Graphics", "Canvas API", "Shaders")),
	WEBRTC("WebRTC", "WebRTC", Arrays.asList("Real-time Communication", "Peer-to-Peer", "Video Chat")),
	SVELTE("Svelte", "스벨트", Arrays.asList("Reactive", "Compiler", "Framework")),
	LARAVEL("Laravel", "라라벨", Arrays.asList("PHP", "MVC", "Web Application"));

	private final String keyword;
	private final String translation;
	private final List<String> relatedKeywords;

	@Override
	public String getKeyword() {
		return keyword;
	}

	@Override
	public String getTranslation() {
		return translation;
	}

	@Override
	public List<String> getRelatedKeywords() {
		return relatedKeywords;
	}
}
