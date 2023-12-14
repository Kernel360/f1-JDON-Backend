package kernel.jdon.moduleapi.domain.member.entity;

public enum MemberRole {

	ROLE_USER("사용자"),
	ROLE_ADMIN("관리자");

	private final String role;

	MemberRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
}
