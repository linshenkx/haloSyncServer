package cn.linshenkx.halosyncserver.model.params;

import cn.linshenkx.halosyncserver.model.enums.JournalType;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * Journal param.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-4-25
 */
@Data
public class JournalParam  {

    @NotBlank(message = "内容不能为空")
    private String sourceContent;

    private JournalType type = JournalType.PUBLIC;
}
