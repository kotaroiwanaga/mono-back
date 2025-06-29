package jp.co.monocrea.service;

import java.util.ArrayList;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jp.co.monocrea.dto.UserSearchResult;
import jp.co.monocrea.entity.User;
import jp.co.monocrea.repository.UserRepository;
import jp.co.monocrea.repository.UserRepository.SortItem;
import jp.co.monocrea.enums.OrderType;

/**
 * ユーザ情報を操作するサービスクラス
 */
@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Transactional
    public UserSearchResult searchUser(Integer id, String userName, String sort, String order, Integer limit, Integer page) {
        int offset = (page - 1) * limit; // ページ番号は1から始まるため、オフセットはページ番号から1を引いて計算

        long cnt = userRepository.getUserCount(id, userName);
        long maxPage = (long)Math.ceil((double)cnt / limit);

        UserSearchResult res = new UserSearchResult();
        res.setUserList(new ArrayList<>());
        res.setSearchId(id);
        res.setSearchUserName(userName);
        res.setSortItemName(sort);
        res.setOrderType(order);
        res.setLimit(limit);
        res.setPageNo(page);
        res.setMaxPageNo(maxPage);
        res.setTotalCnt(cnt);

        if(page <= maxPage){
            // 取得可能なページのときのみ検索する
            res.setUserList(userRepository.selectUser(id, userName, SortItem.fromValue(sort), OrderType.fromKey(order), limit, offset));
        }

        return res;
    }

    @Transactional
    public User createUser(String userName, String userNameKana) {
        User user = new User(null, userName, userNameKana);
        return userRepository.insertUser(user);
    }

    @Transactional
    public User updateUser(User user) {
        return userRepository.updateUser(user);
    }

    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteUser(id);
    }
}
