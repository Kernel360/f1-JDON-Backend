package kernel.jdon.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.member.domain.Member;
import kernel.jdon.member.domain.SocialProviderType;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmailAndSocialProvider(String email, SocialProviderType socialProvider);

	boolean existsByEmail(String email);
}
