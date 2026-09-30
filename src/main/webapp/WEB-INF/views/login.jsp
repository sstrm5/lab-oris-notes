<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!doctype html><html lang="ru"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1"><title>Вход — Закладки</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css"></head>
<body class="auth-page"><main class="app-frame auth-frame">
<header class="site-header"><a class="wordmark" href="${pageContext.request.contextPath}/login">Закладки</a><nav class="auth-nav" aria-label="Авторизация"><a class="active" href="${pageContext.request.contextPath}/login" aria-current="page">Вход</a><a href="${pageContext.request.contextPath}/register">Регистрация</a></nav></header>
<section class="auth-content" aria-labelledby="page-title"><div class="auth-box"><h1 id="page-title">Добро пожаловать</h1><p class="auth-subtitle">Войдите, чтобы продолжить работу с заметками.</p>
<c:if test="${not empty message}"><div class="notice notice-success" role="status"><c:out value="${message}"/></div></c:if>
<c:if test="${not empty error}"><div class="notice notice-error" role="alert"><c:out value="${error}"/></div></c:if>
<form class="auth-form" action="${pageContext.request.contextPath}/login" method="post"><div class="field"><label class="sr-only" for="login">Логин</label><input id="login" name="login" type="text" value="${fn:escapeXml(login)}" placeholder="Логин" autocomplete="username" required autofocus></div><div class="field"><label class="sr-only" for="password">Пароль</label><input id="password" name="password" type="password" placeholder="Пароль" autocomplete="current-password" required></div><button class="button button-primary button-wide" type="submit">Войти</button></form>
<p class="auth-switch">Нет аккаунта? <a href="${pageContext.request.contextPath}/register">Зарегистрироваться</a></p></div></section>
</main></body></html>
