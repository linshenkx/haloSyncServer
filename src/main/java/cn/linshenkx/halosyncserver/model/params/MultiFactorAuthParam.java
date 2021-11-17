package cn.linshenkx.halosyncserver.model.params;

import cn.linshenkx.halosyncserver.model.enums.MFAType;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Multi-Factor Auth Param.
 *
 * @author xun404
 * @date 2020-3-26
 */
@Data
public class MultiFactorAuthParam {

    private MFAType mfaType = MFAType.NONE;

    private String mfaKey;

    @NotBlank(message = "MFA Code不能为空")
    @Size(min = 6, max = 6, message = "MFA Code应为 {max} 位")
    private String authcode;

}
