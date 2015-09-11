fzApp.controller("mainCtrl", function ($scope, Item, uiGridConstants) {
    $scope.itemList = Item.query();
    $scope.selectedItem = {};
    $scope.saveMethod = 'add';


    $scope.getCellClass = function(item){
        if(item.stockLevel === 'Low' || item.stockLevel === 'Out') {
            return 'low';
        }
        return '';
    }

    $scope.handleGridSelection = function(row){
        var method = (function(){
            if(row.isSelected){
                return 'update';
            }else{
                return 'add';
            }
        });
        $scope.setSaveDialog(method);
    }

    $scope.setSaveDialog = function(item, method) {
        if(method === 'add'){
            $scope.newItem = {};
            $scope.saveLabel = 'Add';
            $scope.saveMethod = 'add';
        }else if(method === 'update'){
            $scope.saveLabel = 'Update';
            $scope.saveMethod = 'update';

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