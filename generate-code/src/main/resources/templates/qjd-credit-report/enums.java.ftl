package ${package.Enums};

<#--<#assign enumClassName="${enumCurrentTable.entityName}${enumCurrentTableField.propertyName?cap_first}Enum"/>-->
/**
 * ${enumCurrentTable.comment!} ${enumCurrentFieldComment!} 枚举
 * @author ${author}
 * @date ${date}
 * @version 1.0
 */
public enum ${enumClassName} {

<#if enumsMap?exists>
    <#assign mapSize=enumsMap?size/>
    <#assign current_node_index=1/>
    <#list enumsMap?keys as key>

    /**
     * ${enumsMap[key]}
     */
    <#if enumCurrentTableField.type?contains("char")>
    ${translationMap[key]}("${key}","${enumsMap[key]}")<#if current_node_index < mapSize>,<#assign current_node_index=current_node_index+1/></#if>
    <#else>
    ${translationMap[key]}(${key},"${enumsMap[key]}")<#if current_node_index < mapSize>,<#assign current_node_index=current_node_index+1/></#if>
    </#if>
    </#list>
    ;
</#if>


<#if enumCurrentTableField.type?contains("char")>
    ${enumClassName}(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final String code;
    private final String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 通过code返回desc
     *
     * @param code 编码
     * @return 描述
     */
    String getDescByCode(String code) {
        for (${enumClassName} data : ${enumClassName}.values()) {
            if (data.code.equals(code)) {
                return data.desc;
            }
        }
        return "";
    }
<#else>
    ${enumClassName}(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;
    private final String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 通过code返回desc
     *
     * @param code 编码
     * @return 描述
     */
    String getDescByCode(Integer code) {
        for (${enumClassName} data : ${enumClassName}.values()) {
            if (data.code.equals(code)) {
                return data.desc;
            }
        }
        return "";
    }
</#if>
}
