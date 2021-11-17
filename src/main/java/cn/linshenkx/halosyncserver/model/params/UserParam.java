package cn.linshenkx.halosyncserver.model.params;

import lombok.Data;

/**
 * User param.
 *
 * @author johnniang
 * @date 3/19/19
 */
@Data
public class UserParam  {

    private String username;


    private String nickname;

    private String email;

    private String password;


    private String avatar;


    private String description;

}
