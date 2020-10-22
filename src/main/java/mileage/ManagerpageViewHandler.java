package mileage;

import mileage.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ManagerpageViewHandler {


    @Autowired
    private ManagerpageRepository managerpageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenDormantMemberInserted_then_CREATE_1 (@Payload DormantMemberInserted dormantMemberInserted) {
        try {
            if (dormantMemberInserted.isMe()) {
                // view 객체 생성
                Managerpage managerpage = new Managerpage();
                // view 객체에 이벤트의 Value 를 set 함
                managerpage.setMemberId(dormantMemberInserted.getMemberId());
                managerpage.setPhoneNo(dormantMemberInserted.getPhoneNo());
                managerpage.setNickname(dormantMemberInserted.getNickname());
                managerpage.setMemberStatus(dormantMemberInserted.getMemberStatus());
                // view 레파지 토리에 save
                managerpageRepository.save(managerpage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenDormantMemberChanged_then_UPDATE_1(@Payload DormantMemberChanged dormantMemberChanged) {
        try {
            if (dormantMemberChanged.isMe()) {
                // view 객체 조회
                List<Managerpage> managerpageList = managerpageRepository.findByMemberId(dormantMemberChanged.getMemberId());
                for(Managerpage managerpage : managerpageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    managerpage.setMemberStatus(dormantMemberChanged.getMemberStatus());
                    // view 레파지 토리에 save
                    managerpageRepository.save(managerpage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}