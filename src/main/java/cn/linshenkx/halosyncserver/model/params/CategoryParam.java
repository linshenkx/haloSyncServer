package cn.linshenkx.halosyncserver.model.params;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Category param.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-03-21
 */
@Data
public class CategoryParam {

    @NotBlank(message = "分类名称不能为空")
    @Size(max = 255, message = "分类名称的字符长度不能超过 {max}")
    private String name;

    @Size(max = 255, message = "分类别名的字符长度不能超过 {max}")
    private String slug;

    @Size(max = 100, message = "分类描述的字符长度不能超过 {max}")
    private String description;

    @Size(max = 1023, message = "封面图链接的字符长度不能超过 {max}")
    private String thumbnail;

    @Size(max = 255, message = "分类密码的字符长度不能超过 {max}")
    private String password;

    private Integer parentId = 0;

}
