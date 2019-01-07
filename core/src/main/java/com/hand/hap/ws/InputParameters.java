package com.hand.hap.ws;

import javax.xml.bind.annotation.*;

/**
 * @autor by Val.Zhang
 * @mail wei.zhang12@hand-china.com
 * @date 2019/1/7
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputParameters", propOrder = {
        "distribute"
})
public class InputParameters {
    @XmlElement(name = "ITEMCODE", required = true)
    protected String distribute;

    /**
     * itemCode。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getItemCode() {
        return distribute;
    }

    public void setDistribute(String distribute) {
        this.distribute = distribute;
    }
}
