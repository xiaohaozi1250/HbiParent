package hbi.core.db

/**
 * @autor by Val.Zhang
 * @mail wei.zhang12@hand-china.com
 * @date 2018/12/26
 */

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()

databaseChangeLog(logicalFilePath:"hbi/core/db/2018-12-29-init-table-migration.groovy"){
    changeSet(author:"Admin", id: "2018-12-28_HMDM_DISTRIBUTE_SITUATION"){
        if(mhi.isDbType('oracle') || mhi.isDbType('hana')){
            createSequence(sequenceName:'HMDM_DISTRIBUTE_SITUATION_S', startValue:"10001")
        }
        createTable(tableName:"HMDM_DISTRIBUTE_SITUATION"){
            column(name:"HEADER_ID",type:"bigint",autoIncrement:"true",startWith:"10001",remarks:"表ID，主键，供其他表做外键"){
                constraints(nullable:"false",primaryKey: "true",primaryKeyName: "HMDM_DISTRIBUTE_SITUATION_PK")
            }
            column(name:"ITEM_CODE",type:"varchar(240)")
            column(name:"ITEM_NAME",type:"varchar(240)")
            column(name:"ORANIZATION_CODE",type:"varchar(240)")
            column(name:"BATCH_NUM",type:"varchar(240)")
            column(name:"STATUS",type:"varchar(240)")
            column(name:"DISTRIBUTION_DATE",type:"Date")
            column(name:"OBJECT_VERSION_NUMBER",type:"bigint",defaultValue:"1",remarks:"行版本号，用来处理锁"){
                constraints(nullable:"false")
            }
            column(name:"CREATED_BY",type:"bigint", defaultValue : "-1")
            column(name:"CREATION_DATE",type:"datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name:"LAST_UPDATED_BY",type:"bigint", defaultValue : "-1")
            column(name:"LAST_UPDATE_DATE",type:"datetime", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name:"LAST_UPDATE_LOGIN",type:"bigint", defaultValue : "-1")
            column(name:"PROGRAM_APPLICATION_ID",type:"bigint")
            column(name:"PROGRAM_ID",type:"bigint")
            column(name:"PROGRAM_UPDATE_DATE",type:"datetime")
            column(name:"REQUEST_ID",type:"bigint")
            column(name:"ATTRIBUTE_CATEGORY",type:"varchar(30)")
            column(name:"ATTRIBUTE1",type:"varchar(150)")
            column(name:"ATTRIBUTE2",type:"varchar(150)")
            column(name:"ATTRIBUTE3",type:"varchar(150)")
            column(name:"ATTRIBUTE4",type:"varchar(150)")
            column(name:"ATTRIBUTE5",type:"varchar(150)")
            column(name:"ATTRIBUTE6",type:"varchar(150)")
            column(name:"ATTRIBUTE7",type:"varchar(150)")
            column(name:"ATTRIBUTE8",type:"varchar(150)")
            column(name:"ATTRIBUTE9",type:"varchar(150)")
            column(name:"ATTRIBUTE10",type:"varchar(150)")
            column(name:"ATTRIBUTE11",type:"varchar(150)")
            column(name:"ATTRIBUTE12",type:"varchar(150)")
            column(name:"ATTRIBUTE13",type:"varchar(150)")
            column(name:"ATTRIBUTE14",type:"varchar(150)")
            column(name:"ATTRIBUTE15",type:"varchar(150)")
        }
        addUniqueConstraint(columnNames:"HEADER_ID",tableName:"HMDM_DISTRIBUTE_SITUATION",constraintName: "HMDM_DISTRIBUTE_SITUATION_U1")
        addUniqueConstraint(columnNames:"ITEM_CODE",tableName:"HMDM_DISTRIBUTE_SITUATION",constraintName: "HMDM_DISTRIBUTE_SITUATION_U2")

    }
}