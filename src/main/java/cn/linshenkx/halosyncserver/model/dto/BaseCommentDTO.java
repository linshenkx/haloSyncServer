package cn.linshenkx.halosyncserver.model.dto;

import cn.linshenkx.halosyncserver.model.enums.CommentStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * Base comment output dto.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-03-20
 */
@Data
@ToString
@EqualsAndHashCode
public class BaseCommentDTO  {

    private Long id;

    private String author;

    private String email;

    private String ipAddress;

    private String authorUrl;

    private String gravatarMd5;

    private String content;

    private CommentStatus status;

    private String userAgent;

    private Long parentId;

    private Boolean isAdmin;

    private Boolean allowNotification;

    private Date createTime;

    private String avatar;
}
