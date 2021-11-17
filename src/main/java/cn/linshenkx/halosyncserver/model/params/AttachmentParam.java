package cn.linshenkx.halosyncserver.model.params;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Attachment params.
 *
 * @author ryanwang
 * @date 2019/04/20
 */
@Data
public class AttachmentParam {

    @NotBlank(message = "附件名称不能为空")
    @Size(max = 255, message = "附件名称的字符长度不能超过 {max}")
    private String name;

}
