package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.FollowView;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.FollowService;
import services.ReportService;

/**
 * フォローに関する処理を行うActionクラス
 *
 */
public class FollowAction extends ActionBase {

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


        //セッションからログイン中の従業員情報を取得
        EmployeeView loginEmployee = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //ログイン中に従業員がフォローしたフォロー従業員を、指定されたページ数の一覧画面に表示する分取得する
             int page = getPage();
           List<FollowView> follows = service.getMinePerPage(loginEmployee,page);


         //ログイン中の従業員がフォローした従業員データの件数を取得
         long followsCount = service.countAllMine(loginEmployee);


         putRequestScope(AttributeConst.REP_COUNT, followsCount); //フォローした従業員が作成した日報の数
         putRequestScope(AttributeConst.FOLLOWS, follows); //取得したフォローデータ
         putRequestScope(AttributeConst.FOLLOW_COUNT, followsCount); //ログイン中の従業員がフォローした従業員の数
         putRequestScope(AttributeConst.PAGE, page); //ページ数
         putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_FOL_INDEX);
    }


    public void aiueo() throws ServletException, IOException {


        //セッションからログイン中の従業員情報を取得
        EmployeeView loginEmployee = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //ログイン中に従業員がフォローしたフォロー従業員を、指定されたページ数の一覧画面に表示する分取得する
             int page = getPage();
           List<FollowView> followsaiueo = service.getMinePerPageNarabikae(loginEmployee,page);


         //ログイン中の従業員がフォローした従業員データの件数を取得
         long followsCount = service.countAllMine(loginEmployee);


         putRequestScope(AttributeConst.REP_COUNT, followsCount); //フォローした従業員が作成した日報の数
         putRequestScope(AttributeConst.FOLLOWS, followsaiueo); //取得したフォローデータ
         putRequestScope(AttributeConst.FOLLOW_COUNT, followsCount); //ログイン中の従業員がフォローした従業員の数
         putRequestScope(AttributeConst.PAGE, page); //ページ数
         putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

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

            //  //idを条件に従業員データを取得する
            EmployeeView fol = service.findOne(toNumber(getRequestParam(AttributeConst.EMP_ID)));
            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);
            //パラメータの値をもとにフォロー情報のインスタンスを作成する
            FollowView fv = new FollowView(
                    null,
                    fol,//フォローする従業員を取得
                    ev); //ログインしている従業員

            //フォロー情報登録
            List<String>  errors = service.create(fv);
            if (errors.size() > 0) {
                //登録中にエラーがあった場合

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.FOLLOW, fv); //取得した従業員情報
            putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

            //新規登録画面を再表示
            forward(ForwardConst.FW_REP_NEW);
            } else {
                //登録中にエラーがなかった場合

            //セッションに登録完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_EMP, ForwardConst.CMD_INDEX);
        }
    }

    public void destroy() throws ServletException, IOException {



            //idを条件にフォロー従業員データを削除する
            service.destroy(toNumber(getRequestParam(AttributeConst.FOL_ID)));

            //セッションに削除完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_FOLLOW, ForwardConst.CMD_INDEX);
        }





    private ReportService fservice;

    /**
     * メソッドを実行する
     */
    public void fprocess() throws ServletException, IOException {

        fservice = new ReportService();

        //メソッドを実行
        invoke();
        fservice.close();
    }
    /**
     * フォローしている従業員の日報詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        //idを条件に日報データを取得する
        ReportView rv = fservice.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));

        if (rv == null) {
            //該当の日報データが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.REPORT, rv); //取得した日報データ

            //詳細画面を表示
            forward(ForwardConst.FW_REP_SHOW);
        }

    }

}