<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>支付宝支付index</title>
</head>
<body>
<h1>支付宝支付index</h1>
<form method="post" action="createDan">
    <p> 订单编号:<input type="text" name="id"></p>
    <p> 订单金额:<input type="text" name="price"></p>
    <p> 订单标题:<input type="text" name="title"></p>
    <p> <input type="submit" value="订单提交"></p>
</form>
</body>
</html>
