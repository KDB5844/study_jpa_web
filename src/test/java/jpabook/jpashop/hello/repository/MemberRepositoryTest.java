package jpabook.jpashop.hello.repository;

import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    void testMember() {
        // given
//        Member member = new Member();
//        member.setUsername("memberA");
//
//        // when
//        Long savedMemberId = memberRepository.save(member);
//        Member findMember = memberRepository.findById(savedMemberId);
//
//        // then
//        assertThat(findMember.getId()).isEqualTo(member.getId());
//        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
    }

}