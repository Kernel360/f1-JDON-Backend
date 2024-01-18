package kernel.jdon.favorite.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kernel.jdon.base.BaseEntity;
import kernel.jdon.inflearncourse.domain.InflearnCourse;
import kernel.jdon.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "favorite")
public class Favorite extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", columnDefinition = "BIGINT")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inflearn_id", columnDefinition = "BIGINT")
	private InflearnCourse inflearnCourse;

	@Column(name = "is_deleted", columnDefinition = "BOOLEAN", nullable = false)
	private boolean isDeleted = false;

	@Builder
	public Favorite(Member member, InflearnCourse inflearnCourse) {
		this.member = member;
		this.inflearnCourse = inflearnCourse;
	}

	public void dislike() {
		this.isDeleted = true;
	}

	public void like() {
		this.isDeleted = false;
	}

}
