package kernel.jdon.modulecrawler.legacy.inflearn.converter;

public enum KeywordTranslator {
    // 프론트
    JAVASCRIPT("JavaScript", "자바스크립트"),
    REACT("React", "리액트"),
    TYPESCRIPT("TypeScript", "타입스크립트"),
    HTML("HTML", "HTML"),
    CSS("CSS", "CSS"),
    DOCKER("Docker", "도커"),
    NEXT_JS("Next.js", "넥스트.js"),
    NODE_JS("Node.js", "노드.js"),
    REACT_JS("React.js", "리액트"),
    VUE_JS("Vue.JS", "뷰.js"),
    VUEJS("VueJS", "뷰JS"),
    FIGMA("Figma", "피그마"),
    ANGULAR("Angular", "앵귤러"),
    UX("UX", "UX"),
    WEBGL("WebGL", "웹GL"),
    WEBRTC("WebRTC", "웹RTC"),
    SVELTE("Svelte", "스벨트"),
    LARAVEL("Laravel", "라라벨"),
    // 백엔드
    AWS("AWS", "AWS"),
    GIT("Git", "Git"),
    PYTHON("Python", "파이썬"),
    MYSQL("MySQL", "MySQL"),
    JAVA("Java", "자바"),
    SPRING_FRAMEWORK("Spring Framework", "스프링 프레임워크"),
    GITHUB("Github", "깃허브"),
    // DOCKER("Docker", "도커"),
    KOTLIN("Kotlin", "코틀린"),
    NODEJS("Node.js", "노드.js"),
    SQL("SQL", "SQL"),
    LINUX("Linux", "리눅스"),
    SPRING_BOOT("Spring Boot", "스프링 부트"),
    JIRA("JIRA", "지라"),
    JPA("JPA", "JPA"),
    KUBERNETES("Kubernetes", "쿠버네티스"),
    REDIS("Redis", "레디스"),
    C_PLUS_PLUS("C++", "C++"),
    POSTGRESQL("PostgreSQL", "포스트그레SQL"),
    RESTFUL_API("Restful API", "레스트풀 API"),
    MONGODB("MongoDB", "몽고DB"),
    DJANGO("Django", "장고"),
    JENKINS("Jenkins", "젠킨스"),
    GO("Go", "고"),
    ELASTICSEARCH("ElasticSearch", "엘라스틱서치"),
    PHP("PHP", "PHP"),
    NOSQL("NoSQL", "노SQL"),
    C("C", "C"),
    RDBMS("RDBMS", "RDBMS"),
    ORACLE("Oracle", "오라클"),
    API("API", "API"),
    DEVOPS("DevOps", "데브옵스"),
    GOLANG("Golang", "고랭"),
    NGINX("Nginx", "엔진엑스"),
    MSSQL("MSSQL", "MS SQL"),
    GRAPHQL("GraphQL", "그래프QL"),
    RABBITMQ("RabbitMQ", "래빗MQ"),
    ORM("ORM", "ORM"),
    TDD("TDD", "TDD"),
    R("R", "R"),
    HADOOP("Hadoop", "하둡");
    // LARAVEL("Laravel", "라라벨");
    private final String englishKeyword;
    private final String koreanTranslation;

    KeywordTranslator(String englishKeyword, String koreanTranslation) {
        this.englishKeyword = englishKeyword;
        this.koreanTranslation = koreanTranslation;
    }

    public static String translateToKorean(String keyword) {
        for (KeywordTranslator translation : KeywordTranslator.values()) {
            if (translation.getEnglishKeyword().equalsIgnoreCase(keyword)) {
                return translation.getKoreanTranslation();
            }
        }
        return keyword;
    }

    public String getEnglishKeyword() {
        return englishKeyword;
    }

    public String getKoreanTranslation() {
        return koreanTranslation;
    }
}
