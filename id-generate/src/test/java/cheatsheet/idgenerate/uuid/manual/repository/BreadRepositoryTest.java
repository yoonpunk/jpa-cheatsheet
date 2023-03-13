package cheatsheet.idgenerate.uuid.manual.repository;

import cheatsheet.idgenerate.uuid.manual.entity.Bread;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

/**
 * UUID를 수동으로 부여하여 사용하는 Bread Entity Save 테스트. @Version 사용
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
class BreadRepositoryTest {

    @Autowired
    BreadRepository breadRepository;

    /**
     * ID를 부여해, 신규 Entity 생성
     */
    @Test
    void save_bread() {

        // given
        UUID uuid = UUID.randomUUID();
        System.out.println("uuid = " + uuid);
        String name = "bagel";

        // id 직접 생성
        Bread newBread = Bread.builder()
                .id(uuid)
                .name(name)
                .build();

        // when
        // 영속 후에도 직접 생성한 id가 유지됨. (영속 전 version은 null 이지만, 영속 후 version은 0으로 값이 생성된다.)
        // 신규 entity 생성 시 id가 없을 경우, 오류 발생하므로 반드시 id 부여해 사용할 것
        // em.persist 사용
        Bread savedBread = breadRepository.save(newBread);
        breadRepository.flush();

        // 조회 된 Entity도 isNew = false로 변경됨
        Optional<Bread> searchedBread = breadRepository.findById(uuid);

        // then
        Assertions.assertThat(searchedBread.isPresent()).isTrue();

        // ID 전부다 동일
        Assertions.assertThat(searchedBread.get().getId()).isEqualTo(newBread.getId());
        Assertions.assertThat(searchedBread.get().getId()).isEqualTo(savedBread.getId());

        // version 전부 0
        Assertions.assertThat(searchedBread.get().getVersion()).isEqualTo(0);
        Assertions.assertThat(newBread.getVersion()).isEqualTo(0);
        Assertions.assertThat(savedBread.getVersion()).isEqualTo(0);
    }
}