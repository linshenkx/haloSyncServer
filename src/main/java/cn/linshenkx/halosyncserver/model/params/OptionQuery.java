package cn.linshenkx.halosyncserver.model.params;

import cn.linshenkx.halosyncserver.model.enums.OptionType;
import lombok.Data;

/**
 * Option query params.
 *
 * @author ryanwang
 * @date 2019-12-02
 */
@Data
public class OptionQuery {

    private String keyword;

    private OptionType type;
}
