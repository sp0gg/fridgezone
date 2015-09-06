fzApp.controller("mainCtrl", function ($scope, Item, uiGridConstants) {
    $scope.itemList = Item.query();
    $scope.saveMethod = 'add';
    $scope.stockLevelValues = [
        'Out',
        'Low',
        'Stocked',
        'Surplus'
    ];

    $scope.addItem = function (item) {
        var addedItem = Item.add(item);
        $scope.itemList.push(addedItem);
        $scope.newItem = {};
    }

    $scope.updateItem = function (item) {
        var index = $scope.itemList.indexOf($scope.oldItem);
        var updatedItem = Item.update(item);
        $scope.itemList[index] = updatedItem;
        $scope.setSaveDialog({}, 'add');
        return updatedItem;
    }

    $scope.handleGridSelection = function(row){
        var method = (function(){
            if(row.isSelected){
                return 'update';
            }else{
                return 'add';
            }
        })();
        $scope.setSaveDialog(row.entity, method);
    }

    $scope.setSaveDialog = function(item, method) {
        if(method === 'add'){
            $scope.newItem = {};
            $scope.saveLabel = 'Add';
            $scope.saveMethod = 'add';
        }else if(method === 'update'){
            $scope.saveLabel = 'Update';
            $scope.saveMethod = 'update';

            console.log(item);
            $scope.newItem = {};
            $scope.newItem.id = item.id;
            $scope.newItem.name = item.name;
            $scope.newItem.stockLevel = item.stockLevel;
            $scope.newItem.optimalQuantity = item.optimalQuantity;
            $scope.oldItem = item;
            //$scope.newItem = item;
        }

    }
    $scope.resetItemForm = function(){
        $scope.newItem = {};
        $scope.oldItem = {};
        $scope.setSaveDialog({}, 'add');
        $scope.gridApi.selection.clearSelectedRows();
    }

    $scope.saveItem = function(item){
        console.log(item);
        if($scope.saveMethod === 'update'){
            console.log('upd');
            $scope.updateItem(item);
        }else if($scope.saveMethod === 'add'){
            $scope.addItem(item);
        }
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
        enableRowSelection: true,
        enableSelectAll: false,
        enableColumnMenus: false,
        multiSelect: false,
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
        gridApi.selection.on.rowSelectionChanged($scope, $scope.handleGridSelection);
    };

});