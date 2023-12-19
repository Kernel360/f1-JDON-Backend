package kernel.jdon.member.domain;

public enum Gender {

	MALE("남성"),
	FEMALE("여성");

	private final String gender;

	Gender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}
}
