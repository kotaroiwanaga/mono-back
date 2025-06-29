package jp.co.monocrea.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jp.co.monocrea.dto.UserSearchResult;
import jp.co.monocrea.dto.UserCreateRequest;
import jp.co.monocrea.entity.User;
import jp.co.monocrea.enums.OrderType;
import jp.co.monocrea.repository.UserRepository.SortItem;
import jp.co.monocrea.service.UserService;

/**
 * ユーザ情報を操作するコントローラクラス
 */
@Path("/user")
public class UserController {
    private final UserService userService;
    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchUser(
            @QueryParam("id") Integer id,
            @QueryParam("userName") String userName,
            @QueryParam("_sort") String sort,
            @QueryParam("_order") String order,
            @QueryParam("_page") Integer page,
            @QueryParam("_limit") Integer limit
            ){
        try {
            if (sort == null) {
                sort = SortItem.ID.getValue(); // デフォルトはユーザＩＤ
            }

            if(order == null){
                order = OrderType.ASC.getValue(); // デフォルトは昇順
            }

            if(page == null || page < 1) {
                page = 1; // デフォルトページは1
            }

            if(limit == null || limit < 1) {
                limit = 10; // デフォルトの取得件数は10
            }

            UserSearchResult result = userService.searchUser(id, userName, sort, order, limit, page);
            return Response.ok(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("ユーザの検索に失敗しました。: " + e.getMessage())  // TODO: エラー時レスポンス構成は要検討
                           .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@Valid UserCreateRequest request) {
        try {
            User user = userService.createUser(request.getUserName(), request.getUserNameKana());
            return Response.status(Response.Status.CREATED)
                           .entity(user)
                           .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("ユーザの作成に失敗しました。: " + e.getMessage())  // TODO: エラー時レスポンス構成は要検討
                           .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") Integer id, @Valid UserCreateRequest request) {
        if( id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
            .entity("ユーザIDが不正です。") // TODO: エラー時レスポンス構成は要検討
            .build();
        }

        try {
            User user = new User();
            user.setId(id); 
            user.setUserName(request.getUserName());
            user.setUserNameKana(request.getUserNameKana());

            user = userService.updateUser(user);
            return Response.ok(user).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("ユーザの更新に失敗しました。: " + e.getMessage())  // TODO: エラー時レスポンス構成は要検討
                           .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") Integer id) {
        if (id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("ユーザIDが不正です。") // TODO: エラー時レスポンス構成は要検討
                           .build();
        }

        try {
            userService.deleteUser(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("ユーザの削除に失敗しました。: " + e.getMessage())  // TODO: エラー時レスポンス構成は要検討
                           .build();
        }
    }
}
