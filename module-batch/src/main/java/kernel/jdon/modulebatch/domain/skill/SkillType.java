package kernel.jdon.modulebatch.domain.skill;

public interface SkillType {
    static String getOrderKeyword() {
        return "기타";
    }

    String getKeyword();
}
