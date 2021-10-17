package com.stupidzhang.cps.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
//@AllArgsConstructor
public class TaobaoCps {
    private Integer id;
    private String originTpwd;
    private Number itemId;
    private String title;
    private String coupon;
    private String couponInfo;
    private String couponTpwd;
    private String longCouponTpwd;
    private String qrCode;
    private String notes;
    private String itemImageUrl;
    private String cpsImageUrl;
    private Date createTime;
}
