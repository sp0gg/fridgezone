fzApp.controller("mainCtrl", function ($scope, $rootScope, Item, uiGridConstants) {
    $scope.itemList = Item.query();
    $scope.selectedItem = {};

    $scope.getCellClass = function(item){
        if(item.stockLevel === 'Low' || item.stockLevel === 'Out') {
            return 'low';
        }
        return '';
    }

    $scope.handleGridSelection = function(row){
        if(row.isSelected) {
            $scope.gridApi.selection.clearSelectedRows();
            $scope.openItemDialog(row.entity);
        }
    }

    $scope.resetItemForm = function(){
        $scope.newItem = {};
        $scope.oldItem = {};
        $scope.gridApi.selection.clearSelectedRows();
    }

    $scope.openItemDialog = function(item){
        $rootScope.$broadcast('openItemDialog', item);
    }

    $scope.$on('itemAdded', function(event, item){
        console.log('item has been added: ' + angular.toJson(item));
        var returnedItem = $scope.saveItem(item);
        if(typeof item.id === 'undefined'){
            $scope.itemList.push(returnedItem);
            $scope.newItem = {};
        }else{
            //REFACTOR THIS NOT TO NEED TO CHECK FOR ID OR SOMETHING
            //if index is -1, push
            var oldItem = $scope.itemList.filter(function(listItem){
                return listItem.id === returnedItem.id;
            });
            oldItem = oldItem[0];
            console.log('old item is ' + angular.toJson(oldItem));
            var index = $scope.itemList.indexOf(oldItem);
            console.log('index is ' + index + ' for item ' + angular.toJson(oldItem));
            $scope.itemList[index] = returnedItem;
            //ADD THIS?
            //scrollToIfNecessary(gridRow, gridCol)
        }
    });

    $scope.saveItem = function(item){
        if(typeof item.id === 'undefined'){
            return $scope.addItem(item);
        }else{
            return $scope.updateItem(item);
        }
    }

    $scope.addItem = function (item){
        return Item.add(item);
    }

    $scope.updateItem = function (item){
        return Item.update(item);
    }

    $scope.itemGridOptions={
        data: $scope.itemList,
        columnDefs: [
            {field: 'id',  visible: false},
            {field: 'name', name: 'name', displayName: 'Item',
                cellClass: function(grid, row) {
                    return $scope.getCellClass(row.entity);
                },
                sort: {
                    direction: uiGridConstants.ASC
                }
            },
            {field: 'stockLevel',
                cellClass: function(grid, row) {
                    return $scope.getCellClass(row.entity);
                }
            },
            {field: 'optimalQuantity',
                cellClass: function(grid, row) {
                    return $scope.getCellClass(row.entity);
                }
            }
        ],
        enableSelectAll: false,
        enableColumnMenus: false,
        multiSelect: false
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