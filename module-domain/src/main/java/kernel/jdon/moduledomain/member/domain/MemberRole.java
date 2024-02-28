package kernel.jdon.moduledomain.member.domain;

public enum MemberRole {

	ROLE_USER("사용자"),
	ROLE_ADMIN("관리자"),
	ROLE_GUEST("게스트");

	private final String role;

	MemberRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
}
