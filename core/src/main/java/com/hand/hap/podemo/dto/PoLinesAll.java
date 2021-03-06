package com.hand.hap.podemo.dto;

/**Auto Generated By Hap Code Generator**/
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import com.hand.hap.system.dto.BaseDTO;
@ExtensionAttribute(disable=true)
@Table(name = "cux_po_lines_all")
public class PoLinesAll extends BaseDTO {

     public static final String FIELD_PO_LINE_ID = "poLineId";
     public static final String FIELD_PO_HEADER_ID = "poHeaderId";
     public static final String FIELD_INVENTORY_ITEM_ID = "inventoryItemId";
     public static final String FIELD_QUANTITY = "quantity";
     public static final String FIELD_UNIT_PRICE = "unitPrice";


     @Id
     @GeneratedValue
     private Long poLineId;

     private Long poHeaderId; //采购订单头ID

     private Long inventoryItemId; //物料ID

     private Long quantity; //数量

     private Long unitPrice;


     public void setPoLineId(Long poLineId){
         this.poLineId = poLineId;
     }

     public Long getPoLineId(){
         return poLineId;
     }

     public void setPoHeaderId(Long poHeaderId){
         this.poHeaderId = poHeaderId;
     }

     public Long getPoHeaderId(){
         return poHeaderId;
     }

     public void setInventoryItemId(Long inventoryItemId){
         this.inventoryItemId = inventoryItemId;
     }

     public Long getInventoryItemId(){
         return inventoryItemId;
     }

     public void setQuantity(Long quantity){
         this.quantity = quantity;
     }

     public Long getQuantity(){
         return quantity;
     }

     public void setUnitPrice(Long unitPrice){
         this.unitPrice = unitPrice;
     }

     public Long getUnitPrice(){
         return unitPrice;
     }

     }

