package org.example;

import java.time.LocalDateTime;
import org.example.Request.UpdateDealParams;
import org.example.Service.DealManager;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        DealManager dealManager = new DealManager();
        boolean dealCreated;
        dealCreated = dealManager.createDeal("deal1","product1",90, LocalDateTime.now(),LocalDateTime.now().plusSeconds(2),2);
        System.out.println(dealCreated);

        boolean isDealClaimed;

        UpdateDealParams updateDealParams =  UpdateDealParams.builder().productQuantity(50).build();

        boolean isDealUpdated;
        isDealUpdated = dealManager.updateDeal("deal1",updateDealParams);

        isDealClaimed = dealManager.claimDeal("user1","deal1");

        Thread.sleep(5000);
        isDealClaimed = dealManager.claimDeal("user2","deal1");
    }
}