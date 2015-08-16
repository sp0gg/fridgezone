<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Fridgezone</title>
</head>
<body>

	<div id="items">
		Your items:<br />
        <br/>
        <table id="itemTable">
            <tr>
                <td>Item Name</td>
                <td colspan="2">Quantity</td>
            </tr>

		<c:forEach var="item" items="${items}" varStatus="count">
            <form id="updateItemForm" method="POST" action="addItem">
            <tr>
	    		<td>${item.name}</td>
                <input type="hidden" value="${item.name}" name="name"/>
                <td><input type="text" id="itemUpdateQuantity${count.index}" name="quantity" value="${item.quantity}" size="2"/></td>
                <input type="hidden" value="${item.id}" name="id"/>
                <td><input type="submit" id="updateItem${count.index}" value="Update"/></td>
            </tr>
            </form>

        </c:forEach>
        </table>

	</div>

<div id="itemInput">
    <h2>Add item</h2>
    <form id="addItemForm" method="POST" action="addItem">
        <table id="itemInputTable">
            <tr>
                <td>Item Name</td>
                <td>Quantity</td>
            </tr>
            <tr>
                <td><input type="text" id="itemAddName" name="name"/></td>
                <td><input type="text" id="itemAddQuantity" name="quantity" size="2"></td>
            </tr>

        </table>
     <br/>
     <input type="submit" id="addItem" value="Add"/>

    </form>

</div>

</body>
</html>