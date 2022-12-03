package models;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 日報データのDTOモデル
 *
 */
@Table(name = JpaConst.FOL_EMP)
@NamedQueries({

   // @NamedQuery(
      //      name = JpaConst.Q_FOL_GET_ALL_MINE,
        //    query = JpaConst.Q_FOL_GET_ALL_MINE_DEF),
    //@NamedQuery(
      //      name = JpaConst.Q_FOL_COUNT_ALL_MINE,
        //    query = JpaConst.Q_FOL_COUNT_ALL_MINE_DEF)
})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity

public class Follow {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.FOL_EMP_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * フォローする従業員
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.FOL_EMP_COL_FOLLOW, nullable = false)
    private Employee follow;

    /**
     * フォローされた従業員
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.FOL_EMP_COL_FOLLOWER, nullable = false)
    private Employee follower;



}
