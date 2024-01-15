package kernel.jdon.memberskill.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberSkill is a Querydsl query type for MemberSkill
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberSkill extends EntityPathBase<MemberSkill> {

    private static final long serialVersionUID = -1198191704L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberSkill memberSkill = new QMemberSkill("memberSkill");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final kernel.jdon.member.domain.QMember member;

    public final kernel.jdon.skill.domain.QSkill skill;

    public QMemberSkill(String variable) {
        this(MemberSkill.class, forVariable(variable), INITS);
    }

    public QMemberSkill(Path<? extends MemberSkill> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberSkill(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberSkill(PathMetadata metadata, PathInits inits) {
        this(MemberSkill.class, metadata, inits);
    }

    public QMemberSkill(Class<? extends MemberSkill> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new kernel.jdon.member.domain.QMember(forProperty("member"), inits.get("member")) : null;
        this.skill = inits.isInitialized("skill") ? new kernel.jdon.skill.domain.QSkill(forProperty("skill"), inits.get("skill")) : null;
    }

}

