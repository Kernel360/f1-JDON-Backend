package kernel.jdon.jobcategory.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJobCategory is a Querydsl query type for JobCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJobCategory extends EntityPathBase<JobCategory> {

    private static final long serialVersionUID = -2082823832L;

    public static final QJobCategory jobCategory = new QJobCategory("jobCategory");

    public final kernel.jdon.base.QBaseEntity _super = new kernel.jdon.base.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final NumberPath<Long> parentId = createNumber("parentId", Long.class);

    public final ListPath<kernel.jdon.skill.domain.Skill, kernel.jdon.skill.domain.QSkill> skillList = this.<kernel.jdon.skill.domain.Skill, kernel.jdon.skill.domain.QSkill>createList("skillList", kernel.jdon.skill.domain.Skill.class, kernel.jdon.skill.domain.QSkill.class, PathInits.DIRECT2);

    public final StringPath wantedCode = createString("wantedCode");

    public QJobCategory(String variable) {
        super(JobCategory.class, forVariable(variable));
    }

    public QJobCategory(Path<? extends JobCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJobCategory(PathMetadata metadata) {
        super(JobCategory.class, metadata);
    }

}

