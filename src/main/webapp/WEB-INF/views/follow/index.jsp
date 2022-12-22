<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.AttributeConst"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst"%>

<c:set var="actEmp" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="actFol" value="${ForwardConst.ACT_FOLLOW.getValue()}" />
<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />
<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />
<c:set var="commDel" value="${ForwardConst.CMD_DESTROY.getValue()}" />
<c:set var="commAIU" value="${ForwardConst.CMD_AIUEO.getValue()}" />
<c:set var="employee" value="${employees}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>フォロー 一覧</h2>
        <a href="<c:url value='?action=${actFol}&command=${commAIU}' />"><select name=”aiueojun”>
        <option value=”item1”>あいうえお順</option></select></a>
        <table id="follow_list">
            <tbody>
                <tr>
                    <th class="follow_name">社員番号</th>
                    <th class="follow_date">氏名</th>
                    <th class="follow_title">ふりがな</th>
                    <th class="follow_action">日報</th>
                    <th class="follow_action">フォロー</th>
                </tr>
                 <c:forEach var="follow" items="${follows}" varStatus="status">

                    <tr class="row${status.count % 2}">
                        <td class="follow_name"><c:out
                                value="${follow.follow.code}" /></td>
                                <td><c:out value="${follow.follow.name}" /></td>
                        <td><c:out value="${follow.follow.furigana}" /></td>
                        <td class="follow_action"><a href="<c:url value='?action=${actFol}&command=${commShow}&id=${follow.follow.id}' />">詳細を見る</a></td>
                             <td>
                             <a href="<c:url value='?action=${actFol}&command=${commDel}&id=${follow.id}' />">
                                    <button class="nofollow">フォローを外す</button>
                            </a>

                          </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
         <div id="pagination">
            （全 ${follows_count} 件）<br />
            <c:forEach var="i" begin="1"
                end="${((follows_count - 1) / maxRow) + 1}" step="1">
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