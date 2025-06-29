package jp.co.monocrea.repository;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jp.co.monocrea.entity.User;
import jp.co.monocrea.enums.OrderType;

/**
 * ユーザ情報を操作するリポジトリクラス
 */
@ApplicationScoped
public class UserRepository {

    // ソート項目
    public enum SortItem {
        ID("id"),
        USER_NAME("userName"),
        USER_NAME_KANA("userNameKana");

        private final String colName;

        SortItem(String colName) {
            this.colName = colName;
        }

        public String getValue() {
            return colName;
        }

        public static SortItem fromValue(String value) {
            for (SortItem item : SortItem.values()) {
                if (item.colName.equalsIgnoreCase(value)) {
                    return item;
                }
            }

            throw new IllegalArgumentException("ソート項目名が不正です。");
        }
    }

    private final EntityManager em;
    @Inject
    public UserRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * ユーザ情報の検索
     * @param id　ユーザID（nullの場合は検索条件に含めない）
     * @param userName　ユーザ名（nullまたは空文字の場合は検索条件に含めない）
     * @param sortItem　ソート項目（nullの場合はソートしない）
     * @param order　ソート順（nullの場合はソートしない）
     * @return ユーザ情報のリスト
     */
    public List<User> selectUser(Integer id, String userName, SortItem sortItem, OrderType order, Integer limit, Integer offset) {
        StringBuilder sqlText = new StringBuilder();
        sqlText.append("SELECT u.id, u.userName, u.userNameKana");
        sqlText.append(" FROM User AS u");

        // WHERE条件の設定
        List<String> whereCondList = new ArrayList<>();
        boolean hasIdCond = (id != null);
        boolean hasUserNameCond = (userName != null && !userName.isEmpty());

        if (hasIdCond) {
            whereCondList.add("u.id = :id");
        }

        if(hasUserNameCond) {
            whereCondList.add("(u.userName LIKE :userName OR u.userNameKana LIKE :userName)");
        }

        // WHERE句の作成
        String whereCond = "";
        if (!whereCondList.isEmpty()) {
            whereCond = " WHERE " + String.join(" AND ", whereCondList);
            sqlText.append(whereCond);
        }

        // ORDER BY句の作成
        sqlText.append(String.format(" ORDER BY u.%s %s", sortItem.getValue(), order.getValue()));

        // LIMIT/OFFSET句の作成
        sqlText.append(String.format(" LIMIT %d OFFSET %d", limit, offset));

        // SQLの作成
        TypedQuery<User> query = em.createQuery(sqlText.toString(), User.class);

        // パラメータの設定
        if(hasIdCond) {
            query.setParameter("id", id);
        }

        if(hasUserNameCond) {
            query.setParameter("userName", "%" + userName + "%");
        }
       
        return query.getResultList();
    }

    /**
     * ユーザ情報の登録
     * @param user 登録するユーザ情報
     * @return 登録されたユーザ情報（IDが設定されている）
     */
    public User insertUser(User user){
        em.persist(user);

        // em.persist()で例外発生してない場合は、IDが設定されているはず
        return user;
    }
    
    /**
     * ユーザ情報の更新
     * @param user 更新するユーザ情報（IDが設定されている必要がある）
     * @return 更新されたユーザ情報
     * @throws IllegalArgumentException 更新対象が存在しない場合
     */
    public User updateUser(User user){
        // 更新対象が存在しない場合は例外が発生させる
        // JPQLのほうがSQL呼び出し回数が少なくて済むが、統一感を出すためにJPAを使用
        if (user.getId() == null || em.find(User.class, user.getId()) == null) {
            throw new IllegalArgumentException(String.format("更新対象が存在しません。(ユーザID：%s)", String.valueOf(user.getId())));
        }

        return em.merge(user);
    }

    /**
     * ユーザ情報の削除
     * @param id 削除するユーザID
     * @throws IllegalArgumentException 削除対象が存在しない場合
     */
    public void deleteUser(int id) {
        // 削除対象が存在しない場合は例外が発生する
        em.remove(em.getReference(User.class, id));
    }

    /**
     * ユーザ検索該当件数の取得
     * @param id ユーザID
     * @param userName ユーザ名
     * @return 該当件数
     */
    public long getUserCount(Integer id , String userName){
        StringBuilder sqlText = new StringBuilder();
        sqlText.append("SELECT COUNT(u.id)");
        sqlText.append(" FROM User AS u");

        // WHERE条件の設定
        List<String> whereCondList = new ArrayList<>();
        boolean hasIdCond = (id != null);
        boolean hasUserNameCond = (userName != null && !userName.isEmpty());

        if (hasIdCond) {
            whereCondList.add("u.id = :id");
        }

        if(hasUserNameCond) {
            whereCondList.add("(u.userName LIKE :userName OR u.userNameKana LIKE :userName)");
        }

        // WHERE句の作成
        String whereCond = "";
        if (!whereCondList.isEmpty()) {
            whereCond = " WHERE " + String.join(" AND ", whereCondList);
            sqlText.append(whereCond);
        }

        // SQLの作成
        TypedQuery<Long> query = em.createQuery(sqlText.toString(), Long.class);

        // パラメータの設定
        if(hasIdCond) {
            query.setParameter("id", id);
        }

        if(hasUserNameCond) {
            query.setParameter("userName", "%" + userName + "%");
        }

        return query.getSingleResult();
    }
}