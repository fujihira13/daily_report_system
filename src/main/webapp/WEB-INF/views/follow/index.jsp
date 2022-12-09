<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.AttributeConst"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst"%>

<c:set var="actEmp" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="actFol" value="${ForwardConst.ACT_FOLLOW.getValue()}" />
<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>フォロー 一覧</h2>
        <table id="follow_list">
            <tbody>
                <tr>
                    <th class="follow_name">社員番号</th>
                    <th class="follow_date">氏名</th>
                    <th class="follow_title">ふりがな</th>
                    <th class="follow_action">操作</th>
                    <th class="follow_action">フォロー</th>
                </tr>
                <c:forEach var="follow" items="${follows}" varStatus="status">

                    <tr class="row${status.count % 2}">
                        <td class="follow_name"><c:out
                                value="${follow.id.employee.code}" /></td>
                                <td><c:out value="${follow.id.employee.name}" /></td>
                        <td><c:out value="${follow.id.employee.furigana}" /></td>
                        <td class="follow_action"><a
                            href="<c:url value='?action=${actRep}&command=${commShow}&id=${report.id}' />">詳細を見る</a></td>
                             <td>
                                    <button class="follow">フォローを外す</button>
                          </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>


        <p>
            <a href="<c:url value='?action=${actFol}&command=${commNew}' />">新規日報の登録</a>
        </p>

    </c:param>
</c:import>