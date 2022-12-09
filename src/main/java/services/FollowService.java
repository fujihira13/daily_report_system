package services;


import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.FollowConverter;
import actions.views.FollowView;
import constants.JpaConst;
import models.Employee;
import models.Follow;
import models.validators.FollowValidator;




/**
 * フォローテーブルの操作に関わる処理を行うクラス
 */

public class FollowService  extends ServiceBase {
    /**
     * 指定したフォロー従業員を、指定されたページ数の一覧画面に表示する分取得しFollowViewのリストで返却する
     * @param employee 従業員
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<FollowView> getMinePerPage(int page) {

        List<Follow> follows = em.createNamedQuery(JpaConst.Q_FOL_GET_ALL_MINE, Follow.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return FollowConverter.toViewList(follows);
    }

    /**
     * 指定したフォロー従業員が作成した日報データの件数を取得し、返却する
     * @param employee
     * @return 日報データの件数
     */
    public long countAllMine() {
            long follows_count = (long) em.createNamedQuery(JpaConst.Q_FOL_COUNT, Long.class)
                    .getSingleResult();
            return follows_count;
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
        List<String>  errors = FollowValidator.validate(fv);
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
     * フォローデータを削除する
     * @param rv フォローデータ
     */
   /* private void updateInternal(FollowView fv) {

        em.getTransaction().begin();
        Follow f = findOneInternal(fv.getId());
        FollowConverter.copyViewToModel(f, fv);
        em.getTransaction().commit();

    }*/

}



