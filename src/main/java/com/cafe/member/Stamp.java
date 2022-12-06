package com.cafe.member;

import jakarta.persistence.Entity;
import com.cafe.audit.Auditable;

@Entity
public class Stamp extends Auditable {

    private Long stampId;

    private Long memberId;

    // TODO: 스탬프 연동해보기
}
