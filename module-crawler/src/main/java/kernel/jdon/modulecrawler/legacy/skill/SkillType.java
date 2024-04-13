package kernel.jdon.modulecrawler.legacy.skill;

public interface SkillType {
    static String getOrderKeyword() {
        return "기타";
    }

    String getKeyword();
}
