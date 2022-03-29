package com.lc.springboot.gencode.saas2;

import com.lc.springboot.gencode.saas.AbstractSaasBaseGenCode;

/**
 * 授信
 *
 * @author liangc
 */
public class SaasCreditCodeGenerator_Saas2 extends AbstractSaasBaseGenCode {

    public static void main(String[] args) {
        AbstractSaasBaseGenCode genCode = new SaasCreditCodeGenerator_Saas2();
        genCode.projectName = "saas-credit";
        genCode.rootUrlMapping = "credit";
        genCode.db_env = DB_ENV.TEST_A;

//        genCode.outputRootDir = "/Users/liangchao/tmp/qjd-saas/saas-biz/";
//        genCode.dtoOutputRootDir = "/Users/liangchao/tmp/qjd-saas/saas-api/saas-credit-api/";

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
                "upd_datetime",
                "tenant_id"};

//        genCode.outputRootDir = "/Users/liangchao/tmp/qjd-saas/saas-biz/";
//        genCode.dtoOutputRootDir = "/Users/liangchao/tmp/qjd-saas/saas-api/saas-credit-api/";


        genCode.outputRootDir = "/Users/liangchao/IdeaProjects/qjd/qjd-saas/saas-biz/";
        genCode.dtoOutputRootDir = "/Users/liangchao/IdeaProjects/qjd/qjd-saas/saas-api/saas-credit-api/";

//        genCode.tables = new String[]{"sa_credit_rule_config", "sa_credit_customer_level_rule_rel"};

        genCode.tables = new String[]{
                "sa_credit_voucher",
                "sa_credit_voucher_item_rel"};
        genCode.genCode();
    }


}
