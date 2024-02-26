package kernel.jdon.skill.domain;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BackendSkillType implements SkillType {
	AWS("AWS", "AWS", Arrays.asList("Cloud Computing", "Amazon Web Services")),
	GIT("Git", "깃", Arrays.asList("Version Control", "GitHub")),
	PYTHON("Python", "파이썬", Arrays.asList("Django", "Flask")),
	MYSQL("MySQL", "MySQL", Arrays.asList("Database", "SQL")),
	JAVA("Java", "자바", Arrays.asList("Spring", "JPA")),
	SPRING_FRAMEWORK("Spring Framework", "스프링 프레임워크", Arrays.asList("Spring Boot", "Spring MVC")),
	GITHUB("Github", "깃허브", Arrays.asList("Version Control", "Code Hosting")),
	DOCKER("Docker", "도커", Arrays.asList("Containerization", "DevOps")),
	KOTLIN("Kotlin", "코틀린", Arrays.asList("Android", "JVM Languages")),
	NODEJS("Node.js", "노드.js", Arrays.asList("JavaScript", "Server-side")),
	SQL("SQL", "SQL", Arrays.asList("Database", "Query Language")),
	LINUX("Linux", "리눅스", Arrays.asList("Operating System", "Unix")),
	SPRING_BOOT("Spring Boot", "스프링 부트", Arrays.asList("Microservices", "Spring")),
	JIRA("JIRA", "지라", Arrays.asList("Project Management", "Atlassian")),
	JPA("JPA", "JPA", Arrays.asList("Java Persistence API", "Hibernate")),
	KUBERNETES("Kubernetes", "쿠버네티스", Arrays.asList("Container Orchestration", "Cloud Native")),
	REDIS("Redis", "레디스", Arrays.asList("NoSQL", "In-memory Database")),
	C_PLUS_PLUS("C++", "C++", Arrays.asList("Programming Language", "Object-oriented")),
	POSTGRESQL("PostgreSQL", "포스트그레SQL", Arrays.asList("Database", "SQL")),
	RESTFUL_API("Restful API", "RESTful API", Arrays.asList("Web Services", "HTTP")),
	MONGODB("MongoDB", "몽고DB", Arrays.asList("NoSQL", "Document Database")),
	DJANGO("Django", "장고", Arrays.asList("Python", "Web Framework")),
	JENKINS("Jenkins", "젠킨스", Arrays.asList("Continuous Integration", "DevOps")),
	GO("Go", "고", Arrays.asList("Programming Language", "Concurrency")),
	ELASTICSEARCH("ElasticSearch", "엘라스틱서치", Arrays.asList("Search Engine", "NoSQL")),
	PHP("PHP", "PHP", Arrays.asList("Web Development", "Scripting Language")),
	NOSQL("NoSQL", "NoSQL", Arrays.asList("Database", "Non-relational")),
	C("C", "C", Arrays.asList("Programming Language", "System Programming")),
	RDBMS("RDBMS", "RDBMS", Arrays.asList("Relational Database", "SQL")),
	ORACLE("Oracle", "오라클", Arrays.asList("Database", "SQL")),
	API("API", "API", Arrays.asList("Application Programming Interface", "Software Interface")),
	DEVOPS("DevOps", "데브옵스", Arrays.asList("Software Development", "Operations")),
	GOLANG("Golang", "고랭", Arrays.asList("Programming Language", "Google")),
	NGINX("Nginx", "엔진엑스", Arrays.asList("Web Server", "Reverse Proxy")),
	MSSQL("MSSQL", "MS SQL", Arrays.asList("Microsoft SQL Server", "Database")),
	GRAPHQL("GraphQL", "GraphQL", Arrays.asList("Query Language", "API")),
	RABBITMQ("RabbitMQ", "래빗MQ", Arrays.asList("Message Broker", "AMQP")),
	ORM("ORM", "ORM", Arrays.asList("Object-Relational Mapping", "Database")),
	TDD("TDD", "테스트 주도 개발", Arrays.asList("Test-Driven Development", "Software Development")),
	R("R", "R", Arrays.asList("Programming Language", "Statistics")),
	HADOOP("Hadoop", "하둡", Arrays.asList("Big Data", "Apache")),
	LARAVEL("Laravel", "라라벨", Arrays.asList("PHP", "Web Framework"));

	private String keyword;
	private final String translation;
	private final List<String> relatedKeywords;

	public static List<String> getAllKeywords() {
		return Arrays.stream(BackendSkillType.values())
			.map(BackendSkillType::getKeyword)
			.toList();
	}

	@Override
	public String getKeyword() {
		return keyword;
	}
}