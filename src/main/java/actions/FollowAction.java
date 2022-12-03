package actions;
import java.io.IOException;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.FollowView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;
import services.FollowService;

/**
 * フォローに関する処理を行うActionクラス
 *
 */
public class FollowAction  extends ActionBase {

    private FollowService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new FollowService();

        //メソッドを実行
        invoke();
        service.close();
    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //指定されたページ数の一覧画面に表示するフォローデータを取得
   //     int page = getPage();
     //   List<FollowView> follows = service.getMinePerPage(page);

        //全フォローデータの件数を取得
       // long followsCount = service.countAllMine();

       // putRequestScope(AttributeConst.FOLLOWS, follows); //取得したフォローデータ
       // putRequestScope(AttributeConst.FOLLOW_COUNT, followsCount); //全てのフォローデータの件数
       // putRequestScope(AttributeConst.PAGE, page); //ページ数
       // putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_FOL_INDEX);
    }

    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            //パラメータの値をもとにフォロー情報のインスタンスを作成する
            FollowView fv = new FollowView(
                    null,
                    ev,//ログインしている従業員が、フォローを登録する
                    getRequestParam(AttributeConst.follo));



                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_FOLLOW, ForwardConst.CMD_INDEX);
            }
        }
    }




