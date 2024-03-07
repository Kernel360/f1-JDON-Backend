package kernel.jdon.moduledomain.skill.domain;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BackendSkillType implements SkillType {
	AWS("AWS", "AWS", Arrays.asList("Cloud Computing", "Amazon Web Services", "아마존")),
	GIT("Git", "깃", Arrays.asList("Version Control", "GitHub", "깃허브", "버전 관리")),
	PYTHON("Python", "파이썬", Arrays.asList("Programming Language", "언어")),
	MYSQL("MySQL", "MySQL", Arrays.asList("Database", "SQL", "DB", "데이터베이스")),
	JAVA("Java", "자바", Arrays.asList("Programming Language", "언어")),
	SPRING_FRAMEWORK("Spring Framework", "스프링 프레임워크", Arrays.asList("Spring", "스프링")),
	GITHUB("Github", "깃허브", Arrays.asList("Version Control", "Code Hosting", "깃", "버전 관리")),
	DOCKER("Docker", "도커", Arrays.asList("Containerization", "DevOps", "컨테이너", "데브옵스")),
	KOTLIN("Kotlin", "코틀린", Arrays.asList("Android", "안드로이드")),
	NODEJS("Node.js", "노드.js", Arrays.asList("노드")),
	SQL("SQL", "SQL", Arrays.asList("Database", "Query Language", "쿼리", "DB")),
	LINUX("Linux", "리눅스", Arrays.asList("Operating System", "Unix", "운영체제", "OS")),
	SPRING_BOOT("Spring Boot", "스프링 부트", Arrays.asList("스프링부트", "부트")),
	JIRA("JIRA", "지라", Arrays.asList("Project Management", "Atlassian", "프로젝트 관리")),
	JPA("JPA", "JPA", Arrays.asList("Java Persistence API", "Hibernate", "하이버네이트")),
	KUBERNETES("Kubernetes", "쿠버네티스", Arrays.asList("Container Orchestration", "Cloud Native", "컨테이너", "클라우드")),
	REDIS("Redis", "레디스", Arrays.asList("NoSQL", "In-memory Database", "메모리")),
	C_PLUS_PLUS("C++", "C++", Arrays.asList("Programming Language", "Object-oriented", "C")),
	POSTGRESQL("PostgreSQL", "포스트그레SQL", Arrays.asList("Database", "SQL", "데이터베이스", "포스트그레", "Postgre")),
	RESTFUL_API("Restful API", "RESTful API", Arrays.asList("Web Services", "HTTP", "Rest", "Restful")),
	MONGODB("MongoDB", "몽고DB", Arrays.asList("NoSQL", "Document Database", "몽고디비", "몽고")),
	DJANGO("Django", "장고", Arrays.asList("Web Framework", "jango")),
	JENKINS("Jenkins", "젠킨스", Arrays.asList("Continuous Integration", "DevOps", "CI")),
	GO("Go", "고", Arrays.asList("Programming Language", "Concurrency")),
	ELASTICSEARCH("ElasticSearch", "엘라스틱서치", Arrays.asList("Search Engine", "NoSQL", "엘라스틱", "검색", "검색 엔진", "검색엔진")),
	PHP("PHP", "PHP", Arrays.asList("Scripting Language", "Programming Language", "언어")),
	NOSQL("NoSQL", "NoSQL", Arrays.asList("Database", "Non-relational", "데이터베이스")),
	C("C", "C", Arrays.asList("Programming Language", "System Programming", "C언어", "Programming Language", "언어")),
	RDBMS("RDBMS", "RDBMS", Arrays.asList("Relational Database", "SQL", "데이터베이스")),
	ORACLE("Oracle", "오라클", Arrays.asList("Database", "SQL")),
	API("API", "API", Arrays.asList("Application Programming Interface", "Software Interface")),
	DEVOPS("DevOps", "데브옵스", Arrays.asList("Software Development", "Operations")),
	GOLANG("Golang", "고랭", Arrays.asList("Programming Language", "Google", "구글")),
	NGINX("Nginx", "엔진엑스", Arrays.asList("Web Server", "Reverse Proxy", "웹 서버")),
	MSSQL("MSSQL", "MS SQL", Arrays.asList("Microsoft SQL Server", "Database")),
	GRAPHQL("GraphQL", "GraphQL", Arrays.asList("Query Language", "API", "그래프")),
	RABBITMQ("RabbitMQ", "래빗MQ", Arrays.asList("Message Broker", "AMQP", "래빗", "메시지")),
	ORM("ORM", "ORM", Arrays.asList("Object-Relational Mapping", "Database")),
	TDD("TDD", "테스트 주도 개발", Arrays.asList("Test-Driven Development", "Software Development", "테스트")),
	R("R", "R", Arrays.asList("Programming Language", "Statistics", "통계")),
	HADOOP("Hadoop", "하둡", Arrays.asList("Big Data", "Apache", "빅데이터", "아파치")),
	LARAVEL("Laravel", "라라벨", Arrays.asList("lalavel", "lalabel"));

	private final String keyword;
	private final String translation;
	private final List<String> relatedKeywords;
}