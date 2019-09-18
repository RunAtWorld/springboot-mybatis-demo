package dev.mybatisgen;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Properties;

/**
 * 自定义 MBG
 */
public class MyCommentGenerator extends DefaultCommentGenerator {
    private boolean suppressAllComments;  //阻止生成注释
    private boolean addRemarkComments;        //是否生成数据库表的注释

    /**
     * 设置用户配置的参数
     *
     * @param properties
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        //调用父类方法保证父类方法可以正常使用
        super.addConfigurationProperties(properties);
        //获取supressAllComment参数值
        suppressAllComments =
                Boolean.parseBoolean(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
        //获取addRemarkComments参数值
        addRemarkComments =
                Boolean.parseBoolean(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));
    }

    /**
     * 给字段添加注释信息
     *
     * @param field
     * @param introspectedTable
     * @param introspectedColumn
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        //如果阻止生成注释则直接返回
        if (suppressAllComments) {
            return;
        }
        //文档注释开始
        field.addJavaDocLine("/**");
        //获取数据库字段的备注信息
        String remarks = introspectedColumn.getRemarks();
        //根据参数和备注信息判断是否添加备注信息
        if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
            String[] remarkLinesStrings = remarks.split(System.getProperty("line.separator"));
            for (String remarkLine : remarkLinesStrings) {
                field.addJavaDocLine(" * " + remarkLine);
            }
        }
        //注释中保留数据库字段名
        //field.addJavaDocLine(" * " + introspectedColumn.getActualColumnName());
        field.addJavaDocLine(" */");
    }

}
