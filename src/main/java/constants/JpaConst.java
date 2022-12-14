package constants;

public interface JpaConst {


  //persistence-unit名
    String PERSISTENCE_UNIT_NAME = "daily_report_system";

    //データ取得件数の最大値
    int ROW_PER_PAGE = 15; //1ページに表示するレコードの数

    //従業員テーブル
    String TABLE_EMP = "employees"; //テーブル名
    //従業員テーブルカラム
    String EMP_COL_ID = "id"; //id
    String EMP_COL_CODE = "code"; //社員番号
    String EMP_COL_NAME = "name"; //氏名
    String EMP_COL_PASS = "password"; //パスワード
    String EMP_COL_ADMIN_FLAG = "admin_flag"; //管理者権限
    String EMP_COL_CREATED_AT = "created_at"; //登録日時
    String EMP_COL_UPDATED_AT = "updated_at"; //更新日時
    String EMP_COL_DELETE_FLAG = "delete_flag"; //削除フラグ
    String EMP_COL_FURIGANA = "furigana"; //ふりがな

    int ROLE_ADMIN = 1; //管理者権限ON(管理者)
    int ROLE_GENERAL = 0; //管理者権限OFF(一般)
    int EMP_DEL_TRUE = 1; //削除フラグON(削除済み)
    int EMP_DEL_FALSE = 0; //削除フラグOFF(現役)

    //日報テーブル
    String TABLE_REP = "reports"; //テーブル名
    //日報テーブルカラム
    String REP_COL_ID = "id"; //id
    String REP_COL_EMP = "employee_id"; //日報を作成した従業員のid
    String REP_COL_REP_DATE = "report_date"; //いつの日報かを示す日付
    String REP_COL_TITLE = "title"; //日報のタイトル
    String REP_COL_CONTENT = "content"; //日報の内容
    String REP_COL_CREATED_AT = "created_at"; //登録日時
    String REP_COL_UPDATED_AT = "updated_at"; //更新日時

  //フォローテーブル
    String FOL_EMP = "follower_employees"; //テーブル名
    //フォローテーブルカラム
    String FOL_EMP_COL_ID = "id"; //id
    String FOL_EMP_COL_FOLLOW = "follow_id"; //フォローする従業員のid
    String FOL_EMP_COL_FOLLOWER = "follower_id"; //フォローされた従業員のid

    //Entity名
    String ENTITY_EMP = "employee"; //従業員
    String ENTITY_REP = "report"; //日報
    String ENTITY_FOL = "follow"; //フォロー

    //JPQL内パラメータ
    String JPQL_PARM_CODE = "code"; //社員番号
    String JPQL_PARM_PASSWORD = "password"; //パスワード
    String JPQL_PARM_EMPLOYEE = "employee"; //従業員
    String JPQL_PARM_FOLLOWER = "follower"; //フォロワーー

    //NamedQueryの nameとquery
    //全ての従業員をidの降順に取得する
    String Q_EMP_GET_ALL = ENTITY_EMP + ".getAll"; //name
    String Q_EMP_GET_ALL_DEF = "SELECT e FROM Employee AS e ORDER BY e.id DESC"; //query
    //全ての従業員の件数を取得する
    String Q_EMP_COUNT = ENTITY_EMP + ".count";
    String Q_EMP_COUNT_DEF = "SELECT COUNT(e) FROM Employee AS e";
    //社員番号とハッシュ化済パスワードを条件に未削除の従業員を取得する
    String Q_EMP_GET_BY_CODE_AND_PASS = ENTITY_EMP + ".getByCodeAndPass";
    String Q_EMP_GET_BY_CODE_AND_PASS_DEF = "SELECT e FROM Employee AS e WHERE e.deleteFlag = 0 AND e.code = :" + JPQL_PARM_CODE + " AND e.password = :" + JPQL_PARM_PASSWORD;
    //指定した社員番号を保持する従業員の件数を取得する
    String Q_EMP_COUNT_REGISTERED_BY_CODE = ENTITY_EMP + ".countRegisteredByCode";
    String Q_EMP_COUNT_REGISTERED_BY_CODE_DEF = "SELECT COUNT(e) FROM Employee AS e WHERE e.code = :" + JPQL_PARM_CODE;
  //指定した従業員のふりがなを取得する
    String Q_EMP_FU_GET = ENTITY_EMP + ".getFurigana"; //ふりがな
    String Q_EMP_FU_GET_DEF = "SELECT e FROM Employee AS e WHERE e.furigana = :" + JPQL_PARM_EMPLOYEE;
    //フォローした全件idを降順で取得する
    String Q_FOL_GET_ALL_MINE = ENTITY_FOL + ".getAllMine";
    String Q_FOL_GET_ALL_MINE_DEF = "SELECT f FROM Follow AS f ORDER BY f.id DESC";
    //フォローした社員の件数を取得する
    String Q_FOL_COUNT_ALL_MINE = ENTITY_FOL + ".countAllMine";
    String Q_FOL_COUNT_ALL_MINE_DEF = "SELECT COUNT(f) FROM Follow AS f";
    //指定した従業員がフォローした従業員を全て取得する
    String Q_FOLLOW_GET_ALL_MINE = ENTITY_FOL + ".getAllFollowMine";
    String Q_FOLLOW_GET_ALL_MINE_DEF = "SELECT  f FROM Follow AS f WHERE f.follower = :" + JPQL_PARM_FOLLOWER + " ORDER BY f.id DESC";
    //指定した従業員がフォローした従業員の件数を取得する
    String Q_FOLOW_COUNT_ALL_MINE = ENTITY_FOL + ".countFollowAllMine";
    String Q_FOLLOW_COUNT_ALL_MINE_DEF = "SELECT COUNT(f) FROM Follow AS f WHERE f.follower = :" + JPQL_PARM_FOLLOWER;



