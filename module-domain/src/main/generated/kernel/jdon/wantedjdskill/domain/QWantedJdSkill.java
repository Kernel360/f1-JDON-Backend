package kernel.jdon.wantedjdskill.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWantedJdSkill is a Querydsl query type for WantedJdSkill
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWantedJdSkill extends EntityPathBase<WantedJdSkill> {

    private static final long serialVersionUID = -1684353304L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWantedJdSkill wantedJdSkill = new QWantedJdSkill("wantedJdSkill");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final kernel.jdon.skill.domain.QSkill skill;

    public final kernel.jdon.wantedjd.domain.QWantedJd wantedJd;

    public QWantedJdSkill(String variable) {
        this(WantedJdSkill.class, forVariable(variable), INITS);
    }

    public QWantedJdSkill(Path<? extends WantedJdSkill> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWantedJdSkill(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWantedJdSkill(PathMetadata metadata, PathInits inits) {
        this(WantedJdSkill.class, metadata, inits);
    }

    public QWantedJdSkill(Class<? extends WantedJdSkill> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.skill = inits.isInitialized("skill") ? new kernel.jdon.skill.domain.QSkill(forProperty("skill"), inits.get("skill")) : null;
        this.wantedJd = inits.isInitialized("wantedJd") ? new kernel.jdon.wantedjd.domain.QWantedJd(forProperty("wantedJd"), inits.get("wantedJd")) : null;
    }

}

