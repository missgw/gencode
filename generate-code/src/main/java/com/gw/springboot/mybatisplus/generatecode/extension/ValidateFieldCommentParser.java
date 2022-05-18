package com.gw.springboot.mybatisplus.generatecode.extension;

import com.gw.springboot.mybatisplus.generatecode.config.po.TableField;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字段解析器
 *
 * @author gewei
 * @version 1.0
 * @date 2021/9/2 7:09 下午
 */
public class ValidateFieldCommentParser {

    private static final String LENGTH_F1_REGEX = "支持中文、英文、符号，最长限(\\d+)个字";
    private static final String LENGTH_F2_REGEX = "支持中文、英文、数字、符号，最长限(\\d+)个字";
    private static final String LENGTH_F3_REGEX = "限中文、英文、数字、符号，最长不超过(\\d+)个字";
    private static final String LENGTH_F4_REGEX = "限中文、英文、符号，最长限(\\d+)个字";
    private static final String LENGTH_LETTER_NUMBER_REGEX = "限英文、数字，最长不超过(\\d+)个字";
    private static final String LENGTH_CHINESE_LETTER_REGEX = "仅限中文、英文，最长限(\\d+)个字";

    /**
     * 默认限制>=0
     */
    private static final String DIGITS_F2_REGEX = "仅支持数字，精确到小数点后2位";
    private static final String DIGITS_F2_POSITIVE_REGEX = "仅支持数字，精确到小数点后2位，范围：＞0";
    private static final String DIGITS_F2_RANGE_0_100_REGEX = "仅支持数字，精确到小数点后2位，范围：0-100";
    private static final String DIGITS_F3_REGEX = "仅支持数字，精确到小数点后3位";
    private static final String POSITIVE_INTEGER_OR_ZERO_REGEX = "仅支持整数，≥0";
    private static final String POSITIVE_INTEGER_REGEX = "仅支持整数，>0";

    private static final String ONLY_NUMBERS_REGEX = "仅支持数字，无任何符号";

    private static final String VALID_DATE_STR_REGEX = "精确到日";

    private static final String ID_CARD_REGEX = "身份证号码";
    private static final String ID_CARD_2_REGEX = "身份证号";
    private static final String ID_CARD_3_REGEX = "配偶身份证号";

    private static final String MOBILE_NO_REGEX = "手机号";
    private static final String CAR_NO_REGEX = "车牌号";


    private static final String VALIDATE_GROUPS = "\", groups = ValidateField.class)";
    public static final String IMPORT_VALIDATION_CONSTRAINTS_SIZE = "import javax.validation.constraints.Size;";

