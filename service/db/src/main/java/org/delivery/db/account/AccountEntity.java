package org.delivery.db.account;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@SuperBuilder//부모의 상속변수도 빌더에 포함
@Data
@EqualsAndHashCode(callSuper = true)//callSuper=true: 부모의 entity를 포함해서 비교, false면 해당 entity에서 비교
@Entity
@Table(name = "account")
public class AccountEntity extends BaseEntity {
}
