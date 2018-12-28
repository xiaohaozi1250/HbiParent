package hbi.core.db

import com.hand.hap.db.excel.ExcelDataLoader
import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = MigrationHelper.getInstance().dbType()

databaseChangeLog(logicalFilePath:"hbi/core/db/2018-12-26-init-data-migration.groovy"){
    changeSet(author: "Admin", id: "2018-12-26-hmdm-type-data-xlsx", runAlways:"true"){
        customChange(class:ExcelDataLoader.class.name){
            param(name:"filePath",value:mhi.dataPath("hbi/core/db/data/2018-12-26-hmdm-type-data.xlsx"))
        }
    }
}
