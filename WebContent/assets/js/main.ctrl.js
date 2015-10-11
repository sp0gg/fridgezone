fzApp.controller("mainCtrl", function ($scope, $rootScope, Item, uiGridConstants) {
    $scope.itemList = Item.query();
    $scope.selectedItem = {};
    $scope.favoriteFiltered = false;
    $scope.tagFilter = '';

    $scope.handleGridSelection = function(row){
        if(row.isSelected) {
            $scope.gridApi.selection.clearSelectedRows();
            $scope.openItemDialog(row.entity);
        }
    };

    $scope.openItemDialog = function(item){
        $rootScope.$broadcast('openItemDialog', item);
    };

    $scope.$on('itemAdded', function(event, item){
        var returnedItem = $scope.saveItem(item);

        var oldItem = $scope.itemList.filter(function(listItem){
            return listItem.id === returnedItem.id;
        })[0];
        var index = $scope.itemList.indexOf(oldItem);
        if(index >= 0) {
            $scope.itemList[index] = returnedItem;
        }else{
            $scope.itemList.push(returnedItem);
        }
        //ADD THIS?
        //scrollToIfNecessary(gridRow, gridCol)

    });

    $scope.saveItem = function(item){
        if(typeof item.id === 'undefined'){
            return $scope.addItem(item);
        }else{
            return $scope.updateItem(item);
        }
    };

    $scope.addItem = function(item){
        return Item.add(item);
    };

    $scope.updateItem = function(item){
        return Item.update(item);
    };

    $scope.getCellClass = function(item){
        if(item.stockLevel === 'Low' || item.stockLevel === 'Out') {
            return 'low';
        }
        return '';
    };

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
                },
                enableFiltering: false
            },
            {field: 'stockLevel',
                cellClass: function(grid, row) {
                    return $scope.getCellClass(row.entity);
                },
                enableFiltering: false
            },
            {field: 'optimalQuantity', cellFilter: 'number',
                cellClass: function(grid, row) {
                    return $scope.getCellClass(row.entity);
                },
                enableFiltering: false
            },
            {field: 'tags[0].name', displayName: 'Tags',
                filter: {
                    condition: function(searchTerm, cellValue){
                        console.log("i am conditioning. value is " + cellValue);
                        return (cellValue === $scope.tagFilter);
                    },
                    noTerm: true
                }
            }
        ],
        disableCancelFilterButton: true,
        enableSelectAll: false,
        enableColumnMenus: false,
        multiSelect: false
    };

    $scope.filterTag = function(tag){
        console.log('tag is: ' + tag);
        $scope.tagFilter = tag;
        $scope.itemGridOptions.enableFiltering = !$scope.itemGridOptions.enableFiltering;
        $scope.gridApi.core.notifyDataChange(uiGridConstants.dataChange.COLUMN);

    };

    //CP
    $scope.itemGridOptions.onRegisterApi = function(gridApi){
        $scope.gridApi = gridApi;
        gridApi.selection.on.rowSelectionChanged($scope, $scope.handleGridSelection);
    };

});