package cheatsheet.idgenerate.uuid.genericgenerator.repository;

import cheatsheet.idgenerate.uuid.genericgenerator.entity.Coffee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

/**
 * UUID Generator에 의해 영속 후 생성되는 ID를 사용하는 Coffee Entity Save 테스트.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
class CoffeeRepositoryTest {

    @Autowired
    CoffeeRepository coffeeRepository;

    /**
     * ID를 부여하지 않고, 신규 Entity 생성
     */
    @Test
    void save_coffee() {

        // given
        String name = "coldbrew";

        // ID 생성 X, hibernate에 위임
        Coffee newCoffee = Coffee.builder()
                .name(name)
                .build();

        // when
        // 영속 후, generator에 의해 UUID 생성
        // em.persist 사용
        newCoffee = coffeeRepository.save(newCoffee);
        coffeeRepository.flush();

        Optional<Coffee> searchedCoffee = coffeeRepository.findById(newCoffee.getId());

        // then
        Assertions.assertThat(searchedCoffee.isPresent()).isTrue();
        Assertions.assertThat(searchedCoffee.get().getId()).isEqualTo(newCoffee.getId());
    }

    /**
     * ID를 수동으로 만들어 부여 후, 신규 Entity 생성
     * Entity State 판단하기 위한 SELECT 문이 발생하며, 부여한 ID와 다른 Entity가 생성된다. (em.merge 후 신규 entity를 생성하기 때문에 ID 새로발급)
     */
    @Test
    void merge_coffee() {

        // given
        UUID uuid = UUID.randomUUID();
        System.out.println("uuid = " + uuid);

        String name = "coldbrew";

        // ID를 일부러 미리 생성해 부여해 봄.
        Coffee newCoffee = Coffee.builder()
                .id(uuid)
                .name(name)
                .build();

        // when
        // 영속 후, generator에 의해 생성된 UUID를 영속화 된 mergedCoffee에 새로 할당함.
        // em.merge 사용, select 문 생성
        Coffee mergedCoffee = coffeeRepository.save(newCoffee);
        System.out.println("mergedCoffee.getId() = " + mergedCoffee.getId());
        coffeeRepository.flush();

        Optional<Coffee> searchedCoffee = coffeeRepository.findById(mergedCoffee.getId());

        // then
        Assertions.assertThat(searchedCoffee.isPresent()).isTrue();
        Assertions.assertThat(searchedCoffee.get().getId()).isEqualTo(mergedCoffee.getId());

        // JpaRepository.save 메서드에 파라미터로 활용한 newCoffee의 ID와 실제로 insert된 coffee의 ID가 다르다.
        // hibernate에 ID 생성을 위임할 시, 의도치 않은 문제가 발생할 수 있으므로 신규 Entity를 생성할 때, ID를 부여하지 말고 영속화 한 후 얻어지는 ID를 사용하자.
        Assertions.assertThat(searchedCoffee.get().getId()).isNotEqualTo(newCoffee.getId());
    }
}