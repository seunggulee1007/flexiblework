package com.secommon.separtners.modules.commute.commute;

import com.secommon.separtners.modules.account.Account;
import com.secommon.separtners.modules.common.UpdatedEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Commute extends UpdatedEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commute_id")
    private Long id;

    /** 출근 시간 */
    private LocalDateTime onWorkDate;

    /** 퇴근 시간 */
    private LocalDateTime offWorkDate;

    @Enumerated(EnumType.STRING)
    private CommuteType commuteType;
    /** 출퇴근 사용자 */
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public void setWorkDate ( boolean onOffFlag ) {
        if(onOffFlag) { // 출근
            this.onWorkDate = LocalDateTime.now();
        }else { // 퇴근
            this.offWorkDate = LocalDateTime.now();
        }
    }

    public void setAccount(Account account) {
        this.account = account;
        account.getCommutes().add( this );
    }

}
