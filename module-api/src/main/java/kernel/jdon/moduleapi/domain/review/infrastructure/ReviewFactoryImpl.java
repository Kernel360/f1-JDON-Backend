package kernel.jdon.moduleapi.domain.review.infrastructure;

import org.springframework.stereotype.Component;

import kernel.jdon.moduleapi.domain.jd.core.JdReader;
import kernel.jdon.moduleapi.domain.member.core.MemberReader;
import kernel.jdon.moduleapi.domain.review.core.ReviewCommand;
import kernel.jdon.moduleapi.domain.review.core.ReviewFactory;
import kernel.jdon.moduleapi.domain.review.core.ReviewStore;
import kernel.jdon.moduledomain.member.domain.Member;
import kernel.jdon.moduledomain.review.domain.Review;
import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewFactoryImpl implements ReviewFactory {
    private final MemberReader memberReader;
    private final JdReader jdReader;
    private final ReviewStore reviewStore;

    public Review saveReview(final ReviewCommand.CreateReviewRequest command) {
        final Member findMember = memberReader.findById(command.getMemberId());
        final WantedJd findWantedJd = jdReader.findWantedJd(command.getJdId());

        return reviewStore.save(command.toEntity(findMember, findWantedJd));
    }

}
