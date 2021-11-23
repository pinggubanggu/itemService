package hello.itemservice2.domain.member;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository {

  Member save(Member member);

  Member findById(Long id);

  Optional<Member> findByLoginId(String loginId);

  List<Member> findAll();

}
