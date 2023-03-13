package cheatsheet.idgenerate.uuid.manual.entity;

import org.springframework.data.domain.Persistable;

import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import java.util.UUID;

/**
 * ID를 직접 생성해 사용하는 Entity를 위해 Persistable Interface를 구현한 한 추상클래스
 * Entity의 new 상태를 판단하기 위해, Persistable을 구현함. isNew 메서드 활용
 * @PrePersist, @PostLoad 통해 entity의 not new 상태를 마킹
 */
@MappedSuperclass
public abstract class AbstractPersistableEntity implements Persistable<UUID> {

    // 영속 대상 제외, 신규 Entity 여부 판단을 위한 프로퍼티
    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    /**
     * marking method. 영속된 Entity의 isNew 값을 false로 설정하기 위함.
     */
    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }
}
