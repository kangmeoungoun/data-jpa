package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest{
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;
    @Test
    void testMember(){
        Member member = new Member("memberA");
        Member saveMember = memberRepository.save(member);
        Optional<Member> byId = memberRepository.findById(saveMember.getId());
        Member findMember = byId.get();
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }
    @Test
    public void basicCRUD(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);
        //단검 조회 검증
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);
        //리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(2).isEqualTo(all.size());
        //카운트 검증
        long count = memberRepository.count();
        assertThat(2).isEqualTo(count);

        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);
        long deletedCount = memberRepository.count();
        assertThat(0).isEqualTo(deletedCount);
    }
    @Test
    void findByUsernameAndAgeGreaterThen(){
        Member m1 = new Member("AAA" , 10);
        Member m2 = new Member("AAA" , 20);
        memberRepository.save(m1);
        memberRepository.save(m2);
        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA" , 15);
        assertThat("AAA").isEqualTo(result.get(0).getUsername());
        assertThat(20).isEqualTo(result.get(0).getAge());
        assertThat(result.size()).isEqualTo(1);
    }
    @Test
    void findHelloBy(){
        memberRepository.findTop3HelloBy();
    }
    @Test
    void testNamedQuery(){
        Member m1 = new Member("AAA" , 10);
        Member m2 = new Member("BBB" , 20);
        memberRepository.save(m1);
        memberRepository.save(m2);
        List<Member> result = memberRepository.findByUsername("AAA");
        Member findMember = result.get(0);
        assertThat(findMember).isEqualTo(m1);
    }
    @Test
    void testQuery(){
        Member m1 = new Member("AAA" , 10);
        Member m2 = new Member("BBB" , 20);
        memberRepository.save(m1);
        memberRepository.save(m2);
        List<Member> result = memberRepository.fiindUser("AAA",10);
        Member findMember = result.get(0);
        assertThat(findMember).isEqualTo(m1);
    }
    @Test
    void findUsernameList(){
        Member m1 = new Member("AAA" , 10);
        Member m2 = new Member("BBB" , 20);
        memberRepository.save(m1);
        memberRepository.save(m2);
        List<String> usernameList = memberRepository.findUsernameList();
        for (String s : usernameList) {
            System.out.println("s = " + s);
        }
    }

    @Test
    void findMemberDto(){
        Team team = new Team("teamA");
        teamRepository.save(team);
        Member m1 = new Member("AAA" , 10);
        m1.setTeam(team);
        memberRepository.save(m1);

        List<MemberDto> memberDto = memberRepository.findMemberDto();
        for (MemberDto dto : memberDto) {
            System.out.println("dto = " + dto);
        }
    }
    @Test
    void findBynNames(){
        Member m1 = new Member("AAA" , 10);
        Member m2 = new Member("BBB" , 20);
        memberRepository.save(m1);
        memberRepository.save(m2);
        List<Member> result = memberRepository.findByNames(List.of("AAA" , "BBB"));
        for (Member member : result) {
            System.out.println("member = " + member);
        }

    }


}