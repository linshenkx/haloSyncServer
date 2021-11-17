package cn.linshenkx.halosyncserver.model.dto.post;

import cn.linshenkx.halosyncserver.model.enums.PostEditorType;
import cn.linshenkx.halosyncserver.model.enums.PostStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * Base post minimal output dto.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-03-19
 */
@Data
@ToString
@EqualsAndHashCode
public class BasePostMinimalDTO {

    private Integer id;

    private String title;

    private PostStatus status;

    private String slug;

    private PostEditorType editorType;

    private Date updateTime;

    private Date createTime;

    private Date editTime;

    private String metaKeywords;

    private String metaDescription;

    private String fullPath;
}
