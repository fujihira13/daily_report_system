package services;

import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.FollowConverter;
import actions.views.FollowView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.JpaConst;
import models.Employee;
import models.Follow;
import models.Report;
import models.validators.FollowValidator;

/**
 * フォローテーブルの操作に関わる処理を行うクラス
 */

public class FollowService extends ServiceBase {
    /**
     * 指定した従業員がフォローした従業員を、指定されたページ数の一覧画面に表示する分取得しFollowViewのリストで返却する
     * @param employee 従業員
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<FollowView> getMineFolPerPage(EmployeeView employee, int page) {

        List<Follow> follows = em.createNamedQuery(JpaConst.Q_FOLLOW_GET_ALL_MINE, Follow.class)
                .setParameter(JpaConst.JPQL_PARM_FOLLOWER, EmployeeConverter.toModel(employee))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return FollowConverter.toViewList(follows);
    }




    /**
     * 指定した従業員がフォローした従業員データの件数を取得し、返却する
     * @param employee
     * @return フォロー従業員データの件数
     */
    public long countAllFolMine(EmployeeView employee) {
        long follows_count = (long) em.createNamedQuery(JpaConst.Q_FOLOW_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_FOLLOWER, EmployeeConverter.toModel(employee))
                .getSingleResult();
        return follows_count;
    }


    /**
     * 指定した従業員がフォローした従業員を、並び替えて指定されたページ数の一覧画面に表示する分取得しFollowViewのリストで返却する
     * @param employee 従業員
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<FollowView> getMinePerPageNarabikae(EmployeeView employee, int page) {

        List<Follow> followsaiueo = em.createNamedQuery(JpaConst.Q_FOLLOW_GET_ALL_MINE_NARABIKAE, Follow.class)
                .setParameter(JpaConst.JPQL_PARM_FOLLOWER, EmployeeConverter.toModel(employee))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return FollowConverter.toViewList(followsaiueo);
    }


    /**
     * idを条件に取得したデータをFollowViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    /*public FollowView findOne(int id) {
        return FollowConverter.toView(findOneInternal(id));
    }



    /**
     * idを条件に取得したデータをEmployeeViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public EmployeeView findOne(int id) {
        Employee fol = findOneInternal(id);
        return EmployeeConverter.toView(fol);
    }

    /**
     * idを条件にデータを1件取得し、Employeeのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Employee findOneInternal(int id) {
        Employee fol = em.find(Employee.class, id);

        return fol;
    }


    /**
     * idを条件に取得したデータをEmployeeViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public FollowView findFolOne(int id) {
        Follow fol = findFolOneInternal(id);
        return FollowConverter.toView(fol);
    }

    /**
     * idを条件にデータを1件取得し、Employeeのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Follow findFolOneInternal(int id) {
        Follow fol = em.find(Follow.class, id);

        return fol;
    }

    /**
     * 指定した従業員が作成した日報データを、指定されたページ数の一覧画面に表示する分取得しReportViewのリストで返却する
     * @param employee 従業員
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<ReportView> getMinefollowPerPage(FollowView follow,int page) {

        List<Report> followReports = em.createNamedQuery(JpaConst.Q_REP_GET_ALL_MINE, Report.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, FollowConverter.toModel(follow))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return ReportConverter.toViewList(followReports);
    }

    /**
     * 指定した従業員がフォローした従業員が作成した日報データの件数を取得し、返却する
     * @param employee
     * @return 日報データの件数
     */
    public long countAllFollowMine(FollowView employee) {

        long count = (long) em.createNamedQuery(JpaConst.Q_REP_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, FollowConverter.toModel(employee))
                .getSingleResult();

        return count;
    }


    /**
     * 画面から入力されたフォローの登録を元に、フォローデータを更新する
     * @param fv フォローの更新内容


    public List<String> update(FollowView fv) {

            updateInternal(fv);
        }

    */
    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    /* private Follow findOneInternal(int id) {
        return em.find(Follow.class, id);
    }



    /**
     * 画面から入力されたフォローの登録内容を元にデータを1件作成し、フォローテーブルに登録する
     * @param fv フォローの登録内容
     * @return
     */
    public List<String> create(FollowView fv) {
        List<String> errors = FollowValidator.validate(fv);
        if (errors.size() == 0) {
            createInternal(fv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * フォローデータを1件登録する
     * @param fv フォローデータ
     */
    private void createInternal(FollowView fv) {

        em.getTransaction().begin();
        em.persist(FollowConverter.toModel(fv));
        em.getTransaction().commit();

    }

    /**
     * idを条件にフォロー従業員データを削除する
     * @param id
     */
    public void destroy(Integer id) {
        //idを条件にフォロー済みの従業員情報を取得する

        Follow f = em.find(Follow.class, id);
        //削除する

        em.getTransaction().begin();
        em.remove(f); // データの物理削除
        em.getTransaction().commit();
        em.close();

    }

    /**
     * idを条件にフォロー従業員データを削除する
     * @param id
     */
    public void fdestroy(Integer id) {
        //idを条件にフォロー済みの従業員情報を取得する

        Follow f = em.find(Follow.class, id);
        //削除する

        em.getTransaction().begin();
        em.remove(f); // データの物理削除
        em.getTransaction().commit();
        em.close();

    }




}
