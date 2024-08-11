package org.example.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.example.Entity.Deal;
import org.example.Request.UpdateDealParams;

public class DealManager {

    private Map<String, Deal> deals = new HashMap<>(); //dealId= Deal
    private ConcurrentHashMap<String, List<String>> claimDetails = new ConcurrentHashMap<>(); // userId=[deal1, deal2]

    // create a deal

    public boolean createDeal(String id, String productName, int productPrice, LocalDateTime startTime,
            LocalDateTime endtime, int productQuantity) {
        if(deals.containsKey(id)) {
            System.out.println("Deal=" + id + " Already Created");
            return false;
        }
        Deal deal = new Deal(id,productName,productPrice,startTime,endtime,productQuantity);
        deals.put(id, deal);
        System.out.println("Deal=" + id + " Created Successfully");
        return true;
    }

    //clain a deal - userId, dealId

    public boolean claimDeal(String userId,String dealId) {
        if(!isDealPresent(dealId)){
            System.out.println("Deal=" + dealId + "does not exist");
            return false;
        }
        Deal deal = deals.get(dealId);
        LocalDateTime claimTime = LocalDateTime.now();
        if(!isDealActive(deal,claimTime)) {
            System.out.println("Deal=" + dealId + " is not active");
            return false;
        }
        if(deal.getProductSold().get()>=deal.getProductQuantity()) {
            System.out.println("Deal=" + dealId + " has reached its maximum limits. Thankyou!");
            return false;
        }
        if(claimDetails.containsKey(userId)) {
            List<String> userClaimedDeals  = claimDetails.get(userId);
            for(String userClaimedDeal: userClaimedDeals) {
                if(userClaimedDeal == dealId) {
                    System.out.println("Deal=" + dealId + " is already subscribed. Can't be claimed anymore.");
                    return false;
                }
            }
        }
        deal.getItemsSoldAndIncrement();
        if(claimDetails.containsKey(userId)) {
            claimDetails.get(userId).add(dealId);
        } else {
            claimDetails.put(userId,new ArrayList<>());
            claimDetails.get(userId).add(dealId);
        }
        System.out.println("Deal=" + dealId + "for user="+ userId +" has been successfully claimed");
        return true;
    }

    //update deal
    public boolean updateDeal(String dealId,UpdateDealParams updateDealParams) {

        boolean isValid = validtateUpdateParams(dealId, updateDealParams);
        if(!isValid) return false;
        Deal deal = deals.get(dealId);
        if(deal == null) {
            System.out.println("Something went wrong");
            return false;
        }
        /**
         *  private String productName;
         *     private int productPrice;
         *     private LocalDateTime startTime;
         *     private LocalDateTime endtime;
         *     private int productQuantity;
         */

        if(updateDealParams.getProductName()!=null) {
            deal.setProductName(updateDealParams.getProductName());
        }
        if(updateDealParams.getProductPrice()!=null) {
            deal.setProductPrice(updateDealParams.getProductPrice());
        }
        if(updateDealParams.getStartTime()!=null) {
            deal.setStartTime(updateDealParams.getStartTime());
        }
        if(updateDealParams.getEndtime()!=null) {
            deal.setEndtime(updateDealParams.getEndtime());
        }
        if(updateDealParams.getProductQuantity()!=null) {
            deal.setProductQuantity(updateDealParams.getProductQuantity());
        }
        System.out.println(deal);
        return true;
    }

    //end deal

    public boolean endDeal(String dealId) {
        if(!isDealPresent(dealId)){
            return false;
        }
        Deal deal = deals.get(dealId);
        if(deal == null) {
            System.out.println("Something went wrong");
            return false;
        }
        deal.setEndtime(LocalDateTime.now());
        return true;
    }



    private boolean isDealPresent(String id) {
        return deals.containsKey(id);
    }

    private boolean validtateUpdateParams(String dealId,UpdateDealParams updateDealParams) {
        if(!isDealPresent(dealId)){
            System.out.println("Deal=" + dealId + "does not exist");
            return false;
        }

        if(updateDealParams.getProductQuantity()!=null && updateDealParams.getProductQuantity()<0) {
            System.out.println("can't update negetive product quantity");
            return false;
        }
        if(updateDealParams.getEndtime()!=null && updateDealParams.getEndtime().isBefore(LocalDateTime.now())) {
            System.out.println("endTime is before current time");
            return false;
        }
        return true;
    }

    private boolean isDealActive(Deal deal,LocalDateTime claimTime) {
        if(deal ==null) {
            System.out.println("Something went wrong");
            return false;
        }
        return !(deal.getStartTime().isAfter(claimTime) || deal.getEndtime().isBefore(claimTime));
    }



}
