package com.lc.springboot.gencode.saas2;

import com.lc.springboot.gencode.saas.AbstractSaasBaseGenCode;

/**
 * 授信
 *
 * @author liangc
 */
public class SaasOfcCodeGenerator_Saas2 extends AbstractSaasBaseGenCode {

    public static void main(String[] args) {
        AbstractSaasBaseGenCode genCode = new SaasOfcCodeGenerator_Saas2();
        genCode.projectName = "saas-ofc";
        genCode.rootUrlMapping = "ofc";
        genCode.db_env = DB_ENV.TEST_A;

        genCode.outputRootDir = "/Users/liangchao/tmp/qjd-saas/saas-biz/";
        genCode.dtoOutputRootDir = "/Users/liangchao/tmp/qjd-saas/saas-api/saas-ofc-api/";

        genCode.templateDir = "qjd-saas-boss-dubbo-saas2";
        genCode.superEntityClassName = "com.qjdchina.saas.common.web.BossBaseModelV2";
        genCode.commonFieldsArray = new String[]{
                "id",
                "org_id",
                "created_by",
                "created_time",
                "updated_time",
                "is_deleted",
                "ins_datetime",
                "upd_datetime"};

//        genCode.outputRootDir = "/Users/liangchao/IdeaProjects/qjd/qjd-saas/saas-biz/";
//        genCode.dtoOutputRootDir = "/Users/liangchao/IdeaProjects/qjd/qjd-saas/saas-api/saas-ofc-api/";

        genCode.tables = new String[]{
                "sa_wm_warehouse_info",
                "sa_wm_stock_info",
                "sa_wm_stock_fifo_detail",
                "sa_wm_stock_lock",
                "sa_wm_stock_change_log",
                "sa_wm_storage_info",
                "sa_wm_storage_detail",
                "sa_wm_outbound_info",
                "sa_wm_outbound_detail",
                "sa_wm_transfers_info",
                "sa_wm_transfers_detail",
                "sa_wm_blitem_info",
                "sa_wm_blitem_detail",
                "sa_wm_adjust_info",
                "sa_wm_adjust_detail",
                "sa_goods_price",
                "sa_wm_order_fifo_detail",
                "sa_wm_deliver_fifo_detail"
        };
        genCode.genCode();
    }


}
