package kernel.jdon.modulebatch.job.course.processor.condition;

import kernel.jdon.modulebatch.global.condition.SearchCondition;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CourseDomain implements SearchCondition {
    DEVELOPMENT_PROGRAMMING("it-programming", "개발 프로그래밍"),
    GAME_DEVELOPMENT("game-dev-all", "게임 개발"),
    DATA_SCIENCE("data-science", "데이터 사이언스"),
    SECURITY_NETWORK("it", "보안 네트워크"),
    BUSINESS_MARKETING("business", "비즈니스 마케팅"),
    HARDWARE("hardware", "하드웨어"),
    DESIGN("design", "디자인"),
    ACADEMIC_LANGUAGE("academics", "학문 외국어"),
    CAREER("career", "커리어"),
    SELF_DEVELOPMENT("life", "자기계발");

    private final String searchValue;
    private final String description;

    @Override
    public String getSearchValue() {
        return this.searchValue;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

}
