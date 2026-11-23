
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/views/fragments/header.jspf" %>

<h2>Książki</h2>

<table>
    <tr>
        <th>Tytuł</th>
        <th>Autor</th>
        <th>Rok</th>
        <th></th>
    </tr>

    <c:forEach items="${books}" var="book">
        <tr>
            <td>${book.title}</td>
            <td>${book.author.name}</td>
            <td>${book.year}</td>
            <td>
                <a href="${pageContext.request.contextPath}/books/details?id=${book.id}">
                    Szczegóły
                </a>
            </td>
        </tr>
    </c:forEach>
</table>

<p>
    <a href="${pageContext.request.contextPath}/books/add">
        Dodaj książkę
    </a>
</p>

<%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
