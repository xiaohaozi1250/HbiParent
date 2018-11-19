package com.hand.hap.fruit.dto;

import com.hand.hap.core.annotation.Children;
import com.hand.hap.mybatis.annotation.Condition;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by liuneng on 2017/2/21.
 */
@Table(name = "fruit")
@ExtensionAttribute(disable = true)
public class Fruit extends BaseDTO{
    @Id
    @GeneratedValue(
            generator = "IDENTITY"
    )
    @Column
    private Long id;
    @Column
    @Condition(operator = "LIKE")
    private String name;
    @Column
    private Long price;
    @Column
    private String producingArea;
    @Column
    private double regionId ;
    @Column
    private Date activeDate;

    @Column
    private String color;
    @Transient
    private String regionName ;

    @Transient
    @Children
    private List<FruitVendor> fruitVendors;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getProducingArea() {
        return producingArea;
    }

    public void setProducingArea(String producingArea) {
        this.producingArea = producingArea;
    }

    public double getRegionId() {
        return regionId;
    }

    public void setRegionId(double regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public List<FruitVendor> getFruitVendors() {
        return fruitVendors;
    }

    public void setFruitVendors(List<FruitVendor> fruitVendors) {
        this.fruitVendors = fruitVendors;
    }


    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }



}
