package org.example.Request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UpdateDealParams {

    private String productName;
    private Integer productPrice;
    private LocalDateTime startTime;
    private LocalDateTime endtime;
    private Integer productQuantity;
}
