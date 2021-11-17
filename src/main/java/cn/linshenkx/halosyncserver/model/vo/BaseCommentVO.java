package cn.linshenkx.halosyncserver.model.vo;

import cn.linshenkx.halosyncserver.model.dto.BaseCommentDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * Base comment vo.
 *
 * @author johnniang
 * @date 19-4-24
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BaseCommentVO extends BaseCommentDTO {

    List<BaseCommentVO> children;
}
