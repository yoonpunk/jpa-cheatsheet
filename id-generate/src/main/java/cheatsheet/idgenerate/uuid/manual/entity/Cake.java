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
 * ID 생성을 직접하는 Entity, AssignedIdBaseEntity 상속을 통해 Entity의 new 여부를 판단할 수 있는 isNew() 사용
 * 기본 생성자 혹은 프로퍼티에 UUID 값을 생성해 사용
 * DB 조회 Entity의 경우, 초기 생성자에 의해 생성된 UUID를 DB에서 조회한 값으로 hibernate overwrite함 (UUID 생성 비용이 클 경우, 성능에 마이너스요소)
 * 신규 Entity의 경우, 영속 전후로 동일한 ID를 사용할 수 있음.
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Cake extends AbstractPersistableEntity {

    @Id
    @Column(columnDefinition = "BINARY(16)") // UUID 저장공간 최소화
    private UUID id;

    private String name;

    @Builder
    public Cake(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
