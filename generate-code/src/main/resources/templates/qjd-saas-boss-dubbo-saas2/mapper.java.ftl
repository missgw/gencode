package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ${table.comment!} Mapper 接口
 * @author ${author}
 * @date ${date}
 * @version ${version}
 */

<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

  /**
   * 根据主键物理删除
   *
   * @param idList 主键编号列表
   * @return 影响数据条数
   */
  int physicallyDelete(@Param("idList") List<Long> idList);

}
</#if>
