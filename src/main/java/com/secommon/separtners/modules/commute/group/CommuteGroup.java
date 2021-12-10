package com.secommon.separtners.modules.commute.group;

import com.secommon.separtners.modules.account.Account;
import com.secommon.separtners.modules.commute.area.CommuteArea;
import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommuteGroup {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="commute_group_id")
    private Long id;

    /** 그룹 명칭 */
    private String groupName;

    /** 사용여부 */
    private boolean active;

    @OneToMany(mappedBy = "commuteGroup", fetch = LAZY)
    private List<Account> accountList;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "commute_area_id")
    private CommuteArea commuteArea;

}
