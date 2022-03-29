package com.lc.springboot.gencode.saas2;

import com.lc.springboot.gencode.saas.AbstractSaasBaseGenCode;

/**
 * 授信
 *
 * @author liangc
 */
public class SaasAccountCodeGenerator_Saas2 extends AbstractSaasBaseGenCode {

    public static void main(String[] args) {
        AbstractSaasBaseGenCode genCode = new SaasAccountCodeGenerator_Saas2();

        genCode.projectName = "saas-account";
        genCode.rootUrlMapping = "account";
        genCode.db_env = DB_ENV.TEST_A;

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
                "tenant_id"
        };

        genCode.outputRootDir = "/Users/liangchao/tmp/qjd-saas/";
        genCode.dtoOutputRootDir = "/Users/liangchao/tmp/qjd-saas/saas-api/saas-account-api/";

//        genCode.outputRootDir = "/Users/liangchao/IdeaProjects/qjd/qjd-saas/";
//        genCode.dtoOutputRootDir = "/Users/liangchao/IdeaProjects/qjd/qjd-saas/saas-api/saas-account-api/";
////
//        genCode.tables = new String[]{
//                "sa_account",
//                "sa_account_journal",
//                "sa_account_type",
//                "sa_account_certificate",
//
//                "sa_in_out_bill",
//                "sa_label",
//                "sa_product_label_relation",
//                "sa_account_type_label_relation"
//        };

        genCode.tables = new String[]{"sa_in_out_bill_detail"};
        genCode.genCode();
    }

}
