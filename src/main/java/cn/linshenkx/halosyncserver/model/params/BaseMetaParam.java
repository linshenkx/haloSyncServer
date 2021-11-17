package cn.linshenkx.halosyncserver.model.params;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Base meta param.
 *
 * @author ryanwang
 * @author ikaisec
 * @date 2019-08-04
 */
@Data
public abstract class BaseMetaParam<META>{

    @NotBlank(message = "文章 id 不能为空")
    private Integer postId;

    @NotBlank(message = "Meta key 不能为空")
    @Size(max = 1023, message = "Meta key 的字符长度不能超过 {max}")
    private String key;

    @NotBlank(message = "Meta value 不能为空")
    @Size(max = 1023, message = "Meta value 的字符长度不能超过 {max}")
    private String value;

}
