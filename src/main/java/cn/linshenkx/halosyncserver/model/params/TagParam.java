package cn.linshenkx.halosyncserver.model.params;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Tag param.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-03-20
 */
@Data
public class TagParam {

    @NotBlank(message = "标签名称不能为空")
    @Size(max = 255, message = "标签名称的字符长度不能超过 {max}")
    private String name;

    @Size(max = 255, message = "标签别名的字符长度不能超过 {max}")
    private String slug;

    @Size(max = 1023, message = "封面图链接的字符长度不能超过 {max}")
    private String thumbnail;

}
