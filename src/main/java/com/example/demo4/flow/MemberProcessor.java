package com.example.demo4.flow;

import com.example.demo4.dto.Member;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class MemberProcessor implements ItemProcessor<Member, Member> {
    private Logger log = LoggerFactory.getLogger(MemberProcessor.class);

    @Override
    public Member process(Member item) throws Exception {

        log.info(item.getName());

        return item;
    }
}
