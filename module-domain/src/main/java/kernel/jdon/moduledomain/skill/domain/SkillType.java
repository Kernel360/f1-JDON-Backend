package kernel.jdon.moduledomain.skill.domain;

public interface SkillType {
    static String getOrderKeyword() {
        return "기타";
    }

    String getKeyword();
}
