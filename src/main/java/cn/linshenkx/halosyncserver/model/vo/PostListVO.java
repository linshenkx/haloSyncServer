package cn.linshenkx.halosyncserver.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.linshenkx.halosyncserver.model.dto.CategoryDTO;
import cn.linshenkx.halosyncserver.model.dto.TagDTO;
import cn.linshenkx.halosyncserver.model.dto.post.BasePostSimpleDTO;

import java.util.List;
import java.util.Map;

/**
 * Post list vo.
 *
 * @author johnniang
 * @author guqing
 * @author ryanwang
 * @date 2019-03-19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostListVO extends BasePostSimpleDTO {

    private Long commentCount;

    private List<TagDTO> tags;

    private List<CategoryDTO> categories;

    private Map<String, Object> metas;
}
