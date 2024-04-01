package kernel.jdon.moduledomain.jobcategory.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kernel.jdon.moduledomain.base.AbstractEntity;
import kernel.jdon.moduledomain.skill.domain.Skill;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "job_category")
public class JobCategory extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(255)", nullable = false, unique = true)
    private String name;

    @Column(name = "parent_id", columnDefinition = "BIGINT")
    private Long parentId;

    @Column(name = "wanted_code", columnDefinition = "VARCHAR(255)", nullable = false)
    private String wantedCode;

    @OneToMany(mappedBy = "jobCategory")
    private List<Skill> skillList = new ArrayList<>();

    @Builder
    public JobCategory(String name, String wantedCode) {
        this.name = name;
        this.wantedCode = wantedCode;
    }

}
