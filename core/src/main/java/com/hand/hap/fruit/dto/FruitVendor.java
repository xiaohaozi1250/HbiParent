package com.hand.hap.fruit.dto;

import com.hand.hap.mybatis.annotation.Condition;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.*;

/**
 * Created by liuneng on 2017/2/21.
 */
@Table(name = "fruit_vendor")
@ExtensionAttribute(disable = true)
public class FruitVendor extends BaseDTO{
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
    private String location;
    @Column
    private String isFelsh;
    @Column
    private Long fruitid;


    public Long getFruitid() {
        return fruitid;
    }

    public void setFruitid(Long fruitid) {
        this.fruitid = fruitid;
    }


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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIsFelsh() {
        return isFelsh;
    }

    public void setIsFelsh(String isFelsh) {
        this.isFelsh = isFelsh;
    }




}
