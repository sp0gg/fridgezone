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
        <table id="itemsTable">
            <tr>
                <td>Item Name</td>
                <td>Quantity</td>
            </tr>
		<c:forEach var="item" items="${items}">
			<tr>
	    		<td>${item.name}</td>
                <td>${item.quantity}</td>
            </tr>
		</c:forEach>
        </table>
	</div>

<div id="itemInput">
    <h2>Add item</h2>
    <form method="POST" action="addItem">
        <table id="itemInputTable">
            <tr>
                <td>Item Name</td>
                <td>Quantity</td>
            </tr>
            <tr>
                <td><input type="text" id="itemName" name="name"/></td>
                <td><input type="text" id="itemQuantity" name="quantity" size="2"></td>
            </tr>

        </table>
     <br/>
     <input type="submit" id="addItem" value="Add"/>

    </form>

</div>

</body>
</html>