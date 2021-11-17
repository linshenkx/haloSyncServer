package cn.linshenkx.halosyncserver.model.params;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;

/**
 * Install parameters.
 *
 * @author johnniang
 * @date 3/19/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InstallParam extends UserParam {

    /**
     * Blog locale.
     */
    private String locale = "zh";

    /**
     * Blog title.
     */
    @NotBlank(message = "博客名称不能为空")
    private String title;

    /**
     * Blog url.
     */
    private String url;

}
