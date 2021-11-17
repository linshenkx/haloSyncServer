package cn.linshenkx.halosyncserver.model.params;

import cn.linshenkx.halosyncserver.model.enums.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Post query.
 *
 * @author johnniang
 * @date 4/10/19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostQuery {

    /**
     * Keyword.
     */
    private String keyword;

    /**
     * Post status.
     */
    private PostStatus status;

    /**
     * Category id.
     */
    private Integer categoryId;

}
