package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.FollowView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import constants.PropertyConst;
import services.EmployeeService;
import services.FollowService;

/**
 * 従業員に関わる処理を行うActionクラス
 *
 */
public class EmployeeAction extends ActionBase {

    private EmployeeService service;
    private FollowService fservice;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new EmployeeService();
        fservice = new FollowService();

        //メソッドを実行
        invoke();

        service.close();
        fservice.close();
    }





    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */


    public void entryNew() throws ServletException, IOException {

        if (checkAdmin()) {
        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
        putRequestScope(AttributeConst.EMPLOYEE, new EmployeeView()); //空の従業員インスタンス

        //新規登録画面を表示
        forward(ForwardConst.FW_EMP_NEW);
    }
    }
    public void index() throws ServletException, IOException {

        if (checkAdmin()) {
            //指定されたページ数の一覧画面に表示するデータを取得
            int page = getPage();
            List<EmployeeView> employees = service.getPerPage(page);

            //全ての従業員データの件数を取得
            long employeeCount = service.countAll();


            //ここから追記
            //セッションからログイン中の従業員情報を取得
            EmployeeView loginEmployee = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);
            //ログイン中に従業員がフォローしたフォロー従業員を、指定されたページ数の一覧画面に表示する分取得する
                 int folpage = getPage();
               List<FollowView> follows = fservice.getMineFolPerPage(loginEmployee,folpage);
             //ログイン中の従業員がフォローした従業員データの件数を取得
             long followsCount = fservice.countAllFolMine(loginEmployee);
             //ここまで追記

             //ここから追記
            putRequestScope(AttributeConst.REP_COUNT, followsCount); //フォローした従業員が作成した日報の数
             putRequestScope(AttributeConst.FOLLOWS, follows); //取得したフォローデータ
             //ここまで追記
           putRequestScope(AttributeConst.EMPLOYEES, employees); //取得した従業員データ
            putRequestScope(AttributeConst.EMP_COUNT, employeeCount); //全ての従業員データの件数
            putRequestScope(AttributeConst.PAGE, page); //ページ数
            putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

            //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
            String flush = getSessionScope(AttributeConst.FLUSH);
            if (flush != null) {
                putRequestScope(AttributeConst.FLUSH, flush);
                removeSessionScope(AttributeConst.FLUSH);
            }
        }

        //一覧画面を表示
        forward(ForwardConst.FW_EMP_INDEX);

    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkAdmin() && checkToken()) {

            //パラメータの値を元に従業員情報のインスタンスを作成する
            EmployeeView ev = new EmployeeView(
                    null,
                    getRequestParam(AttributeConst.EMP_CODE),
                    getRequestParam(AttributeConst.EMP_NAME),
                    getRequestParam(AttributeConst.EMP_FURIGANA),
                    getRequestParam(AttributeConst.EMP_PASS),
                    toNumber(getRequestParam(AttributeConst.EMP_ADMIN_FLG)),
                    null,
                    null,
                    AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

            //アプリケーションスコープからpepper文字列を取得
            String pepper = getContextScope(PropertyConst.PEPPER);

            //従業員情報登録
            List<String> errors = service.create(ev, pepper);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.EMPLOYEE, ev); //入力された従業員情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_EMP_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_EMP, ForwardConst.CMD_INDEX);
            }

        }
    }

    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        if (checkAdmin()) {
        //idを条件に従業員データを取得する
        EmployeeView ev = service.findOne(toNumber(getRequestParam(AttributeConst.EMP_ID)));

        if (ev == null || ev.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {

            //データが取得できなかった、または論理削除されている場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

        putRequestScope(AttributeConst.EMPLOYEE, ev); //取得した従業員情報

        //詳細画面を表示
        forward(ForwardConst.FW_EMP_SHOW);
    }
    }

    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

        if (checkAdmin()) {
        //idを条件に従業員データを取得する
        EmployeeView ev = service.findOne(toNumber(getRequestParam(AttributeConst.EMP_ID)));

        if (ev == null || ev.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {

            //データが取得できなかった、または論理削除されている場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
        putRequestScope(AttributeConst.EMPLOYEE, ev); //取得した従業員情報

        //編集画面を表示する
        forward(ForwardConst.FW_EMP_EDIT);

    }
    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkAdmin() && checkToken()) {
            //パラメータの値を元に従業員情報のインスタンスを作成する
            EmployeeView ev = new EmployeeView(
                    toNumber(getRequestParam(AttributeConst.EMP_ID)),
                    getRequestParam(AttributeConst.EMP_CODE),
                    getRequestParam(AttributeConst.EMP_NAME),
                    getRequestParam(AttributeConst.EMP_FURIGANA),
                    getRequestParam(AttributeConst.EMP_PASS),
                    toNumber(getRequestParam(AttributeConst.EMP_ADMIN_FLG)),
                    null,
                    null,
                    AttributeConst.DEL_FLAG_FALSE.getIntegerValue());


            //アプリケーションスコープからpepper文字列を取得
            String pepper = getContextScope(PropertyConst.PEPPER);

            //従業員情報更新
            List<String> errors = service.update(ev, pepper);

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.EMPLOYEE, ev); //入力された従業員情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_EMP_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_EMP, ForwardConst.CMD_INDEX);
            }
        }
    }

    /**
     * 論理削除を行う
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkAdmin() && checkToken()) {

            //idを条件に従業員データを論理削除する
            service.destroy(toNumber(getRequestParam(AttributeConst.EMP_ID)));

            //セッションに削除完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_EMP, ForwardConst.CMD_INDEX);
        }
    }

    private boolean checkAdmin() throws ServletException, IOException {

        //セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //管理者でなければエラー画面を表示
        if (ev.getAdminFlag() != AttributeConst.ROLE_ADMIN.getIntegerValue()) {

            forward(ForwardConst.FW_ERR_UNKNOWN);
            return false;

        } else {

            return true;
        }

    }

}