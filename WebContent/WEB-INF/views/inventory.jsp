<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="webjars/bootstrap/3.3.5/css/bootstrap.min.css">

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Fridgezone</title>
</head>
<body ng-app="fridgezone" ng-controller="mainCtrl">
<div class="container-fluid">
    <div class="row">
        <div id="items" class="col-sm-1 col-lg-3">
            <br/>
            <table id="itemTable" class="table table-striped">
                <thead>
                    <tr>
                        <th>Item</th>
                        <th>Quantity</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${items}" varStatus="count">
                    <form id="updateItemForm" method="POST" action="addItem">
                        <tr>
                            <td>${item.name}</td>
                            <td>
                                <input type="text" id="itemUpdateQuantity${count.index}" name="quantity"
                                       value="${item.quantity}" size="2"/>
                            </td>
                            <td>
                                <button type="submit" id="updateItem${count.index}" class="btn btn-success btn-xs"><span class="glyphicon glyphicon-ok"></span>Update</button>
                            </td>
                        </tr>
                        <input type="hidden" value="${item.id}" name="id"/>
                        <input type="hidden" value="${item.name}" name="name"/>
                    </form>
                </c:forEach>
                </tbody>
            </table>

            <table ng-repeat="item in itemList" class="table table-striped">
                <tr>
                    <td>{{item.name}}</td>
                    <td>
                        <input type="text" id="itemUpdateQuantity${count.index}"
                               ng-model="item.quantity" size="2"/>
                    </td>
                    <td>
                        <button ng-click="updateItem(item)" id="updateItem${count.index}" class="btn btn-success btn-xs"><span class="glyphicon glyphicon-ok"></span>Update</button>
                    </td>
                </tr>
                <input type="hidden" ng-model="item.id"/>
            </table>

        </div>
    </div>
    <div class="row">
        <div id="itemInput" class="col-sm-1 col-lg-3">
            <h3>Add item</h3>
                <table id="itemInputTable">
                    <tr>
                        <td>Item Name</td>
                        <td>Quantity</td>
                    </tr>
                    <tr>
                        <td><input type="text" id="itemAddName" name="name" ng-model="item.name"/></td>
                        <td><input type="text" id="itemAddQuantity" name="quantity" ng-model="item.quantity" size="2"></td>
                    </tr>
                </table>
                <br/>
                <button ng-click="addItem()" id="addItem" class="btn btn-success btn-lg"><span class="glyphicon glyphicon-ok"></span>Add</button>
        </div>
    </div>
</div>

<%--<script src="webjars/jquery/1.11.3/jquery.min.js" type="application/javascript"></script>--%>
<script src="webjars/angular/1.4.4/angular.js" type="application/javascript"></script>
<script src="webjars/angular-resource/1.4.4/angular-resource.js"></script>
<%--<script src="webjars/angular-ui-bootstrap/0.13.1/" type="application/javascript"></script>--%>
<%--<script src="webjars/bootstrap/3.3.5/js/bootstrap.min.js" type="application/javascript"></script>--%>
<%--<script src="js/app.js"></script>--%>
<%--<script src = "js/main.ctrl.js"></script>--%>
<%--<script src = "js/services.js"></script>--%>

<script>
    var app = angular.module('fridgezone', ['ngResource']);

    app.factory("Item", function($resource) {
        return $resource('api/items/:id', {id: '@id'}, {
            query: {method:'GET', params:{id:''}, isArray:true}
            ,add: {method:'POST'}
            ,update: {method:'PUT'}
        });
    });

    app.controller("mainCtrl", function($scope, Item) {
        $scope.itemList = Item.query();

        $scope.addItem = function(){
            Item.add($scope.item)
            $scope.itemList.push($scope.item);
        }

        $scope.updateItem = function(item){
            Item.update(item)
        }
    });

</script>

</body>


</html>