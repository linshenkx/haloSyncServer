package cn.linshenkx.halosyncserver.model.params;

import cn.linshenkx.halosyncserver.model.enums.JournalType;
import lombok.Data;

/**
 * Journal query params.
 *
 * @author ryanwang
 * @date 2019/04/26
 */
@Data
public class JournalQuery {

    /**
     * Keyword.
     */
    private String keyword;

    private JournalType type;
}
