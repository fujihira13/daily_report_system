<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.AttributeConst"%>
<%@ page import="constants.ForwardConst"%>

<c:set var="actEmp" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="actFol" value="${ForwardConst.ACT_FOLLOW.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="actFol" value="${ForwardConst.ACT_FOLLOW.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />
<c:set var="commDel" value="${ForwardConst.CMD_DESTROY.getValue()}" />
<c:set var="commFDel" value="${ForwardConst.CMD_FOL_DESTROY.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>従業員 一覧</h2>
        <table id="employee_list">
            <tbody>
                <tr>
                    <th>社員番号</th>
                    <th>氏名</th>
                    <th>ふりがな</th>
                    <th>従業員詳細</th>
                    <th>フォロー</th>
                </tr>



                <c:forEach var="employee" items="${employees}" varStatus="status">

                    <tr class="row${status.count % 2}">
                        <td class="follow_name"><c:out value="${employee.code}" /></td>
                        <td><c:out value="${employee.name}" /></td>
                        <td><c:out value="${employee.furigana}" /></td>
                        <td><c:choose>
                                <c:when
                                    test="${employee.deleteFlag == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()}">
                                    （削除済み）
                                </c:when>
                                <c:otherwise>
                                    <a
                                        href="<c:url value='?action=${actEmp}&command=${commShow}&id=${employee.id}' />">詳細を見る</a>

                                </c:otherwise>
                            </c:choose></td>



                        <c:set var="isfollow" value="false" />
                        <c:set var="followeeId" value="" />
                        <c:set var="followtableId" value="" />
                        <c:forEach var="follow" items="${follows}" varStatus="status">
                            <c:if test="${employee.id == follow.follow.id}">
                                <c:set var="isfollow" value="true" />
                                <c:set var="followeeId" value="${follow.follow.id}" />
                                <c:set var="followtableId" value="${follow.id}" />
                            </c:if>
                        </c:forEach>

                        <td><c:choose>
                                <c:when test="${employee.id == followeeId}">
                                    <a
                                        href="<c:url value='?action=${actFol}&command=${commFDel}&id=${followtableId}' />"><button class="nofollow">フォローを外す</button></a>
                                </c:when>
                                <c:when
                                            test="${sessionScope.login_employee.id == employee.id}">
                                            <button class="followed">ログイン中</button>
                                        </c:when>
                                <c:otherwise>
                                    <a
                                        href="<c:url value='?action=${actFol}&command=${commCrt}&id=${employee.id}' />"><button class="follow">フォローする</button></a>
                                </c:otherwise>
                            </c:choose></td>

                    </tr>
                </c:forEach>

            </tbody>
        </table>

        <div id="pagination">
            （全 ${employees_count} 件）<br />
            <c:forEach var="i" begin="1"
                end="${((employees_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a
                            href="<c:url value='?action=${actEmp}&command=${commIdx}&page=${i}' />"><c:out
                                value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p>
            <a href="<c:url value='?action=${actEmp}&command=${commNew}' />">新規従業員の登録</a>
        </p>

    </c:param>
</c:import>