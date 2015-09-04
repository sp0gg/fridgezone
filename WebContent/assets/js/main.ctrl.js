fzApp.controller("mainCtrl", function ($scope, Item, uiGridConstants) {
    $scope.itemList = Item.query();

    $scope.addItem = function (item) {
        var addedItem = Item.add(item);
        $scope.itemList.push(addedItem);
        $scope.item.new = {};
    }

    $scope.updateItem = function (item) {
        return Item.update(item);
    }

    $scope.itemGridOptions={
        data: $scope.itemList,
        columnDefs: [
            {field: 'id',  visible: false},
            {field: 'name', name: 'name', displayName: 'Item', enableCellEdit: true,
                sort: {
                    direction: uiGridConstants.ASC
                }
            },
            {field: 'stockLevel', enableCellEdit: true},
            {field: 'optimalQuantity', enableCellEdit: true}
        ],
        enableColumnMenus: false,
        rowEditWaitInterval: 1000
    }
    //CP
    $scope.saveRow = function(rowEntity){
        var promise = $scope.updateItem(rowEntity).$promise;
        $scope.gridApi.rowEdit.setSavePromise(rowEntity, promise);
    }

    //CP
    $scope.itemGridOptions.onRegisterApi = function(gridApi){
        $scope.gridApi = gridApi;
        gridApi.rowEdit.on.saveRow($scope, $scope.saveRow);
    };

});