package kernel.jdon.crawler.wanted.skill;

public interface SkillType {
	String getKeyword();

	static String getOrderKeyword() {
		return "기타";
	}
}
