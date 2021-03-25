package saleson.shop.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.shop.user.domain.ManagerLogin;

import java.util.List;

public interface ManagerLoginRepository extends JpaRepository<ManagerLogin, Long>, QuerydslPredicateExecutor<ManagerLogin> {

    List<ManagerLogin> findManagerLoginsByUserId(long userId);
    void deleteManagerLoginsByUserId(long userId);

}
