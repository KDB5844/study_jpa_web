package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @PersistenceContext
    EntityManager em;

    @Test
    void 회원가입_성공() {
        // given
        Member member = new Member();
        member.setName("member1");

        // when
        Long savedId = memberService.join(member);

        // then
        em.flush();
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    void 회원가입_실패_중복이름() {
        // given
        Member member1 = new Member();
        member1.setName("member1");

        Long savedId1 = memberService.join(member1);

        // when
        Member member2 = new Member();
        member2.setName("member1");

        // then
        assertThrows(IllegalStateException.class, () -> {
            Long savedId2 = memberService.join(member2);
        });
    }
}