    public Set<String> getImportClassList(List<TableField> fields) {
        Set<String> importClassList = new LinkedHashSet<>();

        fields.forEach(field -> {
            String comment = field.getComment();
            if (StringUtils.isNotBlank(comment) && comment.split("\\|").length >= 2) {
                String prefix = comment.split("\\|")[0].trim();
                String suffix = comment.split("\\|")[1].trim();

                if (CAR_NO_REGEX.equals(prefix)) {
                    importClassList.add("import  com.qjdchina.creditreport.common.validator.CarNo;");
                }

                // 浮点类型
                if (suffix.equals(DIGITS_F2_REGEX)
                        || suffix.equals(DIGITS_F3_REGEX)
                        || suffix.equals(DIGITS_F2_POSITIVE_REGEX)) {
                    importClassList.add("import javax.validation.constraints.Digits;");
                    importClassList.add("import javax.validation.constraints.Min;");
                }

                if (suffix.equals(DIGITS_F2_POSITIVE_REGEX)) {
                    importClassList.add("import javax.validation.constraints.Digits;");
                    importClassList.add("import javax.validation.constraints.DecimalMin;");
                }

                if (suffix.equals(DIGITS_F2_RANGE_0_100_REGEX)
                ) {
                    importClassList.add("import javax.validation.constraints.Min;");
                    importClassList.add("import javax.validation.constraints.Max;");
                }

                // 长度限制
                else if (suffix.matches(LENGTH_F1_REGEX)
                        || suffix.matches(LENGTH_F2_REGEX)
                        || suffix.matches(LENGTH_F3_REGEX)
                        || suffix.matches(LENGTH_F4_REGEX)
                ) {
                    importClassList.add(IMPORT_VALIDATION_CONSTRAINTS_SIZE);
                }
                // 仅支持数字
                else if (suffix.equals(ONLY_NUMBERS_REGEX)) {
                    importClassList.add("import com.qjdchina.creditreport.common.validator.OnlyNumbers;");
                }
                // 日期格式，yyyy-MM-dd
                else if (suffix.equals(VALID_DATE_STR_REGEX)) {
                    importClassList.add("import com.qjdchina.creditreport.common.validator.ValidDateStr;");
                }
                //仅支持整数，≥0
                else if (suffix.equals(POSITIVE_INTEGER_OR_ZERO_REGEX)) {
                    importClassList.add("import com.qjdchina.creditreport.common.validator.PositiveIntegerOrZero;");
                }
                //仅支持整数，>0
                else if (suffix.equals(POSITIVE_INTEGER_REGEX)) {
                    importClassList.add("import com.qjdchina.creditreport.common.validator.PositiveInteger;");
                }
                // 限制英文数字,最长不超过xx个字
                else if (suffix.equals(LENGTH_LETTER_NUMBER_REGEX)) {
                    importClassList.add("import com.qjdchina.creditreport.common.validator.OnlyLetterNumbers;");
                    importClassList.add(IMPORT_VALIDATION_CONSTRAINTS_SIZE);
                }
                //仅限中文、英文，最长限xx个字
                else if (suffix.equals(LENGTH_CHINESE_LETTER_REGEX)) {
                    importClassList.add("import com.qjdchina.creditreport.common.validator.OnlyChineseLetter;");
                    importClassList.add(IMPORT_VALIDATION_CONSTRAINTS_SIZE);
                }
            }
            // 身份证号
            else if (ID_CARD_REGEX.equals(comment)
                    || ID_CARD_2_REGEX.equals(comment)
                    || ID_CARD_3_REGEX.equals(comment)
            ) {
                importClassList.add("import com.qjdchina.creditreport.common.validator.IdCard;");
            }
            // 手机号
            else if (MOBILE_NO_REGEX.equals(comment)) {
                importClassList.add("import com.qjdchina.creditreport.common.validator.MobileNo;");
            }
        });

        return importClassList;
    }

