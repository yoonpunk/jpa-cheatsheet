package cheatsheet.idgenerate.uuid.manual.repository;

import cheatsheet.idgenerate.uuid.manual.entity.Cake;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

/**
 * UUID를 수동으로 부여하여 사용하는 Cake Entity Save 테스트. Persistable I/F 사용
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
class CakeRepositoryTest {

    @Autowired
    CakeRepository cakeRepository;

    /**
     * ID를 부여해, 신규 Entity 생성
     */
    @Test
    void save_cake() {

        // given
        UUID uuid = UUID.randomUUID();
        System.out.println("uuid = " + uuid);
        String name = "lemon cake";

        // id 직접 생성
        Cake newCake = Cake.builder()
                .id(uuid)
                .name(name)
                .build();

        // when
        // 영속 후에도 직접 생성한 id가 유지됨. (영속 후, isNew = false로 변경됨)
        // 신규 entity 생성 시 id가 없을 경우, 오류 발생하므로 반드시 id 부여해 사용할 것
        // em.persist 사용
        Cake savedCake = cakeRepository.save(newCake);
        cakeRepository.flush();

        // 조회 된 Entity도 isNew = false로 변경됨
        Optional<Cake> searchedCake = cakeRepository.findById(uuid);

        // then
        Assertions.assertThat(searchedCake.isPresent()).isTrue();

        // ID 전부다 동일
        Assertions.assertThat(searchedCake.get().getId()).isEqualTo(newCake.getId());
        Assertions.assertThat(searchedCake.get().getId()).isEqualTo(savedCake.getId());
    }
}