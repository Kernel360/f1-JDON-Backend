package kernel.jdon.domain.member.entity;

public enum MemberAccountStatus {

	ACTIVE("활성계정"),
	INACTIVE("휴면계정"),
	WITHDRAW("탈퇴계정");

	private final String status;

	MemberAccountStatus(String status) {
		this.status = status;
	}

	public String getAccountStatus() {
		return status;
	}
}
