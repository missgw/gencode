package com.lc.springboot.gencode.saas2;

import com.lc.springboot.gencode.saas.AbstractSaasBaseGenCode;

/**
 * 风险评估
 *
 * @author liangc
 */
public class SaasRiskExpertCodeGenerator_Saas2 extends AbstractSaasBaseGenCode {

    public static void main(String[] args) {
        AbstractSaasBaseGenCode genCode = new SaasRiskExpertCodeGenerator_Saas2();

        genCode.projectName = "risk-expert";
        genCode.rootUrlMapping = "riskexpert";
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

        genCode.outputRootDir = "/Users/liangchao/tmp/qjd-saas/saas-biz/";
        genCode.dtoOutputRootDir = "/Users/liangchao/tmp/qjd-saas/saas-api/saas-risk-expert-api/";

//        genCode.outputRootDir = "/Users/liangchao/IdeaProjects/qjd/qjd-saas/";
//        genCode.dtoOutputRootDir = "/Users/liangchao/IdeaProjects/qjd/qjd-saas/saas-api/saas-account-api/";


        genCode.tables = new String[]{
//                "sa_re_credit_evaluation",
//                "sa_re_history_shipment",
//                "sa_re_evaluate_upload",
//                "sa_re_corporation_authorization",
//                "sa_re_personal_authorization",
//                "sa_re_last_evaluation",
//                "sa_re_risk_event",
//                "sa_re_attention_customer",
//                "sa_re_user_attention",
//                "sa_re_risk_event_distribution",
//                "sa_re_risk_msg"

//                "sa_re_risk_opinion_distribution",
//                "sa_re_risk_news_public_opinion"
//                "sa_re_model_rule"
                "sa_re_risk_time_records"
        };
        genCode.genCode();
    }

}
