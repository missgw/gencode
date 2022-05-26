package com.gw.springboot.gencode.ecarx.testman;


import com.gw.springboot.gencode.AbstractSaasBaseGenCode;

/**
 * 授信
 *
 * @author gewei
 */
public class TestmanCodeGenerator_Saas2 extends AbstractSaasBaseGenCode {

    public static void main(String[] args) {
        AbstractSaasBaseGenCode genCode = new TestmanCodeGenerator_Saas2();

        genCode.projectName = "";
        genCode.rootUrlMapping = "v1";
        genCode.db_env = DB_ENV.DEV;

        genCode.templateDir = "excar-testman";
//        genCode.superEntityClassName = "com.qjdchina.saas.common.web.BossBaseModelV2";
        genCode.commonFieldsArray = new String[]{
//                "id"
//                "org_id",
//                "created_by",
//                "created_time",
//                "updated_time",
//                "is_deleted",
//                "ins_datetime",
//                "upd_datetime",
//                "tenant_id"
        };

        genCode.outputRootDir = "/Users/ecarx/auto_test_server/testman/";
        genCode.dtoOutputRootDir = "/Users/ecarx/auto_test_server/testman/";

//        genCode.outputRootDir = "/Users/gewei/IdeaProjects/qjd/qjd-saas/";
//        genCode.dtoOutputRootDir = "/Users/gewei/IdeaProjects/qjd/qjd-saas/saas-api/saas-account-api/";
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

        genCode.tables = new String[]{"report_property"};
        genCode.genCode();
    }

}
