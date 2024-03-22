package kernel.jdon.moduledomain.skill.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.skillkeyword.domain.SkillKeyword;
import kernel.jdon.moduledomain.wantedjdskill.domain.WantedJdSkill;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "skill",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"job_category_id", "keyword"})
    })
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "keyword", columnDefinition = "VARCHAR(50)", nullable = false)
    private String keyword;

    @OneToMany(mappedBy = "skill")
    private List<WantedJdSkill> wantedJdSkillList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_category_id", columnDefinition = "BIGINT")
    private JobCategory jobCategory;

    @OneToMany(mappedBy = "skill", fetch = FetchType.EAGER)
    private List<SkillKeyword> skillKeywordList = new ArrayList<>();

}
