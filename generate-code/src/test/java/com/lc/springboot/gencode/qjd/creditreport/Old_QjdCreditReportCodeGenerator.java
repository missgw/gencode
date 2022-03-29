package com.lc.springboot.gencode.qjd.creditreport;

import com.lc.springboot.gencode.saas.AbstractSaasBaseGenCode;

/**
 * 仟金顶授信报告
 *
 * @author liangc
 */
public class Old_QjdCreditReportCodeGenerator extends AbstractSaasBaseGenCode {

    public static void main(String[] args) {

        AbstractSaasBaseGenCode genCode = new Old_QjdCreditReportCodeGenerator();

        // 指定数据库
        genCode.dbUrl = "jdbc:mysql://localhost:3306/credit";

//        // dev环境
//        genCode.dbUrl = "jdbc:mysql://10.1.1.42:3306/bigfrog";
//        genCode.dbUserName = "root";
//        genCode.dbPassword = "qjd123";

        // 指定模板
        genCode.templateDir = "qjd-credit-report";
        // 根包名
        genCode.rootPackageName = "com.qjdchina.";
        // 根实体类
        genCode.superEntityClassName = "";
        // 根mapper类
        genCode.superMapperClass = "com.qjdchina.creditreport.mapper.base.ExtBaseMapper";
        // 是否启动枚举类生成（通过表字段的注释生成，前提注释要书写规范）
//        genCode.enableEnum = false;
        // 公共字段
        genCode.commonFieldsArray = new String[]{};
//        genCode.commonFieldsArray = new String[]
//                {"id", "creator", "modifier", "create_time", "update_time", "ins_datetime",
//                        "upd_datetime", "is_deleted", "opt_version"};

        //
        genCode.getRootUrlMappingPattern = "";

        genCode.projectName = "credit-report-service";
        genCode.rootUrlMapping = "creditreport";

//        genCode.outputRootDir = "/Users/liangchao/IdeaProjects/gitee/qjd-credit-report/";
        genCode.outputRootDir = "/Users/liangchao/tmp/qjd-credit-report/";
        //genCode.dtoOutputRootDir = "/Users/liangchao/IdeaProjects/gitee/qjd-credit-report/credit-report-service-api/";


        genCode.tablePrefix = "";
        genCode.likeTable = "";

//        genCode.tables = new String[]{"cr_corp_finance_item", "cr_corp_finance_debt",
//                "cr_corp_finance_profit",
//                "cr_bank_water_detail_base", "cr_bank_water_detail_item", "cr_bank_water_simple",
//                "cr_person_credit_report_base", "cr_house", "cr_vehicle", "cr_land", "cr_person_id_card"};

//        genCode.tables = new String[]{
//                "cif_credit_report",
//                "cif_credit_report_field",
//                "cif_credit_report_items",
//                "cif_credit_report_modules",
//                "cif_credit_report_options",
//                "cif_credit_report_reply",
//                "cif_credit_report_template",
//                "cif_diligence_report",
//                "cif_diligence_report_company"
//        };

        genCode.tables = new String[]{
                "cr_migrate_process"
        };
//        // 梁超
//        genCode.tables = new String[]{"cr_credit_report", "cr_person_base", "cr_person_emergency_contact",
//                "cr_person_id_card", "cr_person_marital", "cr_person_credit_report_base", "cr_person_credit_report_loan_item",
//                "cr_person_credit_report_ext_guaranty", "cr_analysis_recommend", "cr_house",
//                "cr_vehicle", "cr_land", "cr_corp_finance_debt", "cr_corp_finance_item",
//                "cr_corp_finance_profit", "cr_corp_finance_invoiced_sales", "cr_bank_water_base",
//                "cr_bank_water_detail_item", "cr_file", "cr_diligence_report", "cr_diligence_report_company_base"};
////

//        // 檀日新
//        genCode.tables = new String[]{"cr_corporation_base", "cr_corp_business_license", "cr_corp_lease_contract",
//                "cr_corp_brand_auth", "cr_corp_credit_report_base", "cr_corp_credit_report_summary", "cr_corp_loan_details",
//                "cr_corp_guarantee_summary", "cr_corp_guarantee_situation", "cr_corp_legal_proceedings", "cr_corp_shareholder_structure",
//                "cr_corp_shareholder_change_record", "cr_corp_business_tax_investigate", "cr_corp_equity_pledge", "cr_corp_affiliates",
//                "cr_corp_past_historical_items", "cr_corp_tax_information", "cr_corp_salary_information", "cr_corp_account_directory",
//                "cr_corp_ecommerce_platform_account", "cr_corp_electricity_bill"};

//        genCode.tables = new String[]{};

        genCode.genCode();
    }

}
