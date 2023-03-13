package cheatsheet.idgenerate.uuid.manual.entity;


import lombok.Getter;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * ID를 직접 생성해 사용하는 Entity를 위해 @Version 사용하기 위한 기능을 구현한 추상 클래스
 * Entity의 new 상태를 판단하기 위해, @Version을 사용
 * Non-primitive type의 @Version column이 null 이면 new entity로 판단
 */
@MappedSuperclass
@Getter
public abstract class VersionBaseEntity {

    @Version
    private Long version;

}
