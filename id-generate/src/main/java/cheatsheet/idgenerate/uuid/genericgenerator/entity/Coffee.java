package cheatsheet.idgenerate.uuid.genericgenerator.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

/**
 * Hibernate의 Generator에게 UUID 생성을 위임하여 사용하는 Entity
 * 신규 Entity의 경우, id에 null을 두고 사용하며, 영속화 후 UUID를 얻음. (영속화 전 ID를 얻어 사용할 수 없는 단점)
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coffee {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "COFFEE_ID", columnDefinition = "BINARY(16)") // UUID 저장공간 최소화
    private UUID id;

    private String name;

    @Builder
    public Coffee(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
