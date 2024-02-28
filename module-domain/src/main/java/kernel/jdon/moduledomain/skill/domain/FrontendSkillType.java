package kernel.jdon.moduledomain.skill.domain;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FrontendSkillType implements SkillType {

	JAVASCRIPT("JavaScript", "자바스크립트", Arrays.asList("ES6", "Ajax")),
	REACT("React", "리액트", Arrays.asList("Redux", "React Hooks", "리덕스")),
	TYPESCRIPT("TypeScript", "타입스크립트", Arrays.asList("JavaScript", "Static Typing", "자바스크립트")),
	HTML("HTML", "HTML", Arrays.asList("Semantic HTML", "HTML5")),
	CSS("CSS", "CSS", Arrays.asList("Flexbox", "Grid", "CSS Variables", "그리드")),
	DOCKER("Docker", "도커", Arrays.asList("Container", "Docker Compose", "Docker Swarm", "컨테이너", "컴포즈", "스웜")),
	NEXT_JS("Next.js", "넥스트.js", Arrays.asList("SSR", "Static Site Generation", "넥스트")),
	NODE_JS("Node.js", "노드.js", Arrays.asList("Express", "NPM", "Yarn", "노드", "익스프레스")),
	REACT_JS("React.js", "리액트.js", Arrays.asList("React Native", "JSX", "리액트")),
	VUE_JS("Vue.JS", "뷰.JS", Arrays.asList("Vuex", "Vue Router", "Vue", "뷰")),
	VUEJS("VueJS", "뷰JS", Arrays.asList("Vuex", "Vue Router", "Vue", "뷰")),
	FIGMA("Figma", "피그마", Arrays.asList("Design", "Prototyping", "Collaboration", "디자인", "프로토타입", "협업", "툴")),
	ANGULAR("Angular", "앵귤러", Arrays.asList("Angular Material", "타입스크립트")),
	UX("UX", "사용자 경험", Arrays.asList("User Research", "Usability Testing", "Interaction Design")),
	WEBGL("WebGL", "웹GL", Arrays.asList("3D Graphics", "Canvas API", "Shaders")),
	WEBRTC("WebRTC", "웹RTC", Arrays.asList("Real-time Communication", "Peer-to-Peer", "Video Chat", "비디오")),
	SVELTE("Svelte", "스벨트", Arrays.asList("Reactive", "Compiler", "리액티브")),
	LARAVEL("Laravel", "라라벨", Arrays.asList("PHP"));

	private final String keyword;
	private final String translation;
	private final List<String> relatedKeywords;
}
