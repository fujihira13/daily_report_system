<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst"%>

<c:set var="actFol" value="${ForwardConst.ACT_FOLLOW.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />

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
                    <th class="follow_name">氏名</th>
                    <th class="follow_date">日付</th>
                    <th class="follow_title">タイトル</th>
                    <th class="follow_action">操作</th>
                    <th class="follow_action">フォロー</th>
                </tr>
                <c:forEach var="follow" items="${follows}" varStatus="status">

                    <tr class="row${status.count % 2}">
                        <td class="follow_name"><c:out
                                value="${report.employee.name}" /></td>
                        <td class="follow_date"><fmt:formatDate value='${reportDay}'
                                pattern='yyyy-MM-dd' /></td>
                        <td class="follow_title">${report.title}</td>
                        <td class="follow_action"><a
                            href="<c:url value='?action=${actFol}&command=${commShow}&id=${follow.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${follows_count} 件）<br />
          <%--  <c:forEach var="i" begin="1"
                end="${((follows_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a
                            href="<c:url value='?action=${actFol}&command=${commIdx}&page=${i}' />"><c:out
                                value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>--%>
        </div>
        <p>
            <a href="<c:url value='?action=${actFol}&command=${commNew}' />">新規日報の登録</a>
        </p>

    </c:param>
</c:import>