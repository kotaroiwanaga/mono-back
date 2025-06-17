package jp.co.monocrea.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name", nullable = false, length = 100)
    private String userName;

    @Column(name = "user_name_kana", nullable = false, length = 100)
    private String userNameKana;

    public User() {}

    public User(Integer id, String userName, String userNameKana) {
        this.id = id;
        this.userName = userName;
        this.userNameKana = userNameKana;
    }

    public Integer getId() { return id; }
    public String getUserName() { return userName; }
    public String getUserNameKana() { return userNameKana; }

    public void setId(Integer id) { this.id = id; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setUserNameKana(String userNameKana) { this.userNameKana = userNameKana; }
}
