package org.example.Entity;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Deal {

    /**
     * id, productName, productPrice,startTime, endTime,maximumItems,itemsSold
     */
    private String id;
    private String productName;
    private int productPrice;
    private LocalDateTime startTime;
    private LocalDateTime endtime;
    private int productQuantity;
    private AtomicInteger productSold = new AtomicInteger(0);

    public Deal(String id, String productName, int productPrice, LocalDateTime startTime,
            LocalDateTime endtime, int productQuantity) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.startTime = startTime;
        this.endtime = endtime;
        this.productQuantity = productQuantity;
    }

    public void getItemsSoldAndIncrement(){
        productSold.incrementAndGet();
    }

    @Override
    public String toString() {
        return "Deal{" +
                "id='" + id + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", startTime=" + startTime +
                ", endtime=" + endtime +
                ", productQuantity=" + productQuantity +
                ", productSold=" + productSold +
                '}';
    }



}
