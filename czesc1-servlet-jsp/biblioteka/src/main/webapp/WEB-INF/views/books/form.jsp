
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%-- Formularz dodawania książki: wartości i błędy pobierane przez EL --%>
<%@ include file="/WEB-INF/views/fragments/header.jspf" %>

<h2>Dodaj książkę</h2>
<form method="post" action="${pageContext.request.contextPath}/books/add">
    <label>Tytuł
        <input name="title" value="${param.title}">
    </label>
    <span class="error">${errors.title}</span>

    <label>Autor
        <input name="author" value="${param.author}">
    </label>
    <span class="error">${errors.author}</span>

    <label>Rok wydania
        <input name="year" value="${param.year}">
    </label>
    <span class="error">${errors.year}</span>

    <button type="submit">Zapisz</button>
</form>

<p>Liczba błędów: ${empty errors ? 0 : errors.size()}</p>

<%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
