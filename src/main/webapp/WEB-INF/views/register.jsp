<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!doctype html><html lang="ru"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1"><title>Регистрация — Закладки</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css"></head>
<body class="auth-page"><main class="app-frame auth-frame">
<header class="site-header"><a class="wordmark" href="${pageContext.request.contextPath}/login">Закладки</a><nav class="auth-nav" aria-label="Авторизация"><a href="${pageContext.request.contextPath}/login">Вход</a><a class="active" href="${pageContext.request.contextPath}/register" aria-current="page">Регистрация</a></nav></header>
<section class="auth-content" aria-labelledby="page-title"><div class="auth-box"><h1 id="page-title">Создайте аккаунт</h1><p class="auth-subtitle">Достаточно логина и надёжного пароля.</p>
<c:if test="${not empty error}"><div class="notice notice-error" role="alert"><c:out value="${error}"/></div></c:if>
<form class="auth-form" action="${pageContext.request.contextPath}/register" method="post"><div class="field"><label class="sr-only" for="login">Логин</label><input id="login" name="login" type="text" value="${fn:escapeXml(login)}" placeholder="Логин" autocomplete="username" minlength="3" maxlength="64" required autofocus></div><div class="field"><label class="sr-only" for="password">Пароль</label><input id="password" name="password" type="password" placeholder="Пароль (от 8 символов)" autocomplete="new-password" minlength="8" maxlength="72" required></div><button class="button button-primary button-wide" type="submit">Зарегистрироваться</button></form>
<p class="auth-switch">Уже есть аккаунт? <a href="${pageContext.request.contextPath}/login">Войти</a></p></div></section>
</main></body></html>
