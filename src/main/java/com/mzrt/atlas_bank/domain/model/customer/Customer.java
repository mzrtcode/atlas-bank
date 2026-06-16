package com.mzrt.atlas_bank.domain.model.customer;

import com.mzrt.atlas_bank.domain.model.shared.Email;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Customer {

    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private Email email;
    private CustomerStatus status;
    private LocalDateTime createdAt;

    public void initDefaults(){
        if(createdAt == null) this.createdAt = LocalDateTime.now();
        if(status == null) status = CustomerStatus.ACTIVE;
    }

    public boolean isActive(){
        return this.status == CustomerStatus.ACTIVE;
    }
}
