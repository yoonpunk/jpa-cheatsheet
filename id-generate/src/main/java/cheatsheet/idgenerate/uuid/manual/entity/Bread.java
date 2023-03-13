package cheatsheet.idgenerate.uuid.manual.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

/**
 *
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Bread extends VersionBaseEntity {

    @Id
    @Column(columnDefinition = "BINARY(16)") // UUID 저장공간 최소화
    private UUID id;

    private String name;

    @Builder
    public Bread(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
