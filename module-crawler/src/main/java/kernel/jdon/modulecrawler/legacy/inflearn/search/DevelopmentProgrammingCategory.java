package kernel.jdon.modulecrawler.legacy.inflearn.search;

import kernel.jdon.modulecrawler.legacy.search.SearchCondition;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DevelopmentProgrammingCategory implements SearchCondition {
    WEB_DEVELOPMENT("web-dev", "웹 개발"),
    FRONTEND("front-end", "프론트엔드"),
    BACKEND("back-end", "백엔드"),
    FULL_STACK("full-stack", "풀스택"),
    MOBILE_APP_DEVELOPMENT("mobile-app", "모바일 앱 개발"),
    PROGRAMMING_LANGUAGE("programming-lang", "프로그래밍 언어"),
    ALGORITHM_DATA_STRUCTURE("algorithm", "알고리즘 자료구조"),
    DATABASE("database-dev", "데이터베이스"),
    DEVOPS_INFRA("devops-infra", "데브옵스 인프라"),
    CERTIFICATION("certificate-programming", "자격증"),
    DEVELOPMENT_TOOL("programming-tool", "개발 도구"),
    WEB_PUBLISHING("web-publishing", "웹 퍼블리싱"),
    VR_AR("vr-ar", "VR/AR"),
    DESKTOP_APP_DEVELOPMENT("desktop-application", "데스크톱 앱 개발"),
    GAME_DEVELOPMENT("game-dev", "게임 개발"),
    DATA_SCIENCE("dev-data-science", "데이터 사이언스"),
    GENERAL_KNOWLEDGE("dev-besides", "교양 기타");

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
