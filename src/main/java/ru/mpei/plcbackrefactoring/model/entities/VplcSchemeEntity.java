package ru.mpei.plcbackrefactoring.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "vplc_schemes")
@IdClass(VplcSchemeEntity.SchemeIdProjectIdCompositeKey.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class VplcSchemeEntity {
//    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "scheme_id", nullable = false)
    private UUID schemeId;

    @Column(name = "scheme_name", nullable = false)
    private String schemeName;

    @Id
    @Column(name = "project_id", nullable = false)
    private UUID projectId;

    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Lob
    @Column(name = "scheme_json")
    private String schemeJson;

    @Column(nullable = false)
    private boolean validated;

    public VplcSchemeEntity(UUID schemeId, String schemeName, UUID projectId, String projectName) {
        this.schemeId = schemeId;
        this.schemeName = schemeName;
        this.projectId = projectId;
        this.projectName = projectName;
    }

    public static class SchemeIdProjectIdCompositeKey implements Serializable {
        private UUID schemeId;
        private UUID projectId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SchemeIdProjectIdCompositeKey that = (SchemeIdProjectIdCompositeKey) o;
            return Objects.equals(schemeId, that.schemeId) && Objects.equals(projectId, that.projectId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(schemeId, projectId);
        }
    }
}