    public String getFieldValidatorAnnotation(TableField field) {

        String comment = field.getComment();
        String result = "";
        if (StringUtils.isNotBlank(comment) && comment.split("\\|").length >= 2) {
//            String normalComment = comment.split("\\|")[0].trim();
            String normalComment = removeUnit(comment);
            String suffix = comment.split("\\|")[1].trim();

            if (CAR_NO_REGEX.equals(normalComment)) {
                result = "@CarNo(message = \"" + normalComment + "格式不正确" + VALIDATE_GROUPS;
            }

            if (suffix.equals(DIGITS_F2_REGEX)) {
                result = "@Digits(integer = 15,fraction = 2, message = \"" + normalComment + suffix + VALIDATE_GROUPS;
                result += minEqualZeroAnnotation(normalComment);
            } else if (suffix.equals(DIGITS_F2_POSITIVE_REGEX)) {
                result = "@Digits(integer = 15,fraction = 2, message = \"" + normalComment + suffix + VALIDATE_GROUPS;
                result += "\n    @DecimalMin(value = \"0.01\", message = \"" + normalComment + "的值必须大于0" + VALIDATE_GROUPS;
            } else if (suffix.equals(DIGITS_F2_RANGE_0_100_REGEX)) {
                result = "@Digits(integer = 3,fraction = 2, message = \"" + normalComment + suffix + VALIDATE_GROUPS;
                result += minEqualZeroAnnotation(normalComment);
                result += "\n    @Max(value = 100, message = \"" + normalComment + "的值不能超过100" + VALIDATE_GROUPS;
            } else if (suffix.equals(DIGITS_F3_REGEX)) {
                result = "@Digits(integer = 15,fraction = 3, message = \"" + normalComment + suffix + VALIDATE_GROUPS;
                result += minEqualZeroAnnotation(normalComment);
            } else if (suffix.matches(LENGTH_F1_REGEX)) {
                String number = getNumber(suffix, LENGTH_F1_REGEX);
                result = sizeAnnotation(normalComment, number);
            } else if (suffix.matches(LENGTH_F2_REGEX)) {
                String number = getNumber(suffix, LENGTH_F2_REGEX);
                result = sizeAnnotation(normalComment, number);
            } else if (suffix.matches(LENGTH_F3_REGEX)) {
                String number = getNumber(suffix, LENGTH_F3_REGEX);
                result = sizeAnnotation(normalComment, number);
            } else if (suffix.matches(LENGTH_F4_REGEX)) {
                String number = getNumber(suffix, LENGTH_F4_REGEX);
                result = sizeAnnotation(normalComment, number);
            } else if (suffix.equals(ONLY_NUMBERS_REGEX)) {
                result = "@OnlyNumbers(message = \"" + normalComment + "仅支持数字" + VALIDATE_GROUPS;
            } else if (suffix.equals(VALID_DATE_STR_REGEX)) {
                result = "@ValidDateStr(message = \"" + normalComment + "请填写正确的日期，精确到日" + VALIDATE_GROUPS;
            } else if (suffix.equals(POSITIVE_INTEGER_OR_ZERO_REGEX)) {
                result = "@PositiveIntegerOrZero(message = \"" + normalComment + POSITIVE_INTEGER_OR_ZERO_REGEX + VALIDATE_GROUPS;
            } else if (suffix.equals(POSITIVE_INTEGER_REGEX)) {
                result = "@PositiveInteger(message = \"" + normalComment + POSITIVE_INTEGER_REGEX + VALIDATE_GROUPS;
            } else if (suffix.equals(LENGTH_LETTER_NUMBER_REGEX)) {
                String number = getNumber(suffix, LENGTH_LETTER_NUMBER_REGEX);
                result = sizeAnnotation(normalComment, number);
                result += "\n    @OnlyLetterNumbers(message = \"" + normalComment + "仅支持英文、数字" + VALIDATE_GROUPS;
            } else if (suffix.equals(LENGTH_CHINESE_LETTER_REGEX)) {
                String number = getNumber(suffix, LENGTH_CHINESE_LETTER_REGEX);
                result = sizeAnnotation(normalComment, number);
                result += "\n    @OnlyChineseLetter(message = \"" + normalComment + "仅支持中文、英文" + VALIDATE_GROUPS;
            }
        } else if (ID_CARD_REGEX.equals(comment)
                || ID_CARD_2_REGEX.equals(comment)
                || ID_CARD_3_REGEX.equals(comment)
        ) {
            result = "@IdCard(message = \"请填写正确的身份证号码" + VALIDATE_GROUPS;
        }
        // 手机号
        else if (MOBILE_NO_REGEX.equals(comment)) {
            result = "@MobileNo(message = \"请填写正确的手机号码" + VALIDATE_GROUPS;
        }

        return result;
    }

    private String minEqualZeroAnnotation(String normalComment) {
        return "\n    @Min(value = 0, message = \"" + normalComment + "的值必须大于等于0" + VALIDATE_GROUPS;
    }

    private String sizeAnnotation(String normalComment, String number) {
        return "@Size(max = " + number + ", message = \"" + normalComment + "最长不能超过" + number + "个字\")";
    }

    /**
     * 去掉单位字样的信息，处理格式样例
     * （单位：XXX）
     * （单位:XXX）
     * (单位:xxx)
     * (单位：xxx)
     * ...
     */
    public String removeUnit(String comment) {
        if (StringUtils.isEmpty(comment)) {
            return comment;
        }

        comment = comment.split("\\|")[0].trim();

        comment = comment.replace("(", "（");
        comment = comment.replace(")", "）");
        comment = comment.replace(":", "：");

        String regex = "(\\S+)(（单位：\\S+）)";
        if (comment.matches(regex)) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(comment);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }

        regex = "(\\S+)(（\\S+）)";
        if (comment.matches(regex)) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(comment);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return comment;
    }

    private String getNumber(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }


    public static void main(String[] args) {
        ValidateFieldCommentParser validateFieldCommentParser = new ValidateFieldCommentParser();
//        //test case2
//        String comment = "家庭资产（单位：万元）";
//        System.out.println(validateFieldCommentParser.removeUnit(comment));
//
//        //test case3
//        comment = "家庭资产2（万元）";
//        System.out.println(validateFieldCommentParser.removeUnit(comment));

        // test case4
        String comment = "日均负债比(单位：%) | 仅支持数字，精确到小数点后2位";
        System.out.println(validateFieldCommentParser.removeUnit(comment));

    }
}