    //指定した従業員がフォローした従業員を全て取得しあいうえお順に並び替える
    String Q_FOLLOW_GET_ALL_MINE_NARABIKAE = ENTITY_FOL + ".getAllFollowMineNarabikae";
    String Q_FOLLOW_GET_ALL_MINE_NARABIKAE_DEF = "SELECT  f FROM Follow AS f WHERE f.follower = :" + JPQL_PARM_FOLLOWER+ " ORDER BY f.follow.furigana ";



    //指定した社員番号を保持するフォロー従業員の件数を取得する
    String Q_FOL_COUNT_REGISTERED_BY_CODE = ENTITY_FOL + ".countRegisteredByCode";
    String Q_FOL_COUNT_REGISTERED_BY_CODE_DEF = "SELECT COUNT(f) FROM Follow AS f WHERE f.code = :" + JPQL_PARM_CODE;
    //ログイン中従業員がフォローした従業員の作成した日報を全て取得
    String Q_FOLLOW_GET_ALL_MINE_REPORT = ENTITY_FOL + ".getAllFollowMineReport";
    String Q_FOLLOW_GET_ALL_MINE__REPORT_DEF = "SELECT  f FROM Report AS r,Follow AS f WHERE f.follower = :" + JPQL_PARM_FOLLOWER + " ORDER BY f.id DESC";
    //ログイン中従業員がフォローした従業員の作成した日報の件数を取得する
    String Q_REP_COUNT_ALL_FOLLOW_MINE = ENTITY_REP + ".countAllFollowMine";
    String Q_REP_COUNT_ALL_FOLLOW_MINE_DEF = "SELECT COUNT(r) FROM Report AS r,Follow AS f WHERE r.employee = :" + JPQL_PARM_EMPLOYEE;
    //指定した従業員が保持しているをフォローidを取得する
    String Q_FOL_GET_ID = ENTITY_FOL + ".getID";
    String Q_FOL_GET_ID_DEF = "SELECT f FROM Follow AS f WHERE f.follow = :" + JPQL_PARM_EMPLOYEE ;
    //指定した従業員が保持しているフォローidの件数を取得する
    String Q_FOLID_COUNT_ALL_MINE = ENTITY_REP + ".countIdAllMine";
    String Q_FOLID_COUNT_ALL_MINE_DEF = "SELECT COUNT(f) FROM Follow AS f WHERE f.follow = :" + JPQL_PARM_EMPLOYEE;
    //全ての日報をidの降順に取得する
    String Q_REP_GET_ALL = ENTITY_REP + ".getAll";
    String Q_REP_GET_ALL_DEF = "SELECT r FROM Report AS r ORDER BY r.id DESC";
    //全ての日報の件数を取得する
    String Q_REP_COUNT = ENTITY_REP + ".count";
    String Q_REP_COUNT_DEF = "SELECT COUNT(r) FROM Report AS r";
    //指定した従業員が作成した日報を全件idの降順で取得する
    String Q_REP_GET_ALL_MINE = ENTITY_REP + ".getAllMine";
    String Q_REP_GET_ALL_MINE_DEF = "SELECT r FROM Report AS r WHERE r.employee = :" + JPQL_PARM_EMPLOYEE + " ORDER BY r.id DESC";
    //指定した従業員が作成した日報の件数を取得する
    String Q_REP_COUNT_ALL_MINE = ENTITY_REP + ".countAllMine";
    String Q_REP_COUNT_ALL_MINE_DEF = "SELECT COUNT(r) FROM Report AS r WHERE r.employee = :" + JPQL_PARM_EMPLOYEE;

}