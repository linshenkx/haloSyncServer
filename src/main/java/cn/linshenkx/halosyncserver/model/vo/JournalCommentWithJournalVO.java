package cn.linshenkx.halosyncserver.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cn.linshenkx.halosyncserver.model.dto.BaseCommentDTO;
import cn.linshenkx.halosyncserver.model.dto.JournalDTO;

/**
 * Journal comment with journal vo.
 *
 * @author johnniang
 * @date 19-4-25
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class JournalCommentWithJournalVO extends BaseCommentDTO {

    private JournalDTO journal;
}
