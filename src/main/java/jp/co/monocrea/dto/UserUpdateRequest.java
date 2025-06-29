package jp.co.monocrea.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jp.co.monocrea.constants.*;

/**
 * ユーザー更新リクエストのDTOクラス
 */
public class UserUpdateRequest {
    // TODO: 要検討：@ValidのNGとコントローラクラス側のNGでレスポンス構成が統一されてない
    @NotNull
    @Size(min = 1, max = 25)
    private String userName;

    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = RegexPattern.KATAKANA, message = "全角カタカナのみ使用可能です。")
    private String userNameKana;

    public UserUpdateRequest() {}

    public UserUpdateRequest(String userName, String userNameKana) {
        this.userName = userName;
        this.userNameKana = userNameKana;
    }

    public String getUserName() { return userName; }
    public String getUserNameKana() { return userNameKana; }

    public void setUserName(String userName) { this.userName = userName; }
    public void setUserNameKana(String userNameKana) { this.userNameKana = userNameKana; }
}
