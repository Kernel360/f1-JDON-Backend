package kernel.jdon.modulecrawler.legacy.wanted.skill;

public interface SkillType {
    static String getOrderKeyword() {
        return "기타";
    }

    String getKeyword();
}
