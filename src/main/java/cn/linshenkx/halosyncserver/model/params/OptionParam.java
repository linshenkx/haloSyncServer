package cn.linshenkx.halosyncserver.model.params;

import cn.linshenkx.halosyncserver.model.enums.OptionType;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Optional param.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-03-20
 */
@Data
public class OptionParam {

    @NotBlank(message = "Option key must not be blank")
    @Size(max = 100, message = "Length of option key must not be more than {max}")
    private String key;

    private String value;

    private OptionType type;
}
