
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/fragments/header.jspf" %>

<h2>Witaj w Bibliotece</h2>
<p>Twoja przeglądarka: ${header['User-Agent']}</p>
<p>Kontekst aplikacji: ${pageContext.request.contextPath}</p>
<p>Przykład operatorów EL: 2 + 3 = ${2 + 3}, 10 > 3 to ${10 gt 3}</p>

<%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
