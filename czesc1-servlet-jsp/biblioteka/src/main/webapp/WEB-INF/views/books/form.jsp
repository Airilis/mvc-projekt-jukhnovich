
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/views/fragments/header.jspf" %>

<h2>Dodaj książkę</h2>

<form method="post" action="${pageContext.request.contextPath}/books/add">

    <label>
        Tytuł
        <input name="title" value="${param.title}">
    </label>

    <span class="error">${errors.title}</span>

    <label>
        Autor
        <select name="authorId">
            <option value="">-- wybierz --</option>

            <c:forEach items="${authors}" var="a">
                <option value="${a.id}"
                    ${param.authorId == a.id ? 'selected' : ''}>
                    ${a.name}
                </option>
            </c:forEach>
        </select>
    </label>

    <span class="error">${errors.authorId}</span>

    <label>
        Rok wydania
        <input name="year" value="${param.year}">
    </label>

    <span class="error">${errors.year}</span>

    <button type="submit">Zapisz</button>

</form>

<%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
