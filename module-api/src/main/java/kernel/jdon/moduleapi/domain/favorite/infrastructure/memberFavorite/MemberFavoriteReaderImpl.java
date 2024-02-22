package kernel.jdon.moduleapi.domain.favorite.infrastructure.memberFavorite;

import org.springframework.stereotype.Component;

import kernel.jdon.member.domain.Member;
import kernel.jdon.moduleapi.domain.favorite.core.memberFavorite.MemberFavoriteReader;
import kernel.jdon.moduleapi.domain.member.error.MemberErrorCode;
import kernel.jdon.moduleapi.domain.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberFavoriteReaderImpl implements MemberFavoriteReader {

	private final MemberRepository memberRepository;

	@Override
	public Member findById(Long memberId) {

		return memberRepository.findById(memberId)
			.orElseThrow(MemberErrorCode.NOT_FOUND_MEMBER::throwException);
	}

	@Override
	public boolean existsById(Long memberId) {
		return memberRepository.existsById(memberId);
	}
}