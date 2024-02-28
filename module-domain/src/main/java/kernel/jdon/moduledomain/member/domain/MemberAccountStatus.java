package kernel.jdon.moduledomain.member.domain;

public enum MemberAccountStatus {

	ACTIVE("활성계정"),
	// INACTIVE("휴면계정"),
	WITHDRAW("탈퇴계정");

	private final String status;

	MemberAccountStatus(String status) {
		this.status = status;
	}

	public String getAccountStatus() {
		return status;
	}
}
