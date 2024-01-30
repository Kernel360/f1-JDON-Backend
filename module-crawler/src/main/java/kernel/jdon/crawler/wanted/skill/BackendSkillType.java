package kernel.jdon.crawler.wanted.skill;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BackendSkillType implements SkillType {
	AWS("AWS"),
	GIT("Git"),
	PYTHON("Python"),
	MYSQL("MySQL"),
	JAVA("Java"),
	SPRING_FRAMEWORK("Spring Framework"),
	GITHUB("Github"),
	DOCKER("Docker"),
	KOTLIN("Kotlin"),
	NODEJS("Node.js"),
	SQL("SQL"),
	LINUX("Linux"),
	SPRING_BOOT("Spring Boot"),
	JIRA("JIRA"),
	JPA("JPA"),
	KUBERNETES("Kubernetes"),
	REDIS("Redis"),
	C_PLUS_PLUS("C++"),
	POSTGRESQL("PostgreSQL"),
	RESTFUL_API("Restful API"),
	MONGODB("MongoDB"),
	DJANGO("Django"),
	JENKINS("Jenkins"),
	GO("Go"),
	ELASTICSEARCH("ElasticSearch"),
	PHP("PHP"),
	NOSQL("NoSQL"),
	C("C"),
	RDBMS("RDBMS"),
	ORACLE("Oracle"),
	API("API"),
	DEVOPS("DevOps"),
	GOLANG("Golang"),
	NGINX("Nginx"),
	MSSQL("MSSQL"),
	GRAPHQL("GraphQL"),
	RABBITMQ("RabbitMQ"),
	ORM("ORM"),
	TDD("TDD"),
	R("R"),
	HADOOP("Hadoop"),
	LARAVEL("Laravel");

	private String keyword;

	@Override
	public String getKeyword() {
		return keyword;
	}

	public static List<String> getAllKeywords() {
		return Arrays.stream(BackendSkillType.values())
			.map(BackendSkillType::getKeyword)
			.toList();
	}
}