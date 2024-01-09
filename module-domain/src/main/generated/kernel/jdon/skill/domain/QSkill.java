package kernel.jdon.skill.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSkill is a Querydsl query type for Skill
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSkill extends EntityPathBase<Skill> {

    private static final long serialVersionUID = 1939354056L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSkill skill = new QSkill("skill");

    public final NumberPath<Long> count = createNumber("count", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final kernel.jdon.jobcategory.domain.QJobCategory jobCategory;

    public final StringPath keyword = createString("keyword");

    public final ListPath<kernel.jdon.wantedskill.domain.WantedJdSkill, kernel.jdon.wantedskill.domain.QWantedJdSkill> wantedJdSkillList = this.<kernel.jdon.wantedskill.domain.WantedJdSkill, kernel.jdon.wantedskill.domain.QWantedJdSkill>createList("wantedJdSkillList", kernel.jdon.wantedskill.domain.WantedJdSkill.class, kernel.jdon.wantedskill.domain.QWantedJdSkill.class, PathInits.DIRECT2);

    public QSkill(String variable) {
        this(Skill.class, forVariable(variable), INITS);
    }

    public QSkill(Path<? extends Skill> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSkill(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSkill(PathMetadata metadata, PathInits inits) {
        this(Skill.class, metadata, inits);
    }

    public QSkill(Class<? extends Skill> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.jobCategory = inits.isInitialized("jobCategory") ? new kernel.jdon.jobcategory.domain.QJobCategory(forProperty("jobCategory")) : null;
    }

}

