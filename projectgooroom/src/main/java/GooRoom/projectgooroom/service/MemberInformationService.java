package GooRoom.projectgooroom.service;

import GooRoom.projectgooroom.domain.member.Member;
import GooRoom.projectgooroom.domain.member.MemberInformation;
import GooRoom.projectgooroom.repository.MemberInformationRepository;
import GooRoom.projectgooroom.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberInformationService {

    private final MemberInformationRepository memberInformationRepository;
    private final MemberRepository memberRepository;

    /**
     * MemberInformation 생성
     * @param memberId
     * @param informationDto
     * @return
     */
    @Transactional
    public MemberInformation createMemberInformation(Long memberId, MemberInformationDto informationDto){
        Member member = memberRepository.findMemberById(memberId).get();

        MemberInformation information = MemberInformation.builder()
                .smokingType(informationDto.getSmokingType())
                .drinkingType(informationDto.getDrinkingType())
                .sleepingHabitType(informationDto.getSleepingHabitType())
                .wakeupType(informationDto.getWakeupType())
                .organizeType(informationDto.getOrganizeType())
                .cleanupType(informationDto.getCleanupType())
                .introduce(informationDto.getIntroduce())
                .build();
        information.addMember(member);
        return memberInformationRepository.save(information);
    }

    @Transactional
    public void editMemberInformation(Long memberId, MemberInformationDto informationDto){
        Member member = memberRepository.findMemberById(memberId).get();
        MemberInformation memberInformation = member.getMemberInformation();
        memberInformation.editInformation(informationDto);
    }

    @Transactional
    public void addProfileImage(Long memberId, String filePath){
        Member member = memberRepository.findMemberById(memberId).get();
        member.getMemberInformation().addProfileImage(filePath);
    }
}
